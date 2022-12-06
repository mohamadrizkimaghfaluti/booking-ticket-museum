package com.java.bookingticketmuseum.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CountryResponseDto {
    private String countryCode;
    private String country;
}
