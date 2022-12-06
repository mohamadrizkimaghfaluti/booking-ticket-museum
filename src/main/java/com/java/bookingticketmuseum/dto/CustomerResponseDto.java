package com.java.bookingticketmuseum.dto;

import com.java.bookingticketmuseum.model.Country;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponseDto {
    private String customerName;
    private int customerAge;
    private String customerAddress;
    private long customerPhoneNumber;
    private Country country;
}
