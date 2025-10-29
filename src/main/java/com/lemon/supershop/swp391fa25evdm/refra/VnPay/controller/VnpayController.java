package com.lemon.supershop.swp391fa25evdm.refra.VnPay.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lemon.supershop.swp391fa25evdm.refra.VnPay.model.dto.Response.VnpayRes;
import com.lemon.supershop.swp391fa25evdm.refra.VnPay.service.VnpayService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/vnpay")
@CrossOrigin("*")
public class VnpayController {

    @Autowired
    private VnpayService vnpayService;

    /**
     * Tạo URL thanh toán VNPay POST /api/vnpay/create-payment
     *
     * @param orderId Mã đơn hàng (unique)
     * @param bankCode (Optional) Mã ngân hàng - nếu null, khách chọn tại VNPay
     * VD: NCB, VIETCOMBANK, VIETINBANK, AGRIBANK, etc.
     */
    @PostMapping("/create-payment")
    public ResponseEntity<?> createPayment(
            @RequestParam String orderId,
            @RequestParam(required = false) String bankCode,
            HttpServletRequest request
    ) {
        try {
            String ipAddress = vnpayService.getIpAddress(request);
            VnpayRes response = vnpayService.createPaymentUrl(orderId, ipAddress, bankCode);
            System.out.println("✅ Payment URL created for order: " + orderId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("❌ Error creating payment: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", e.getMessage()
            ));
        }
    }

    /**
     * 📍 Endpoint nhận callback trực tiếp từ VNPay (cho TEST không có FE)
     * GET /api/vnpay/return
     * 
     * ✅ Để test ngay: Giữ nguyên vnpay.return_url=http://localhost:6969/api/vnpay/return
     * ⚠️ Sau khi có FE: Đổi thành http://localhost:3000/payment/vnpay-return
     */
    @GetMapping("/return")
    public ResponseEntity<?> handleVnpayReturn(@RequestParam Map<String, String> params) {
        System.out.println("🔔 VNPay return callback received");
        System.out.println("   TxnRef: " + params.get("vnp_TxnRef"));
        System.out.println("   ResponseCode: " + params.get("vnp_ResponseCode"));
        
        return processPaymentCallback(params);
    }

    /**
     * 📍 API để Frontend gửi callback params từ VNPay (khi có FE)
     * POST /api/vnpay/verify-payment
     * 
     * Frontend nhận params từ URL sau khi VNPay redirect, rồi POST lên API này
     */
    @PostMapping("/verify-payment")
    public ResponseEntity<?> verifyPayment(@RequestParam Map<String, String> params) {
        System.out.println("🔔 Verifying payment from frontend");
        System.out.println("   TxnRef: " + params.get("vnp_TxnRef"));
        System.out.println("   ResponseCode: " + params.get("vnp_ResponseCode"));
        
        return processPaymentCallback(params);
    }

    /**
     * 🔧 Xử lý callback chung (dùng cho cả 2 endpoint trên)
     */
    private ResponseEntity<?> processPaymentCallback(Map<String, String> params) {
        try {
            // 1. Verify signature
            if (!vnpayService.verifyCallback(params)) {
                System.err.println("❌ Invalid signature");
                return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", "Invalid signature"
                ));
            }

            // 2. Lấy thông tin từ VNPay
            String vnpTxnRef = params.get("vnp_TxnRef");
            String vnpAmount = params.get("vnp_Amount");
            String vnpTransactionNo = params.get("vnp_TransactionNo");
            String vnpBankCode = params.get("vnp_BankCode");
            String vnpResponseCode = params.get("vnp_ResponseCode");

