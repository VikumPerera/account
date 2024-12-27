package com.microservice.account.Service.client;

import com.microservice.account.dto.external.LoanDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="loan",fallback = LoanFallback.class)
public interface LoanFeignClient {

    @GetMapping(value = "/v1/loans", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoanDTO> fetchLoanDetails(@RequestHeader ("eazybank-correlation-id") String correlationId, @RequestParam String mobileNumber);

}
