package com.microservice.account.Service.client;

import com.microservice.account.dto.external.LoanDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class LoanFallback implements LoanFeignClient{

    @Override
    public ResponseEntity<LoanDTO> fetchLoanDetails(String correlationId, String mobileNumber) {
        return null;
    }
}
