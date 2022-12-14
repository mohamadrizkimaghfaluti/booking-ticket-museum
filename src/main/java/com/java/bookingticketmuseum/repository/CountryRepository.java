package com.java.bookingticketmuseum.repository;

import com.java.bookingticketmuseum.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CountryRepository extends JpaRepository<Country, String> {

    @Modifying
    @Transactional
    @Query("update Country set deleteStatus = -1 where countryId = :countryId")
    void softDeleteProcess(@Param("countryId") String countryId);
}
