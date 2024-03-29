package com.ing.loan.request.persistence;

import com.ing.loan.request.models.Customer;
import com.ing.loan.request.models.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByCustomerId(Long customerId);

}
