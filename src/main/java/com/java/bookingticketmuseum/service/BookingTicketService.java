package com.java.bookingticketmuseum.service;

import com.java.bookingticketmuseum.dto.BookingTicketRequestDto;
import com.java.bookingticketmuseum.dto.BookingTicketResponseDto;
import com.java.bookingticketmuseum.dto.DetailTransactionDto;

import java.util.List;

public interface BookingTicketService {
    public BookingTicketResponseDto createTransaction(BookingTicketRequestDto requestDto) throws Exception;

    public List<BookingTicketResponseDto> readTransaction();

    public BookingTicketResponseDto updateTransaction(BookingTicketRequestDto requestDto) throws Exception;

    public DetailTransactionDto detailTransaction(String ticketCode) throws Exception;
}
