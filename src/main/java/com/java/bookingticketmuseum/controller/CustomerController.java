package com.java.bookingticketmuseum.controller;

import com.java.bookingticketmuseum.dto.CustomerRequestDto;
import com.java.bookingticketmuseum.dto.CustomerResponseDto;
import com.java.bookingticketmuseum.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/booking-ticket/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping("/create")
    public ResponseEntity<CustomerResponseDto> create(@RequestBody CustomerRequestDto requestDto)
            throws Exception {
        CustomerResponseDto responseDto = customerService.createCustomer(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/read")
    public ResponseEntity<List<CustomerResponseDto>> read(){
        List<CustomerResponseDto> responseDtoList = customerService.readCustomer();
        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<CustomerResponseDto> update(@RequestBody CustomerRequestDto requestDto)
            throws Exception {
        CustomerResponseDto responseDto = customerService.updateCustomer(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") String customerId)
            throws Exception {
        customerService.deleteCustomer(customerId);
        return new ResponseEntity<>("Delete Customer Success!", HttpStatus.OK);
    }
}
