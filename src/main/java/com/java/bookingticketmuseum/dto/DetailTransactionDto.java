package com.java.bookingticketmuseum.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class DetailTransactionDto {
    private String bookingTicketCode;
    private LocalDate bookingTicketDate;
    private LocalDate forDate;
    private double totalPrice;
    private String customerName;
    private int customerAge;
    private String customerAddress;
    private long customerPhoneNumber;
    private String country;
}
