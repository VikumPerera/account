package com.microservice.account.Service.impl;

import com.microservice.account.Service.CustomersService;
import com.microservice.account.Service.client.CardFeignClient;
import com.microservice.account.Service.client.LoanFeignClient;
import com.microservice.account.dto.AccountDTO;
import com.microservice.account.dto.CustomerDetailsDTO;
import com.microservice.account.dto.external.CardDTO;
import com.microservice.account.dto.external.LoanDTO;
import com.microservice.account.entity.Account;
import com.microservice.account.entity.Customer;
import com.microservice.account.exception.ResourceNotFoundException;
import com.microservice.account.mapper.AccountMapper;
import com.microservice.account.mapper.CustomerMapper;
import com.microservice.account.repository.AccountRepository;
import com.microservice.account.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomersServiceImpl implements CustomersService {

    private AccountRepository accountRepository;
    private CustomerRepository customerRepository;
    private CardFeignClient cardFeignClient;
    private LoanFeignClient loanFeignClient;

    @Override
    public CustomerDetailsDTO fetchCustomerDetails(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        Account account = accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );

        CustomerDetailsDTO customerDetailsDTO = CustomerMapper.mapToCustomerDetailsDTO(customer, new CustomerDetailsDTO());
        customerDetailsDTO.setAccountDTO(AccountMapper.mapToAccountDTO(account, new AccountDTO()));

        ResponseEntity<LoanDTO> loanDTOResponseEntity = loanFeignClient.fetchLoanDetails(mobileNumber);
        customerDetailsDTO.setLoanDTO(loanDTOResponseEntity.getBody());

        ResponseEntity<CardDTO> cardDTOResponseEntity = cardFeignClient.fetchCardDetails(mobileNumber);
        customerDetailsDTO.setCardDTO(cardDTOResponseEntity.getBody());

        return customerDetailsDTO;

    }
}
