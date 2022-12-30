package com.java.bookingticketmuseum.dto;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CountryResponseDto {
    private String countryCode;
    private String country;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CountryResponseDto that = (CountryResponseDto) o;
        return Objects.equals(countryCode, that.countryCode) && Objects.equals(country, that.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(countryCode, country);
    }
}
