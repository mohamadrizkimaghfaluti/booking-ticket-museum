package com.java.bookingticketmuseum.model;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "mst_customer")
public class Customer {
    @Id
    @NotNull
    @Column(name = "id_customer")
    private String customerId;

    @NotNull
    @Column(name = "customer_name", length = 50)
    private String customerName;

    @NotNull
    @Column(name = "customer_age")
    private int customerAge;

    @NotNull
    @Column(name = "customer_address", length = 50)
    private String customerAddress;

    @NotNull
    @Column(name = "customer_phone_number")
    private long customerPhoneNumber;

    @NotNull
    @OneToOne
    @JoinColumn(name = "country_code")
    private Country country;
}