package com.java.bookingticketmuseum.model;

import com.java.bookingticketmuseum.dto.DetailTransactionDto;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@NamedNativeQuery(
        name = "BookingTicket.getDetailTransaction",
        query = "select " +
                "   b.booking_ticket_code as bookingTicketCode" +
                "   , b.booking_ticket_date as bookingTicketDate" +
                "   , b.for_date as forDate" +
                "   , b.total_price as totalPrice" +
                "   , c.customer_name as customerName" +
                "   , c.customer_age as customerAge" +
                "   , c.customer_address as customerAddress" +
                "   , c.customer_phone_number as customerPhoneNumber" +
                "   , co.country as country" +
                "   from" +
                "       trx_booking_ticket b" +
                "       join mst_customer c" +
                "           on b.customer_id = c.id_customer " +
                "       join mst_country co" +
                "           on c.country_code = co.country_id " +
                "where " +
                "   b.booking_ticket_code = :ticketCode " +
                "   and " +
                "       c.delete_status = 0 " +
                "   and " +
                "       co.delete_status = 0",
        resultSetMapping = "Mapping.DetailTransactionDto"
)
@SqlResultSetMapping(
        name = "Mapping.DetailTransactionDto",
        classes = @ConstructorResult(
                targetClass = DetailTransactionDto.class,
                columns = {
                        @ColumnResult(name = "bookingTicketCode", type = String.class),
                        @ColumnResult(name = "bookingTicketDate", type = LocalDate.class),
                        @ColumnResult(name = "forDate", type = LocalDate.class),
                        @ColumnResult(name = "totalPrice", type = double.class),
                        @ColumnResult(name = "customerName", type = String.class),
                        @ColumnResult(name = "customerAge", type = int.class),
                        @ColumnResult(name = "customerAddress", type = String.class),
                        @ColumnResult(name = "customerPhoneNumber", type = long.class),
                        @ColumnResult(name = "country", type = String.class)
                }
        )
)
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
