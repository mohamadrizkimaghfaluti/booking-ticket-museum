package com.java.bookingticketmuseum.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class BookingTicketRequestDto {
    private String bookingTicketCode;
    private String forDate;
    private String customer;
}
