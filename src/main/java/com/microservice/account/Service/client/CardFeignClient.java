package com.microservice.account.Service.client;

import com.microservice.account.dto.external.CardDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("card")
public interface CardFeignClient {

    @GetMapping(value = "/v1/cards", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CardDTO> fetchCardDetails(@RequestParam String mobileNumber);

}
