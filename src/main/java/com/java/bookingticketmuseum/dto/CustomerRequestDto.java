package com.java.bookingticketmuseum.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CustomerRequestDto {
    private String customerId;
    private String customerName;
    private int customerAge;
    private String customerAddress;
    private long customerPhoneNumber;
    private String country;
}
