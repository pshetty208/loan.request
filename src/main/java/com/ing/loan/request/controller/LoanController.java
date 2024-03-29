package com.ing.loan.request.controller;
import com.ing.loan.request.dto.LoanRequest;
import com.ing.loan.request.dto.LoanResponse;
import com.ing.loan.request.exception.CustomerNotFoundException;
import com.ing.loan.request.exception.IncorrectLoanAmountException;
import com.ing.loan.request.models.Loan;
import com.ing.loan.request.services.LoanRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loan")
public class LoanController {

    @Autowired
    private LoanRequestService loanService;

    // Create new loan request
    @PostMapping
    public LoanResponse createLoanRequest(@RequestBody LoanRequest loanRequest) throws IncorrectLoanAmountException, CustomerNotFoundException {
        return loanService.createLoan(loanRequest);
    }

    // Get Total Loan Amount By CustomerId
    @GetMapping("/{customerId}")
    public double getTotalLoanByCustomerId(@PathVariable Long customerId) {
        return loanService.getLoanAmountByCustomerId(customerId);
    }

    //Other End points

}
