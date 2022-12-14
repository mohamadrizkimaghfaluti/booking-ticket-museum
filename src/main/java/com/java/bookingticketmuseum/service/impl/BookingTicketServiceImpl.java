package com.java.bookingticketmuseum.service.impl;

import com.java.bookingticketmuseum.dto.BookingTicketRequestDto;
import com.java.bookingticketmuseum.dto.BookingTicketResponseDto;
import com.java.bookingticketmuseum.dto.DetailTransactionDto;
import com.java.bookingticketmuseum.model.BookingTicket;
import com.java.bookingticketmuseum.model.Country;
import com.java.bookingticketmuseum.model.Customer;
import com.java.bookingticketmuseum.repository.BookingTicketRepository;
import com.java.bookingticketmuseum.repository.CountryRepository;
import com.java.bookingticketmuseum.repository.CustomerRepository;
import com.java.bookingticketmuseum.service.BookingTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class BookingTicketServiceImpl implements BookingTicketService {
    @Autowired
    BookingTicketRepository ticketRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CountryRepository countryRepository;

    @Override
    public BookingTicketResponseDto createTransaction(BookingTicketRequestDto requestDto) throws Exception {
        //this.checkTicketCode(requestDto.getBookingTicketCode());
        String ticketCode = generateAndCheckTicketCode(requestDto.getBookingTicketCode());
        BookingTicket ticket = buildModelTicketFromRequest(ticketCode, requestDto);
        BookingTicketResponseDto responseDto = buildResponseTicketFromModel(ticket);
        return responseDto;
    }

    @Override
    public List<BookingTicketResponseDto> readTransaction() {
        List<BookingTicket> bookingTickets = ticketRepository.findAll();
        List<BookingTicketResponseDto> responseDtoList =
                buildResponseReadTicketFromModel(bookingTickets);
        return responseDtoList;
    }

    @Override
    public BookingTicketResponseDto updateTransaction(BookingTicketRequestDto requestDto)
            throws Exception {
        this.checkTicketIdForUpdate(requestDto.getBookingTicketCode());
        BookingTicket updateBookingTicket = buildModelTicketFromRequest(requestDto
                .getBookingTicketCode(), requestDto);
        BookingTicketResponseDto responseDto = buildResponseTicketFromModel(updateBookingTicket);
        return responseDto;
    }

    @Override
    public DetailTransactionDto detailTransaction(String ticketCode) throws Exception {
        this.checkTicketIdForUpdate(ticketCode);
        DetailTransactionDto transactionDto = new DetailTransactionDto();
        List<DetailTransactionDto> transactionDtoList = ticketRepository.getDetailTransaction(ticketCode);
        if (transactionDtoList.isEmpty()){
            throw new Exception("Transaction Not Found!");
        } else {
            transactionDto = setDetailTransaction(transactionDtoList);
        }
        return transactionDto;
    }

    private DetailTransactionDto setDetailTransaction(List<DetailTransactionDto> transactionDtoList) {
        DetailTransactionDto transactionDto = new DetailTransactionDto();
        for (DetailTransactionDto dto: transactionDtoList){
            transactionDto.setBookingTicketCode(dto.getBookingTicketCode());
            transactionDto.setBookingTicketDate(dto.getBookingTicketDate());
            transactionDto.setForDate(dto.getForDate());
            transactionDto.setTotalPrice(dto.getTotalPrice());
            transactionDto.setCustomerName(dto.getCustomerName());
            transactionDto.setCustomerAge(dto.getCustomerAge());
            transactionDto.setCustomerAddress(dto.getCustomerAddress());
            transactionDto.setCustomerPhoneNumber(dto.getCustomerPhoneNumber());
            transactionDto.setCountry(dto.getCountry());
        }
        return transactionDto;
    }

    private List<BookingTicketResponseDto> buildResponseReadTicketFromModel(List<BookingTicket> bookingTickets) {
        List<BookingTicketResponseDto> responseDtoList = new ArrayList<>();
        for (BookingTicket ticket : bookingTickets){
            if (ticket.getCustomer().getDeleteStatus() == 0){
                BookingTicketResponseDto responseDto = BookingTicketResponseDto.builder()
                        .bookingTicketCode(ticket.getBookingTicketCode())
                        .bookingTicketDate(ticket.getBookingTicketDate())
                        .forDate(ticket.getForDate())
                        .totalPrice(ticket.getTotalPrice())
                        .customer(ticket.getCustomer())
                        .build();
                responseDtoList.add(responseDto);
            }
        }
        return responseDtoList;
    }

    private BookingTicketResponseDto buildResponseTicketFromModel(BookingTicket ticket) {
        BookingTicketResponseDto responseDto = BookingTicketResponseDto.builder()
                .bookingTicketCode(ticket.getBookingTicketCode())
                .bookingTicketDate(ticket.getBookingTicketDate())
                .forDate(ticket.getForDate())
                .totalPrice(ticket.getTotalPrice())
                .customer(ticket.getCustomer())
                .build();
        return responseDto;
    }

    private BookingTicket buildModelTicketFromRequest(String ticketCode
            , BookingTicketRequestDto requestDto) throws ParseException {
        LocalDate today = LocalDate.now();
        double resultCalculation = resultOfCalculation(requestDto);

        Customer customer = checkCustomerId(requestDto.getCustomer());

        BookingTicket ticket = BookingTicket.builder()
                .bookingTicketCode(ticketCode)
                .bookingTicketDate(today)
                .forDate(requestDto.getForDate())
                .totalPrice(resultCalculation)
                .customer(customer)
                .build();
        ticketRepository.save(ticket);
        return ticket;
    }

    private double resultOfCalculation(BookingTicketRequestDto requestDto) {
        Customer customer = checkCustomerId(requestDto.getCustomer());
        double price;
        if (customer.getCountry().getCountryCode().equals("ID")){
            if (customer.getCustomerAge() > 5 && customer.getCustomerAge() < 18){
                price = 50000;
            } else if (customer.getCustomerAge() > 17){
                price = 100000;
            } else {
                //free
                price = 0;
            }
        } else {
            if (customer.getCustomerAge() > 5 && customer.getCustomerAge() < 18){
                price = 100000;
            } else if (customer.getCustomerAge() > 17){
                price = 200000;
            } else {
                //free
                price = 0;
            }
        }
        return price;
    }

    private Customer checkCustomerId(String customerId) {
        List<Customer> customerList = customerRepository.findAll();
        Customer customerResult = new Customer();
        for (Customer customer: customerList){
            if (customer.getCustomerId().equals(customerId)){
                customerResult = inputCustomer(customer);
            }
        }
        return customerResult;
    }

    private Customer inputCustomer(Customer customer) {
        Country country = checkCountryId(customer.getCountry());
        Customer customer1 = Customer.builder()
                .customerId(customer.getCustomerId())
                .customerName(customer.getCustomerName())
                .customerAge(customer.getCustomerAge())
                .customerAddress(customer.getCustomerAddress())
                .customerPhoneNumber(customer.getCustomerPhoneNumber())
                .country(country)
                .build();
        return customer1;
    }

    private Country checkCountryId(Country country) {
        List<Country> countryList = countryRepository.findAll();
        Country resultCountry = new Country();
        for (Country country1: countryList){
            if (country1.getCountryId().equals(country.getCountryId())){
                resultCountry = inputCountry(country1);
            }
        }
        return resultCountry;
    }

    private Country inputCountry(Country country1) {
        Country country = Country.builder()
                .countryId(country1.getCountryId())
                .countryCode(country1.getCountryCode())
                .country(country1.getCountry())
                .build();
        return country;
    }

    private void checkTicketCode(String ticketCode) throws Exception {
        String tempTicketCode = "";
        String tempTicketCode2 = "";
        List<BookingTicket> bookingTickets = ticketRepository.findAll();
        for (BookingTicket ticket : bookingTickets){
            if (ticket.getBookingTicketCode().equals(ticketCode)
                    || ticket.getBookingTicketCode() == ticketCode){
                tempTicketCode = ticket.getBookingTicketCode();
            }
        }
        tempTicketCode2 = tempTicketCode;
        if (!tempTicketCode2.isEmpty() && tempTicketCode.equals(ticketCode)){
            throw new Exception("Ticket Code " + ticketCode + " already exists!");
        }
    }

    private void checkTicketIdForUpdate(String ticketId) throws Exception {
        String tempTicketId = "";
        String tempTickedId2 = "";
        List<BookingTicket> bookingTicketList = ticketRepository.findAll();
        for (BookingTicket ticket : bookingTicketList){
            if (ticket.getBookingTicketCode().equals(ticketId)){
                tempTicketId = ticket.getBookingTicketCode();
            }
            tempTickedId2 = tempTicketId;
        }
        if (tempTickedId2.equals("") || tempTickedId2 == null){
            throw new Exception("Ticked Code " + ticketId + " Not Found");
        }
    }

    private String generateAndCheckTicketCode(String bookingTicketCode) throws Exception {
        this.checkTicketCode(bookingTicketCode);
        String resultTicketCode = null;
        if (bookingTicketCode == null || bookingTicketCode.equals("")){
            resultTicketCode = UUID.randomUUID().toString();
        } else {
            resultTicketCode = bookingTicketCode;
        }
        return resultTicketCode;
    }
}