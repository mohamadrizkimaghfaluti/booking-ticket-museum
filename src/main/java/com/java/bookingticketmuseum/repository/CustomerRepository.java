package com.java.bookingticketmuseum.repository;

import com.java.bookingticketmuseum.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import javax.websocket.server.PathParam;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

    @Modifying
    @Transactional
    @Query(value = "update mst_customer set delete_status = -1 where id_customer = :customerId"
            , nativeQuery = true)
    void softDeleteProcess(@Param("customerId") String customerId);
}
