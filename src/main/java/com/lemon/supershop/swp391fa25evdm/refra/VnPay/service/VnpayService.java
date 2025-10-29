package com.lemon.supershop.swp391fa25evdm.refra.VnPay.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TimeZone;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.lemon.supershop.swp391fa25evdm.order.model.entity.Order;
import com.lemon.supershop.swp391fa25evdm.order.repository.OrderRepo;
import com.lemon.supershop.swp391fa25evdm.payment.model.entity.Payment;
import com.lemon.supershop.swp391fa25evdm.payment.model.enums.PaymentStatus;
import com.lemon.supershop.swp391fa25evdm.payment.repository.PaymentRepo;
import com.lemon.supershop.swp391fa25evdm.refra.VnPay.model.dto.Response.VnpayRes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@Service
public class VnpayService {

    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private PaymentRepo paymentRepo;

    @Value("${vnpay.tmn_code}")
    private String tmnCode;

    @Value("${vnpay.hash_secret}")
    private String hashSecret;

    @Value("${vnpay.url}")
    private String vnpayUrl;

    @Value("${vnpay.return_url}")
    private String returnUrl;

    @Value("${vnpay.api_url:https://sandbox.vnpayment.vn/merchant_webapi/api/transaction}")
    private String apiUrl;

    /**
     * Tạo URL thanh toán VNPay
     *
     * @param orderId Mã đơn hàng
     * @param ipAddress IP của khách hàng
     * @param bankCode Mã ngân hàng (tùy chọn, null nếu để khách chọn tại VNPay)
     * @return URL thanh toán
     */
    public VnpayRes createPaymentUrl(String orderId, String ipAddress, String bankCode) throws Exception {
        Optional<Order> order = orderRepo.findById(Integer.valueOf(orderId));

        if (!order.isPresent()) {
            throw new Exception("Order not found with ID: " + orderId);
        }

        long amount = order.get().getTotal();
        String orderInfo = order.get().getDescription();

        // ✅ Tạo mã giao dịch duy nhất: orderId + timestamp
        String vnpTxnRef = orderId + "_" + System.currentTimeMillis();

        Optional<Payment> existingPayment = paymentRepo.findByVnpOrderId(orderId);

        if (existingPayment.isPresent()) {
            Payment payment = existingPayment.get();

            if (payment.getPaidStatus() == PaymentStatus.PAID) {
                throw new Exception("Order has already been paid.");
            } else if (payment.getPaidStatus() == PaymentStatus.PENDING) {
                // Cập nhật lại Payment với trạng thái PENDING
                payment.setPaidStatus(PaymentStatus.PENDING);
                payment.setTransactionCode(null);
                payment.setResponseCode(null);
                payment.setBankCode(null);
                payment.setProviderResponse(null);
                payment.setUpdateAt(new Date());
                paymentRepo.save(payment);
                
                System.out.println("🔄 Reset existing payment for retry: Order " + orderId);
            }
        } else {
            // ✅ Tạo mới Payment với trạng thái PENDING
            Payment newPayment = new Payment();
            newPayment.setMethod("VNPay");
            newPayment.setPaidStatus(PaymentStatus.PENDING);
            newPayment.setOrder(order.get());
            newPayment.setUser(order.get().getUser());
            newPayment.setVnpOrderId(orderId);
            newPayment.setUpdateAt(new Date());
            paymentRepo.save(newPayment);
            
            System.out.println("✅ Created new payment for order: " + orderId);
        }

        // Build tham số VNPay
        Map<String, String> vnp_Params = new HashMap<>();

        vnp_Params.put("vnp_Version", "2.1.0");
        vnp_Params.put("vnp_Command", "pay");
        vnp_Params.put("vnp_TmnCode", tmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount * 100)); // VNPay yêu cầu số tiền * 100
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", vnpTxnRef); // ✅ Sử dụng mã giao dịch duy nhất
        vnp_Params.put("vnp_OrderInfo", orderInfo + " - Order: " + orderId); // ✅ Thêm orderId để tra cứu
        vnp_Params.put("vnp_OrderType", "other");

