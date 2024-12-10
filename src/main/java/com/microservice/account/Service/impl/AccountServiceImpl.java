package com.microservice.account.Service.impl;

import com.microservice.account.Service.AccountService;
import com.microservice.account.constants.AccountsConstants;
import com.microservice.account.dto.AccountDTO;
import com.microservice.account.dto.CustomerDTO;
import com.microservice.account.entity.Account;
import com.microservice.account.entity.Customer;
import com.microservice.account.exception.CustomerAlreadyExistsException;
import com.microservice.account.exception.ResourceNotFoundException;
import com.microservice.account.mapper.AccountMapper;
import com.microservice.account.mapper.CustomerMapper;
import com.microservice.account.repository.AccountRepository;
import com.microservice.account.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;
    private CustomerRepository customerRepository;

    @Override
    public void createAccount(CustomerDTO customerDTO) {
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDTO.getMobileNumber());
        if(optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already registered with given mobileNumber "
                    +customerDTO.getMobileNumber());
        }
        Customer customer = CustomerMapper.mapToCustomer(customerDTO, new Customer());

        Customer savedCustomer = customerRepository.save(customer);
        accountRepository.save(createNewAccount(savedCustomer));
    }

    private Account createNewAccount(Customer customer) {
        Account newAccount = new Account();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        return newAccount;
    }

    @Override
    public CustomerDTO fetchAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        Account account = accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );
        CustomerDTO customerDTO = CustomerMapper.mapToCustomerDTO(customer, new CustomerDTO());
        customerDTO.setAccountDTO(AccountMapper.mapToAccountDTO(account, new AccountDTO()));
        return customerDTO;
    }

    @Override
    public boolean updateAccount(CustomerDTO customerDTO) {
        boolean isUpdated = false;
        AccountDTO accountDTO = customerDTO.getAccountDTO();
        if(accountDTO !=null ){
            Account account = accountRepository.findById(accountDTO.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "AccountNumber", accountDTO.getAccountNumber().toString())
            );
            AccountMapper.mapToAccount(accountDTO, account);
            account = accountRepository.save(account);

            Long customerId = account.getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "CustomerID", customerId.toString())
            );
            CustomerMapper.mapToCustomer(customerDTO, customer);
            customerRepository.save(customer);
            isUpdated = true;
        }
        return  isUpdated;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        accountRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }

}
