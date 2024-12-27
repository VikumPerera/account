package com.microservice.account.Service.client;

import com.microservice.account.dto.external.CardDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CardFallback implements CardFeignClient{

    @Override
    public ResponseEntity<CardDTO> fetchCardDetails(String correlationId, String mobileNumber) {
        return null;
    }

}
