package com.java.bookingticketmuseum.controller;

import com.java.bookingticketmuseum.dto.BookingTicketRequestDto;
import com.java.bookingticketmuseum.dto.BookingTicketResponseDto;
import com.java.bookingticketmuseum.dto.DetailTransactionDto;
import com.java.bookingticketmuseum.service.BookingTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/booking-ticket/ticket")
public class BookingTicketController {
    @Autowired
    BookingTicketService ticketService;

    @PostMapping("/create")
    public ResponseEntity<BookingTicketResponseDto> create(@RequestBody BookingTicketRequestDto requestDto)
            throws Exception {
        BookingTicketResponseDto responseDto = ticketService.createTransaction(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/read")
    public ResponseEntity<List<BookingTicketResponseDto>> read(){
        List<BookingTicketResponseDto> responseDtoList = ticketService.readTransaction();
        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<BookingTicketResponseDto> update(@RequestBody BookingTicketRequestDto requestDto)
            throws Exception {
        BookingTicketResponseDto responseDto = ticketService.updateTransaction(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/detailTransaction/{ticketId}")
    public ResponseEntity<DetailTransactionDto> detailTransaction(@PathVariable("ticketId") String ticketId) throws Exception {
        DetailTransactionDto transactionDto = ticketService.detailTransaction(ticketId);
        return new ResponseEntity<>(transactionDto, HttpStatus.OK);
    }
}