            if (vnpTxnRef == null || vnpResponseCode == null) {
                return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", "Missing required parameters"
                ));
            }

            // 3. Tách orderId từ vnpTxnRef (format: orderId_timestamp)
            String orderId = vnpTxnRef.split("_")[0];

            // 4. Lưu payment vào DB
            vnpayService.savePayment(vnpTxnRef, vnpAmount, vnpTransactionNo, vnpBankCode, vnpResponseCode);

            // 5. Chuẩn bị response
            Map<String, Object> response = new HashMap<>();
            response.put("orderId", orderId);
            response.put("transactionNo", vnpTransactionNo != null ? vnpTransactionNo : "");
            response.put("bankCode", vnpBankCode);
            response.put("responseCode", vnpResponseCode);

            if (vnpAmount != null) {
                try {
                    response.put("amount", Long.parseLong(vnpAmount) / 100);
                } catch (NumberFormatException e) {
                    response.put("amount", 0);
                }
            }

            // 6. Trả về kết quả
            if ("00".equals(vnpResponseCode)) {
                response.put("success", true);
                response.put("message", "Giao dịch thành công");
                System.out.println("✅ Payment successful: Order " + orderId);
            } else {
                response.put("success", false);
                response.put("message", vnpayService.getResponseCodeMessage(vnpResponseCode));
                System.out.println("❌ Payment failed: Order " + orderId + " - Code: " + vnpResponseCode);
            }

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            System.err.println("❌ Error processing payment callback: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of(
                "success", false,
                "message", "Internal error: " + e.getMessage()
            ));
        }
    }

    // ============================================
    // 📦 CODE CŨ (đã comment để backup)
    // ============================================
    
    /**
     * [OLD CODE - COMMENTED]
     * Callback từ VNPay (IPN - Instant Payment Notification)
     * GET /api/vnpay/callback
     * 
     * ❌ Code này có vấn đề: Không lưu payment vào DB
     */
    // @GetMapping("/callback")
    // public ResponseEntity<?> vnpayCallback(@RequestParam Map<String, String> params) {
    //     try {
    //         // Verify signature
    //         boolean isValid = vnpayService.verifyCallback(params);
    //
    //         if (!isValid) {
    //             return ResponseEntity.badRequest().body(Map.of(
    //                     "RspCode", "97",
    //                     "Message", "Invalid signature"
    //             ));
    //         }
    //
    //         // Lấy thông tin giao dịch
    //         String vnp_ResponseCode = params.get("vnp_ResponseCode");
    //         String vnp_TxnRef = params.get("vnp_TxnRef"); // orderId
    //         String vnp_Amount = params.get("vnp_Amount");
    //         String vnp_TransactionNo = params.get("vnp_TransactionNo");
    //         String vnp_PayDate = params.get("vnp_PayDate");
    //
    //         // Kiểm tra kết quả thanh toán
    //         if ("00".equals(vnp_ResponseCode)) {
    //             // Thanh toán thành công
    //         } else {
    //             // Thanh toán thất bại
    //             System.out.println("❌ Payment failed: " + vnp_TxnRef + " - Code: " + vnp_ResponseCode);
    //
    //             return ResponseEntity.ok(Map.of(
    //                     "RspCode", vnp_ResponseCode,
    //                     "Message", "Payment failed",
    //                     "orderId", vnp_TxnRef
    //             ));
    //         }
    //
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         return ResponseEntity.badRequest().body(Map.of(
    //                 "RspCode", "99",
    //                 "Message", "Unknown error: " + e.getMessage()
    //         ));
    //     }
    //     return ResponseEntity.ok(Map.of(
    //             "RspCode", "00",
    //             "Message", "Payment successful"
    //     ));
    // }

    /**
     * [OLD CODE - COMMENTED]
     * GET /api/vnpay/return - phiên bản cũ
     * 
     * ❌ Vấn đề:
     * - Duplicate logic lưu DB (đã có trong VnpayService.savePayment)
     * - Không tách orderId từ vnpTxnRef
     * - Sử dụng vnp_TxnRef làm orderId → sai khi có timestamp
     */
    // @GetMapping("/return")
    // public ResponseEntity<?> vnpayReturnOld(@RequestParam Map<String, String> params) {
    //     try {
    //         // 1. Verify signature
    //         boolean isValid = vnpayService.verifyCallback(params);
    //
    //         if (!isValid) {
    //             return ResponseEntity.badRequest().body(Map.of(
    //                     "success", false,
    //                     "message", "Invalid signature"
    //             ));
    //         }
    //
    //         // 2. Lấy thông tin giao dịch
    //         String vnp_ResponseCode = params.get("vnp_ResponseCode");
    //         String vnp_TxnRef = params.get("vnp_TxnRef");
    //         String vnp_Amount = params.get("vnp_Amount");
    //         String vnp_TransactionNo = params.get("vnp_TransactionNo");
    //         String vnp_BankCode = params.get("vnp_BankCode");
    //
    //         vnpayService.savePayment(vnp_TxnRef, vnp_Amount, vnp_TransactionNo, vnp_BankCode, vnp_ResponseCode);
    //         
    //         // 3. Tìm Payment trong DB
    //         Optional<Payment> payment = paymentRepo.findByVnpOrderId(vnp_TxnRef);
    //         if (payment.isEmpty()) {
    //             return ResponseEntity.badRequest().body(Map.of(
    //                     "success", false,
    //                     "message", "Order not found"
    //             ));
    //         }
    //
    //         // 4. ✅ UPDATE DB (duplicate với savePayment)
    //         if ("00".equals(vnp_ResponseCode)) {
    //             // Thanh toán thành công
    //             payment.get().setPaidStatus(PaymentStatus.PAID);
    //             payment.get().setTransactionCode(vnp_TransactionNo);
    //             payment.get().setResponseCode(vnp_ResponseCode);
    //             payment.get().setBankCode(vnp_BankCode);
    //             payment.get().setUpdateAt(new Date());
    //             payment.get().setProviderResponse(params.toString());
    //             paymentRepo.save(payment.get());
    //
    //             System.out.println("✅ Payment successful (return): " + vnp_TxnRef);
    //         } else {
    //             // Thanh toán thất bại
    //             payment.get().setPaidStatus(PaymentStatus.FAILED);
    //             payment.get().setResponseCode(vnp_ResponseCode);
    //             payment.get().setUpdateAt(new Date());
    //             payment.get().setProviderResponse(params.toString());
    //             paymentRepo.save(payment.get());
    //
    //             System.out.println("❌ Payment failed (return): " + vnp_TxnRef + " - Code: " + vnp_ResponseCode);
    //         }
    //
    //         // 5. Trả về response cho frontend
    //         Map<String, Object> response = new HashMap<>();
    //         response.put("orderId", vnp_TxnRef);
    //         response.put("amount", Long.parseLong(vnp_Amount) / 100);
    //         response.put("transactionNo", vnp_TransactionNo);
    //         response.put("bankCode", vnp_BankCode);
    //         response.put("responseCode", vnp_ResponseCode);
    //
    //         if ("00".equals(vnp_ResponseCode)) {
    //             response.put("success", true);
    //             response.put("message", "Payment successful");
    //         } else {
    //             response.put("success", false);
    //             response.put("message", getResponseMessage(vnp_ResponseCode));
    //         }
    //
    //         return ResponseEntity.ok(response);
    //
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         return ResponseEntity.badRequest().body(Map.of(
    //                 "success", false,
    //                 "message", "Error processing return: " + e.getMessage()
    //         ));
    //     }
    // }

    /**
     * [OLD CODE - COMMENTED]
     * Lấy message từ response code
     * 
     * ✅ Đã chuyển sang VnpayService.getResponseCodeMessage()
     */
    // private String getResponseMessage(String responseCode) {
    //     switch (responseCode) {
    //         case "00": return "Giao dịch thành công";
    //         case "07": return "Trừ tiền thành công. Giao dịch bị nghi ngờ (liên quan tới lừa đảo, giao dịch bất thường).";
    //         case "09": return "Giao dịch không thành công do: Thẻ/Tài khoản của khách hàng chưa đăng ký dịch vụ InternetBanking tại ngân hàng.";
    //         case "10": return "Giao dịch không thành công do: Khách hàng xác thực thông tin thẻ/tài khoản không đúng quá 3 lần";
    //         case "11": return "Giao dịch không thành công do: Đã hết hạn chờ thanh toán. Xin quý khách vui lòng thực hiện lại giao dịch.";
    //         case "12": return "Giao dịch không thành công do: Thẻ/Tài khoản của khách hàng bị khóa.";
    //         case "13": return "Giao dịch không thành công do Quý khách nhập sai mật khẩu xác thực giao dịch (OTP). Xin quý khách vui lòng thực hiện lại giao dịch.";
    //         case "24": return "Giao dịch không thành công do: Khách hàng hủy giao dịch";
    //         case "51": return "Giao dịch không thành công do: Tài khoản của quý khách không đủ số dư để thực hiện giao dịch.";
    //         case "65": return "Giao dịch không thành công do: Tài khoản của Quý khách đã vượt quá hạn mức giao dịch trong ngày.";
    //         case "75": return "Ngân hàng thanh toán đang bảo trì.";
    //         case "79": return "Giao dịch không thành công do: KH nhập sai mật khẩu thanh toán quá số lần quy định. Xin quý khách vui lòng thực hiện lại giao dịch";
    //         default: return "Giao dịch thất bại";
    //     }
    // }

}
