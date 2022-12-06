package com.java.bookingticketmuseum.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CountryRequestDto {
    private String countryId;
    private String countryCode;
    private String country;
}
