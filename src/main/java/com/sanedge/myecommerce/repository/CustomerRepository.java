package com.sanedge.myecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sanedge.myecommerce.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
