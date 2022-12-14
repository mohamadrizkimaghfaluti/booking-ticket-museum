package com.java.bookingticketmuseum.repository;

import com.java.bookingticketmuseum.dto.DetailTransactionDto;
import com.java.bookingticketmuseum.model.BookingTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BookingTicketRepository extends JpaRepository<BookingTicket, String> {

    @Query(nativeQuery = true)
    List<DetailTransactionDto> getDetailTransaction(@Param("ticketCode") String ticketCode);
}
