package com.java.bookingticketmuseum.model;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "trx_booking_ticket")
public class BookingTicket {

    @Id
    @NotNull
    @Column(name = "booking_ticket_code")
    private String bookingTicketCode;

    @NotNull
    @Column(name = "booking_ticket_date")
    private LocalDate bookingTicketDate;

    @NotNull
    @Column(name = "for_date")
    private String forDate;

    @NotNull
    @Column(name = "total_price")
    private double totalPrice;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
