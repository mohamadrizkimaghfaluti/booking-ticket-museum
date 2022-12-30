package com.java.bookingticketmuseum.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CountryRequestDto {
    private String countryId;
    private String countryCode;
    private String country;
}
