package com.mzrt.atlas_bank.application.port.out;

import com.mzrt.atlas_bank.domain.model.customer.Customer;

import java.util.Optional;

public interface CustomerRepositoryPort {
    Customer save(Customer customer);
    Optional<Customer> findById(Long id);
}
