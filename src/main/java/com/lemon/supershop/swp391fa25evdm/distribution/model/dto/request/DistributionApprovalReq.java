package com.lemon.supershop.swp391fa25evdm.distribution.model.dto.request;

public class DistributionApprovalReq {
    private String decision; // "CONFIRMED" or "CANCELED"
    private String evmNotes;

    public DistributionApprovalReq() {}

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public String getEvmNotes() {
        return evmNotes;
    }

    public void setEvmNotes(String evmNotes) {
        this.evmNotes = evmNotes;
    }
}
