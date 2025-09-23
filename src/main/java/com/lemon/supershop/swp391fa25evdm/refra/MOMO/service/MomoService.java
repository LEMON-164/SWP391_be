package com.lemon.supershop.swp391fa25evdm.refra.MOMO.service;

import com.lemon.supershop.swp391fa25evdm.refra.MOMO.client.MomoApi;
import com.lemon.supershop.swp391fa25evdm.refra.MOMO.dto.CreateMomoReq;
import com.lemon.supershop.swp391fa25evdm.refra.MOMO.dto.CreateMomoRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MomoService {

    @Value(value = "${momo.partner-code}")
    private String PARNER_CODE;
    @Value(value = "${momo.access-key}")
    private String ACCESS_KEY;
    @Value(value = "${momo.secret-key}")
    private String SECRET_KEY;
    @Value(value = "${momo.return-url}")
    private String REDIRECT_URL;
    @Value(value = "${momo.ipn-url}")
    private String IPN_URL;
    @Value(value = "${momo.request-type}")
    private String REQUEST_TYPE;

    private final MomoApi momoApi;

    public MomoService(MomoApi momoApi) {
        this.momoApi = momoApi;
    }

//    public CreateMomoRes CreateQr(Order){
//
//        int orderId =
//
//        CreateMomoReq req = CreateMomoReq.builder();
//    }
}
