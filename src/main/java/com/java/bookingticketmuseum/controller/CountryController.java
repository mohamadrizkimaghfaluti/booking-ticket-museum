package com.java.bookingticketmuseum.controller;

import com.java.bookingticketmuseum.dto.CountryRequestDto;
import com.java.bookingticketmuseum.dto.CountryResponseDto;
import com.java.bookingticketmuseum.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/booking-ticket/country")
public class CountryController {
    @Autowired
    private CountryService countryService;

    @PostMapping("/create")
    public ResponseEntity<CountryResponseDto> create(@RequestBody CountryRequestDto requestDto)
            throws Exception {
        CountryResponseDto responseDto = countryService.createCountry(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/read")
    public ResponseEntity<List<CountryResponseDto>> read(){
        List<CountryResponseDto> responseDtoList = countryService.readCountry();
        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<CountryResponseDto> update(@RequestBody CountryRequestDto requestDto)
            throws Exception {
        CountryResponseDto responseDto = countryService.updateCountry(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") String countryId) throws Exception {
        countryService.deleteCountry(countryId);
        return new ResponseEntity<>("Delete Country Success!", HttpStatus.OK);
    }
}
