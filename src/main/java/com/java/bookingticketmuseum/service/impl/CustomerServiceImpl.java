package com.java.bookingticketmuseum.service.impl;

import com.java.bookingticketmuseum.dto.CustomerRequestDto;
import com.java.bookingticketmuseum.dto.CustomerResponseDto;
import com.java.bookingticketmuseum.model.Country;
import com.java.bookingticketmuseum.model.Customer;
import com.java.bookingticketmuseum.repository.CountryRepository;
import com.java.bookingticketmuseum.repository.CustomerRepository;
import com.java.bookingticketmuseum.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Override
    public CustomerResponseDto createCustomer(CustomerRequestDto requestDto) throws Exception {
        String customerId = generateAndCheckCustomerId(requestDto.getCustomerId());
        Customer customer = buildModelCustomerFromRequest(customerId, requestDto);
        CustomerResponseDto responseDto = buildResponseCustomerFromModel(customer);
        return responseDto;
    }

    @Override
    public List<CustomerResponseDto> readCustomer() {
        List<Customer> customerList = customerRepository.findAll();
        List<CustomerResponseDto> responseDto = buildResponseReadFromModel(customerList);
        return responseDto;
    }

    @Override
    public CustomerResponseDto updateCustomer(CustomerRequestDto requestDto) throws Exception {
        this.checkCustomerIdForUpdateAndDelete(requestDto.getCustomerId());
        Customer updateCustomer = buildModelCustomerFromRequest(requestDto.getCustomerId(), requestDto);
        CustomerResponseDto responseDto = buildResponseCustomerFromModel(updateCustomer);
        return responseDto;
    }

    @Override
    public void deleteCustomer(String customerId) throws Exception {
        this.checkCustomerIdForUpdateAndDelete(customerId);
        customerRepository.deleteById(customerId);
    }

    private List<CustomerResponseDto> buildResponseReadFromModel(List<Customer> customerList) {
        List<CustomerResponseDto> responseDtoList = new ArrayList<>();
        for (Customer customer : customerList){
            CustomerResponseDto responseDto = CustomerResponseDto.builder()
                    .customerName(customer.getCustomerName())
                    .customerAge(customer.getCustomerAge())
                    .customerAddress(customer.getCustomerAddress())
                    .customerPhoneNumber(customer.getCustomerPhoneNumber())
                    .country(customer.getCountry())
                    .build();
            responseDtoList.add(responseDto);
        }
        return responseDtoList;
    }

    private CustomerResponseDto buildResponseCustomerFromModel(Customer customer) {
        CustomerResponseDto responseDto = CustomerResponseDto.builder()
                .customerName(customer.getCustomerName())
                .customerAge(customer.getCustomerAge())
                .customerAddress(customer.getCustomerAddress())
                .customerPhoneNumber(customer.getCustomerPhoneNumber())
                .country(customer.getCountry())
                .build();
        return responseDto;
    }

    private Customer buildModelCustomerFromRequest(String customerId, CustomerRequestDto requestDto)
            throws Exception {
        Country country = checkCountryId(requestDto);
        Customer customer = Customer.builder()
                .customerId(customerId)
                .customerName(requestDto.getCustomerName())
                .customerAge(requestDto.getCustomerAge())
                .customerAddress(requestDto.getCustomerAddress())
                .customerPhoneNumber(requestDto.getCustomerPhoneNumber())
                .country(country)
                .build();
        Customer saveCustomer = customerRepository.save(customer);
        return saveCustomer;
    }

    private Country checkCountryId(CustomerRequestDto requestDto) throws Exception {
        Country country = new Country();
        List<Country> countryList = countryRepository.findAll();
        for (Country country1: countryList){
            if (country1.getCountryId().equals(requestDto.getCountry())){
                country = insertCountry(country1);
            }
        }
        return country;
    }

    private Country insertCountry(Country country1) {
        Country country = Country.builder()
                .countryId(country1.getCountryId())
                .countryCode(country1.getCountryCode())
                .country(country1.getCountry())
                .build();
        return country;
    }

    private void checkCustomerIdForUpdateAndDelete(String customerId) throws Exception {
        String tempCustomerId = "";
        String tempCustomerId2 = "";
        List<Customer> customerList = customerRepository.findAll();
        for (Customer customer : customerList){
            if (customer.getCustomerId().equals(customerId)){
                tempCustomerId = customer.getCustomerId();
            }
        }
        tempCustomerId2 = tempCustomerId;
        if (tempCustomerId2.equals("") || tempCustomerId2 == null){
            throw new Exception("Customer ID: " + customerId + " Not Found!");
        }
    }

    private String generateAndCheckCustomerId(String customerId) throws Exception {
        this.checkCustomerId(customerId);
        String resultCostumerId = null;
        if (customerId == null || customerId.equals("")){
            resultCostumerId = UUID.randomUUID().toString();
        } else {
        resultCostumerId = customerId;
        }
        return resultCostumerId;
    }

    private void checkCustomerId(String customerId) throws Exception {
        String tempCustomerId = "";
        String tempCustomerId2 = "";
        List<Customer> customerList = customerRepository.findAll();
        for (Customer customer : customerList){
            if (customer.getCustomerId().equals(customerId)
                    || customer.getCustomerId() == customerId){
                tempCustomerId = customer.getCustomerId();
            }
        }
        tempCustomerId2 = tempCustomerId;
        if (!tempCustomerId2.isEmpty() && tempCustomerId2.equals(customerId)){
            throw new Exception("Customer ID: " + customerId + " already exists!");
        }
    }
}
