package com.java.bookingticketmuseum.service;

import com.java.bookingticketmuseum.dto.CountryRequestDto;
import com.java.bookingticketmuseum.dto.CountryResponseDto;

import java.util.List;

public interface CountryService {
    public CountryResponseDto createCountry(CountryRequestDto requestDto) throws Exception;

    public List<CountryResponseDto> readCountry();

    public CountryResponseDto updateCountry(CountryRequestDto requestDto) throws Exception;

    public void deleteCountry(String countryCode) throws Exception;
}