        // Thêm vnp_BankCode nếu được chỉ định (tùy chọn)
        // Nếu không có, khách hàng sẽ chọn ngân hàng tại trang VNPay
        if (bankCode != null && !bankCode.isEmpty()) {
            vnp_Params.put("vnp_BankCode", bankCode);
        }
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", returnUrl);
        vnp_Params.put("vnp_IpAddr", ipAddress);

        // Tạo thời gian
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15); // Hết hạn sau 15 phút
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        // Build query string và tạo secure hash
        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);

        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();

        Iterator<String> itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = itr.next();
            String fieldValue = vnp_Params.get(fieldName);

            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                // Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));

                // Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));

                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }

        String queryUrl = query.toString();
        String vnp_SecureHash = hmacSHA512(hashSecret, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String url = vnpayUrl + "?" + queryUrl;
        // VnpayRes res = new VnpayRes(orderId, amount, bankCode, url);
        // return res;
        //    return vnpayUrl + "?" + queryUrl;
        return new VnpayRes(orderId, amount, bankCode, url);
    }

    /**
     * Verify và xử lý callback từ VNPay
     */
    @Transactional
    public Map<String, String> handleCallback(HttpServletRequest request) {
        Map<String, String> result = new HashMap<>();

        try {
            // Lấy tất cả parameters từ VNPay
            Map<String, String> params = new HashMap<>();
            for (String key : request.getParameterMap().keySet()) {
                params.put(key, request.getParameter(key));
            }

            System.out.println("📨 Received callback from VNPay:");
            System.out.println("   TxnRef: " + params.get("vnp_TxnRef"));
            System.out.println("   ResponseCode: " + params.get("vnp_ResponseCode"));
            System.out.println("   TransactionNo: " + params.get("vnp_TransactionNo"));

            // 1. Verify signature
            if (!verifyCallback(params)) {
                result.put("status", "error");
                result.put("message", "Invalid signature");
                System.err.println("❌ Invalid signature from VNPay");
                return result;
            }

            // 2. Lấy thông tin từ callback
            String vnpTxnRef = params.get("vnp_TxnRef");
            String vnpAmount = params.get("vnp_Amount");
            String vnpTransactionNo = params.get("vnp_TransactionNo");
            String vnpBankCode = params.get("vnp_BankCode");
            String vnpResponseCode = params.get("vnp_ResponseCode");

            // 3. Tách orderId từ vnpTxnRef (format: orderId_timestamp)
            String orderId = vnpTxnRef.split("_")[0];

            // 4. Cập nhật Payment trong DB
            savePayment(vnpTxnRef, vnpAmount, vnpTransactionNo, vnpBankCode, vnpResponseCode);

            // 5. Trả về kết quả
            if ("00".equals(vnpResponseCode)) {
                result.put("status", "success");
                result.put("message", "Payment successful");
                result.put("orderId", orderId);
                result.put("transactionNo", vnpTransactionNo);
            } else {
                result.put("status", "failed");
                result.put("message", "Payment failed: " + getResponseCodeMessage(vnpResponseCode));
                result.put("orderId", orderId);
                result.put("responseCode", vnpResponseCode);
            }

        } catch (Exception e) {
            System.err.println("❌ Error processing callback: " + e.getMessage());
            e.printStackTrace();
            result.put("status", "error");
            result.put("message", "Internal error: " + e.getMessage());
        }

        return result;
    }

    /**
     * Verify callback từ VNPay
     *
     * @param params Các tham số từ VNPay callback
     * @return true nếu signature hợp lệ
     */
    public boolean verifyCallback(Map<String, String> params) {
        String vnp_SecureHash = params.get("vnp_SecureHash");
        params.remove("vnp_SecureHash");
        params.remove("vnp_SecureHashType");

        // Build hash data
        List<String> fieldNames = new ArrayList<>(params.keySet());
        Collections.sort(fieldNames);

        StringBuilder hashData = new StringBuilder();
        Iterator<String> itr = fieldNames.iterator();

        while (itr.hasNext()) {
            String fieldName = itr.next();
            String fieldValue = params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                hashData.append(fieldName);
                hashData.append('=');
                try {
                    hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                if (itr.hasNext()) {
                    hashData.append('&');
                }
            }
        }

        String calculatedHash = hmacSHA512(hashSecret, hashData.toString());
        return calculatedHash.equals(vnp_SecureHash);
    }

    @Transactional
    public void savePayment(String vnp_TxnRef, String vnp_Amount, String vnp_TransactionNo, String vnp_BankCode, String vnp_ResponseCode) {
        // ✅ Tách orderId từ vnp_TxnRef (format: orderId_timestamp)
        String orderId = vnp_TxnRef.split("_")[0];

        // Tìm Payment trong DB bằng orderId
        Optional<Payment> payment = paymentRepo.findByVnpOrderId(orderId);
        if (payment.isPresent()) {
            // Tạo chuỗi thông tin phản hồi
            String providerResponse = String.format("TxnRef=%s, Amount=%s, TransactionNo=%s, BankCode=%s, ResponseCode=%s",
                    vnp_TxnRef, vnp_Amount, vnp_TransactionNo, vnp_BankCode, vnp_ResponseCode);

            // 4. ✅ UPDATE DB (giống callback, vì localhost không nhận IPN)
            if ("00".equals(vnp_ResponseCode)) {
                // Thanh toán thành công
                payment.get().setPaidStatus(PaymentStatus.PAID);
                payment.get().setTransactionCode(vnp_TransactionNo);
                payment.get().setResponseCode(vnp_ResponseCode);
                payment.get().setBankCode(vnp_BankCode);
                payment.get().setUpdateAt(new Date());
                payment.get().setProviderResponse(providerResponse);
                paymentRepo.save(payment.get());

                System.out.println("✅ Payment successful (return): " + vnp_TxnRef);
            } else {
                // Thanh toán thất bại
                payment.get().setPaidStatus(PaymentStatus.FAILED);
                payment.get().setResponseCode(vnp_ResponseCode);
                payment.get().setUpdateAt(new Date());
                payment.get().setProviderResponse(providerResponse);
                paymentRepo.save(payment.get());

                System.out.println("❌ Payment failed (return): " + vnp_TxnRef + " - Code: " + vnp_ResponseCode);
            }
        }
    }

    /**
     * Tính HMAC SHA512
     */
    private String hmacSHA512(String key, String data) {
        try {
            Mac hmac512 = Mac.getInstance("HmacSHA512");
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
            hmac512.init(secretKey);
            byte[] result = hmac512.doFinal(data.getBytes(StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder();
            for (byte b : result) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("Failed to calculate HMAC SHA512", e);
        }
    }

    /**
     * Lấy IP address từ request
     */
    public String getIpAddress(jakarta.servlet.http.HttpServletRequest request) {
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null || ipAddress.isEmpty()) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }

    /**
     * Lấy message từ response code - public để Controller có thể dùng
     */
    public String getResponseCodeMessage(String responseCode) {
        Map<String, String> messages = new HashMap<>();
        messages.put("00", "Giao dịch thành công");
        messages.put("07", "Trừ tiền thành công. Giao dịch bị nghi ngờ (liên quan tới lừa đảo, giao dịch bất thường).");
        messages.put("09", "Giao dịch không thành công do: Thẻ/Tài khoản của khách hàng chưa đăng ký dịch vụ InternetBanking tại ngân hàng.");
        messages.put("10", "Giao dịch không thành công do: Khách hàng xác thực thông tin thẻ/tài khoản không đúng quá 3 lần");
        messages.put("11", "Giao dịch không thành công do: Đã hết hạn chờ thanh toán. Xin quý khách vui lòng thực hiện lại giao dịch.");
        messages.put("12", "Giao dịch không thành công do: Thẻ/Tài khoản của khách hàng bị khóa.");
        messages.put("13", "Giao dịch không thành công do Quý khách nhập sai mật khẩu xác thực giao dịch (OTP).");
        messages.put("24", "Giao dịch không thành công do: Khách hàng hủy giao dịch");
        messages.put("51", "Giao dịch không thành công do: Tài khoản của quý khách không đủ số dư để thực hiện giao dịch.");
        messages.put("65", "Giao dịch không thành công do: Tài khoản của Quý khách đã vượt quá hạn mức giao dịch trong ngày.");
        messages.put("75", "Ngân hàng thanh toán đang bảo trì.");
        messages.put("79", "Giao dịch không thành công do: KH nhập sai mật khẩu thanh toán quá số lần quy định.");
        messages.put("99", "Các lỗi khác");

        return messages.getOrDefault(responseCode, "Unknown error code: " + responseCode);
    }
}
