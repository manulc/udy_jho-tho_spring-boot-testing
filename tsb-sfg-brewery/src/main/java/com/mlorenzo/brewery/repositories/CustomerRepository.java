package com.mlorenzo.brewery.repositories;

import com.mlorenzo.brewery.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
}
