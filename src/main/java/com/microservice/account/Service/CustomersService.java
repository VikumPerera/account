package com.microservice.account.Service;


import com.microservice.account.dto.CustomerDetailsDTO;

public interface CustomersService {

    CustomerDetailsDTO fetchCustomerDetails(String mobileNumber);

}
