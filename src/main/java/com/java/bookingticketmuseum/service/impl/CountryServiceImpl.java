package com.java.bookingticketmuseum.service.impl;

import com.java.bookingticketmuseum.dto.CountryRequestDto;
import com.java.bookingticketmuseum.dto.CountryResponseDto;
import com.java.bookingticketmuseum.model.Country;
import com.java.bookingticketmuseum.repository.CountryRepository;
import com.java.bookingticketmuseum.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CountryServiceImpl implements CountryService {
    @Autowired
    CountryRepository countryRepository;

    @Override
    public CountryResponseDto createCountry(CountryRequestDto requestDto) throws Exception {
        this.checkCountryCode(requestDto.getCountryCode());
        String countryId = generateAndCheckCountryId(requestDto.getCountryId());
        Country country = buildModelCountryFromRequest(countryId, requestDto);
        CountryResponseDto responseDto = buildResponseCountryFromModel(country);
        return responseDto;
    }

    @Override
    public List<CountryResponseDto> readCountry() {
        List<Country> countryList = countryRepository.findAll();
        List<CountryResponseDto> responseDtoList = buildResponseCountryReadFromModel(countryList);
        return responseDtoList;
    }

    @Override
    public CountryResponseDto updateCountry(CountryRequestDto requestDto) throws Exception {
        this.checkCountryIdForUpdateAndDelete(requestDto.getCountryId());
        this.checkCountryCode(requestDto.getCountryCode());
        Country country = buildModelCountryFromRequest(requestDto.getCountryId(), requestDto);
        CountryResponseDto responseDto = buildResponseCountryFromModel(country);
        return responseDto;
    }

    @Override
    public void deleteCountry(String countryCode) throws Exception {
        this.checkCountryIdForUpdateAndDelete(countryCode);
        //countryRepository.deleteById(countryCode);
        countryRepository.softDeleteProcess(countryCode);
    }

    private List<CountryResponseDto> buildResponseCountryReadFromModel(List<Country> countryList) {
        List<CountryResponseDto> responseDtoList = new ArrayList<>();
        for (int i = 0; i < countryList.size(); i++){
            if (countryList.get(i).getDeleteStatus() == 0){
                CountryResponseDto responseDto = CountryResponseDto.builder()
                        .countryCode(countryList.get(i).getCountryCode())
                        .country(countryList.get(i).getCountry())
                        .build();
                responseDtoList.add(responseDto);
            }
        }
        return responseDtoList;
    }

    private CountryResponseDto buildResponseCountryFromModel(Country country) {
        CountryResponseDto responseDto = CountryResponseDto.builder()
                .countryCode(country.getCountryCode())
                .country(country.getCountry())
                .build();
        return responseDto;
    }

    private Country buildModelCountryFromRequest(String countryId, CountryRequestDto requestDto) {
        Country country = Country.builder()
                .countryId(countryId)
                .countryCode(requestDto.getCountryCode())
                .country(requestDto.getCountry())
                .deleteStatus(0)
                .build();
        countryRepository.save(country);
        return country;
    }

    private String generateAndCheckCountryId(String countryId) throws Exception {
        this.checkCountryId(countryId);
        String resultCountryId = null;
        if (countryId == null || countryId.equals("")){
            resultCountryId = UUID.randomUUID().toString();
        } else {
            resultCountryId = countryId;
        }
        return resultCountryId;
    }

    private void checkCountryId(String countryId) throws Exception {
        List<Country> countryList = countryRepository.findAll();
        for (Country country : countryList){
            if (country.getCountryId().equals(countryId)
                    || country.getCountryId() == countryId){
                throw new Exception("Country ID already exists!");
            }
        }
    }

    private void checkCountryIdForUpdateAndDelete(String countryId) throws Exception {
        String tempCountry1 = "";
        String tempCountry2 = "";
        List<Country> countryList = countryRepository.findAll();
        for (Country country: countryList){
            if (country.getCountryId().equals(countryId)){
                tempCountry1 = country.getCountryId();
            }
            tempCountry2 = tempCountry1;
        }
        if (tempCountry2.equals("") || tempCountry2 == null){
            throw new Exception("Country ID: " + countryId + ", Not Found!");
        }
    }

    private void checkCountryCode(String countryCode) throws Exception {
        String tempCountryCode = "";
        String tempCountryCode2 = "";
        List<Country> countryList = countryRepository.findAll();
        for (Country country : countryList){
            if (country.getCountryCode().equals(countryCode)
                    || country.getCountryCode() == countryCode){
                tempCountryCode = country.getCountryCode();
            }
        }
        tempCountryCode2 = tempCountryCode;
        if (!tempCountryCode2.isEmpty() && tempCountryCode2.equals(countryCode)){
            throw new Exception("Country Code " + countryCode + " already exists!");
        }
    }
}
