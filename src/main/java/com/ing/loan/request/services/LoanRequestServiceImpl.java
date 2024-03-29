package com.ing.loan.request.services;


import com.ing.loan.request.dto.LoanRequest;
import com.ing.loan.request.dto.LoanResponse;
import com.ing.loan.request.exception.CustomerNotFoundException;
import com.ing.loan.request.exception.IncorrectLoanAmountException;
import com.ing.loan.request.models.Loan;

public interface LoanRequestServiceImpl {

    LoanResponse createLoan(LoanRequest loanRequest) throws IncorrectLoanAmountException, CustomerNotFoundException;

    double getLoanAmountByCustomerId(Long customerId);

}
