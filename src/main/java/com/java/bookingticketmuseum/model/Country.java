package com.java.bookingticketmuseum.model;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "mst_country")
public class Country {

    @Id
    @NotNull
    @Column(name = "countryId")
    private String countryId;

    @NotNull
    @Column(name = "country_code", length = 10)
    private String countryCode;

    @NotNull
    @Column(name = "country", length = 30)
    private String country;
}
