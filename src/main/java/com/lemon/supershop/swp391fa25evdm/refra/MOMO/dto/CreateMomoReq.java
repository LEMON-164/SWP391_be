package com.lemon.supershop.swp391fa25evdm.refra.MOMO.dto;

public class CreateMomoReq {
    private String parnerCode;
    private String reqType;
    private String ipnUrl;
    private int orderId;
    private long amount;
    private String requestId;
    private String redirectUrl;
    private String signature;

    public CreateMomoReq() {
    }

    public String getParnerCode() {
        return parnerCode;
    }

    public String getReqType() {
        return reqType;
    }

    public String getIpnUrl() {
        return ipnUrl;
    }

    public int getOrderId() {
        return orderId;
    }

    public long getAmount() {
        return amount;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public String getSignature() {
        return signature;
    }
}
