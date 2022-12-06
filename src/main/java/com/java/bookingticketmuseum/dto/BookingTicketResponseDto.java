package com.java.bookingticketmuseum.dto;

import com.java.bookingticketmuseum.model.Customer;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingTicketResponseDto {
    private String bookingTicketCode;
    private LocalDate bookingTicketDate;
    private String forDate;
    private double totalPrice;
    private Customer customer;
}
