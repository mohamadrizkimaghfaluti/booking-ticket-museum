package com.java.bookingticketmuseum.service;

import com.java.bookingticketmuseum.dto.BookingTicketRequestDto;
import com.java.bookingticketmuseum.dto.BookingTicketResponseDto;

import java.util.List;

public interface BookingTicketService {
    public BookingTicketResponseDto createTransaction(BookingTicketRequestDto requestDto) throws Exception;

    public List<BookingTicketResponseDto> readTransaction();

}
