package com.ing.loan.request.services;
import com.ing.loan.request.dto.LoanRequest;
import com.ing.loan.request.dto.LoanResponse;
import com.ing.loan.request.exception.CustomerNotFoundException;
import com.ing.loan.request.exception.IncorrectLoanAmountException;
import com.ing.loan.request.models.Customer;
import com.ing.loan.request.models.Loan;
import com.ing.loan.request.persistence.CustomerRepository;
import com.ing.loan.request.persistence.LoanRepository;
import com.ing.loan.request.services.utils.Amount;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanRequestService implements LoanRequestServiceImpl{

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    @Transactional
    public LoanResponse createLoan(LoanRequest loanRequest) throws IncorrectLoanAmountException, CustomerNotFoundException {
        var amount = loanRequest.getAmount();
        var min = Amount.LOAN_MIN.getValue();
        var max = Amount.LOAN_MAX.getValue();
        if (amount < min || amount > max) {
            throw new IncorrectLoanAmountException("Incorrect Loan amount; Loan amount must be between "+min+" and "+max);
        }

        // Check if customer exists
        var customer = customerRepository.findByCustomerId(loanRequest.getCustomerId()).stream().findFirst();
        if (customer.isEmpty()) {
            throw new CustomerNotFoundException("Customer with ID " + loanRequest.getCustomerId() + " does not exist");
        }

        var loan = loanRepository.save(Loan.builder()
                .amount(loanRequest.getAmount())
                .customerFullName(loanRequest.getCustomerFullName())
                .customer(customer.get()).build());

        return LoanResponse.builder().amount(loan.getAmount())
                .customerFullName(loan.getCustomerFullName())
                .customerId(loan.getCustomer().getCustomerId())
                .message("Your loan request is successfully created")
                .build();
    }

    @Override
    @Transactional
    public double getLoanAmountByCustomerId(Long customerId) {
        List<Loan> loans = loanRepository.findByCustomerCustomerId(customerId);
        return loans.stream()
                .mapToDouble(Loan::getAmount)
                .sum();
    }

}
