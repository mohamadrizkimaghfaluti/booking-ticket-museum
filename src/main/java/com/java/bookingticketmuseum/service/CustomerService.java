package com.java.bookingticketmuseum.service;

import com.java.bookingticketmuseum.dto.CustomerRequestDto;
import com.java.bookingticketmuseum.dto.CustomerResponseDto;

import java.util.List;

public interface CustomerService {
    public CustomerResponseDto createCustomer(CustomerRequestDto requestDto) throws Exception;

    public List<CustomerResponseDto> readCustomer();

    public CustomerResponseDto updateCustomer(CustomerRequestDto requestDto) throws Exception;

    public void deleteCustomer(String customerId) throws Exception;
}
