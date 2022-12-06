package com.java.bookingticketmuseum.repository;

import com.java.bookingticketmuseum.model.BookingTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingTicketRepository extends JpaRepository<BookingTicket, String> {
}
