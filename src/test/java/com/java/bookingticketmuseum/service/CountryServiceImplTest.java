package com.java.bookingticketmuseum.service;

import com.java.bookingticketmuseum.dto.CountryRequestDto;
import com.java.bookingticketmuseum.dto.CountryResponseDto;
import com.java.bookingticketmuseum.model.Country;
import com.java.bookingticketmuseum.repository.CountryRepository;
import com.java.bookingticketmuseum.service.impl.CountryServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.Extensions;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Extensions({
        @ExtendWith(MockitoExtension.class)
})
@DisplayName("Class Test: CountryServiceImpl")
public class CountryServiceImplTest {

    @Mock
    private CountryRepository countryRepository;

    private CountryServiceImpl countryService;

    @BeforeEach
    void setUp() {
        countryService = new CountryServiceImpl(countryRepository);
        System.out.println("before each");
    }

    @Test
    @DisplayName("Method Test: createCountry()")
    void createCountryTest() throws Exception {
        CountryRequestDto requestDto = CountryRequestDto.builder()
                        .countryId("")
                        .countryCode("ID")
                        .country("Indonesia")
                        .build();

        Assertions.assertEquals("", requestDto.getCountryId());

        CountryResponseDto responseDto = countryService.createCountry(requestDto);

        Assertions.assertEquals("ID", responseDto.getCountryCode());
        Assertions.assertEquals("Indonesia", responseDto.getCountry());
    }

    @Test
    @DisplayName("Method Test: checkCountryCode()")
    void checkCountryCodeTest() throws Exception {
        List<Country> countryList = new ArrayList<>();

        Country country = new Country("1", "ID", "Indo", 0);
        Country country2 = new Country("2", "IN", "Indi", 0);

        countryList.add(country);
        countryList.add(country2);

        Mockito.when(countryRepository.findAll()).thenReturn(countryList);

        Assertions.assertThrows(Exception.class, () -> {
            countryService.checkCountryCode("IN");
        });
    }

    @Test
    @DisplayName("Method Test: generateAndCheckCountryId()")
    void generateAndCheckCountryIdTest() throws Exception {
        String countryId = countryService.generateAndCheckCountryId("");
        System.out.println(countryId);
        Assertions.assertNotNull(countryId); // generate UUID
    }

    @Test
    @DisplayName("Method Test: buildModelCountryFromRequest()")
    void buildModelCountryFromRequestTest() {
        String countryId = UUID.randomUUID().toString();
        CountryRequestDto requestDto = new CountryRequestDto("", "ID", "Indo");

        Country country = countryService.buildModelCountryFromRequest(countryId, requestDto);

        Assertions.assertNotNull(country.getCountryId());
        Assertions.assertEquals(country.getCountryCode(), "ID");
        Assertions.assertEquals(country.getCountry(), "Indo");
        Assertions.assertEquals(country.getDeleteStatus(), 0);

        Mockito.verify(countryRepository, Mockito.times(1))
                .save(country);
    }

    @Test
    @DisplayName("Method Test: buildResponseCountryFromModel()")
    void buildResponseCountryFromModelTest() {
        String countryId = UUID.randomUUID().toString();
        Country country = new Country(countryId, "ID", "Indo", 0);

        CountryResponseDto responseDto = countryService.buildResponseCountryFromModel(country);

        Assertions.assertEquals(responseDto.getCountryCode(), "ID");
        Assertions.assertEquals(responseDto.getCountry(), "Indo");
    }

    @Test
    @DisplayName("Method Test: readCountry()")
    void readCountryTest() {
        List<Country> countryList = new ArrayList<>();
        Country country = new Country("1", "ID", "Indo", 0);
        Country country2 = new Country("2", "IN", "Indi", 0);

        countryList.add(country);
        countryList.add(country2);

        Mockito.when(countryRepository.findAll()).thenReturn(countryList);

        List<CountryResponseDto> responseDtoList = countryService.readCountry();

        Assertions.assertEquals(responseDtoList.get(0), new CountryResponseDto("ID", "Indo"));
        Assertions.assertEquals(responseDtoList.get(1), new CountryResponseDto("IN", "Indi"));

        Mockito.verify(countryRepository, Mockito.times(1)).findAll();
    }

    @Test
    @DisplayName("Method Test: updateCountry()")
    void updateCountryTest() throws Exception {
        List<Country> countryList = new ArrayList<>();
        Country country = new Country("1", "ID", "Indo", 0);
        Country country2 = new Country("2", "IN", "Indi", 0);

        countryList.add(country);
        countryList.add(country2);

        Mockito.when(countryRepository.findAll()).thenReturn(countryList);

        CountryRequestDto requestDto = CountryRequestDto.builder()
                .countryId("1")
                .countryCode("SG")
                .country("Singapore")
                .build();

        CountryResponseDto responseDto = countryService.updateCountry(requestDto);

        Assertions.assertEquals(responseDto.getCountryCode(), "SG");
        Assertions.assertEquals(responseDto.getCountry(), "Singapore");

    }

    @Test
    @DisplayName("Method Test: checkCountryIdForUpdateAndDelete()")
    void checkCountryIdForUpdateAndDeleteTest() {
        List<Country> countryList = new ArrayList<>();
        Country country = new Country("1", "ID", "Indo", 0);
        Country country2 = new Country("2", "IN", "Indi", 0);

        countryList.add(country);
        countryList.add(country2);

        Mockito.when(countryRepository.findAll()).thenReturn(countryList);

         Assertions.assertThrows(Exception.class, () -> {
             countryService.checkCountryIdForUpdateAndDelete("3");
         });
    }

    @Test
    @DisplayName("Method Test: deleteCountry()")
    void deleteCountryTest() throws Exception {
        List<Country> countryList = new ArrayList<>();
        Country country = new Country("1", "ID", "Indo", 0);
        Country country2 = new Country("2", "IN", "Indi", 0);

        countryList.add(country);
        countryList.add(country2);

        Mockito.when(countryRepository.findAll()).thenReturn(countryList);

        countryService.deleteCountry("1");

        Mockito.verify(countryRepository, Mockito.times(1))
                .softDeleteProcess("1");
    }

}