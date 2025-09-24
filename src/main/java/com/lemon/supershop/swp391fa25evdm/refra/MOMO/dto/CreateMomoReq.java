package com.lemon.supershop.swp391fa25evdm.refra.MOMO.dto;


public class CreateMomoReq {
    private String parnerCode;
    private String reqType;
    private String ipnUrl;
    private String orderId;
    private long amount;
    private String requestId;
    private String redirectUrl;
    private String extraData;
    private String lang;
    private String signature;

    public CreateMomoReq() {
    }

    public CreateMomoReq(String parnerCode, String reqType, String ipnUrl, String orderId, long amount, String requestId, String redirectUrl, String extraData, String lang, String signature) {
        this.parnerCode = parnerCode;
        this.reqType = reqType;
        this.ipnUrl = ipnUrl;
        this.orderId = orderId;
        this.amount = amount;
        this.requestId = requestId;
        this.redirectUrl = redirectUrl;
        this.extraData = extraData;
        this.lang = lang;
        this.signature = signature;
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

    public String getOrderId() {
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

    public String getExtraData() {
        return extraData;
    }

    public String getLang() {
        return lang;
    }
}
