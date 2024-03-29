package com.ing.loan.request.services;

import com.ing.loan.request.dto.LoanRequest;
import com.ing.loan.request.dto.LoanResponse;
import com.ing.loan.request.exception.CustomerNotFoundException;
import com.ing.loan.request.exception.IncorrectLoanAmountException;
import com.ing.loan.request.models.Customer;
import com.ing.loan.request.models.Loan;
import com.ing.loan.request.persistence.CustomerRepository;
import com.ing.loan.request.persistence.LoanRepository;
import com.ing.loan.request.services.LoanRequestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LoanRequestServiceTest {

    @InjectMocks
    private LoanRequestService loanRequestService;

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private CustomerRepository customerRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateLoanWithValidInput() throws IncorrectLoanAmountException, CustomerNotFoundException {
        when(customerRepository.findByCustomerId(anyLong())).thenReturn(Collections.singletonList(Customer.builder()
                .customerId(123456789L)
                .customerFullName("John Doe")
                .id(123456780L)
                .build()));
        when(loanRepository.save(any(Loan.class))).thenReturn(Loan.builder()
                .amount(1000.0)
                .id(111111111L)
                .customerFullName("John Doe")
                .customer(Customer.builder()
                        .customerId(123456789L)
                        .customerFullName("John Doe")
                        .id(123456780L)
                        .build())
                .build());

        LoanRequest loanRequest = new LoanRequest(1000.0, "John Doe", 123456789L);

        LoanResponse loanResponse = loanRequestService.createLoan(loanRequest);

        assertNotNull(loanResponse);
        assertEquals(1000.0, loanResponse.getAmount());
        assertEquals("John Doe", loanResponse.getCustomerFullName());
        assertEquals(123456789L, loanResponse.getCustomerId());
        assertEquals("Your loan request is successfully created", loanResponse.getMessage());

        verify(customerRepository, times(1)).findByCustomerId(anyLong());
        verify(loanRepository, times(1)).save(any(Loan.class));
    }

    @Test
    public void testCreateLoanWithInvalidAmount() {
        LoanRequest loanRequest = new LoanRequest(400.0, "John Doe", 123456789L);

        assertThrows(IncorrectLoanAmountException.class, () -> loanRequestService.createLoan(loanRequest));

        verifyNoInteractions(customerRepository);
        verifyNoInteractions(loanRepository);
    }

    @Test
    public void testCreateLoanWithNonExistentCustomer() {
        when(customerRepository.findByCustomerId(anyLong())).thenReturn(Collections.emptyList());

        LoanRequest loanRequest = new LoanRequest(1000.0, "John Doe", 123456789L);

        assertThrows(CustomerNotFoundException.class, () -> loanRequestService.createLoan(loanRequest));

        verify(customerRepository, times(1)).findByCustomerId(anyLong());
        verifyNoInteractions(loanRepository);
    }

    @Test
    public void testGetLoanAmountByCustomerIdWithNoLoans() {
        // Mocking loan repository to return an empty list of loans for the given customer ID
        when(loanRepository.findByCustomerCustomerId(123456789L)).thenReturn(Collections.emptyList());

        // Calling method under test
        double totalLoanAmount = loanRequestService.getLoanAmountByCustomerId(123456789L);

        // Assertions
        assertEquals(0.0, totalLoanAmount);
    }

    @Test
    public void testGetLoanAmountByCustomerIdWithSingleLoan() {
        // Mocking loan repository to return a single loan for the given customer ID
        when(loanRepository.findByCustomerCustomerId(123456789L)).thenReturn(Collections.singletonList(Loan.builder()
                .amount(1000.0)
                .id(111111111L)
                .customerFullName("John Doe")
                .customer(Customer.builder()
                        .customerId(123456789L)
                        .customerFullName("John Doe")
                        .id(123456780L)
                        .build())
                .build()));

        double totalLoanAmount = loanRequestService.getLoanAmountByCustomerId(123456789L);

        assertEquals(1000.0, totalLoanAmount);
    }

    @Test
    public void testGetLoanAmountByCustomerIdWithMultipleLoans() {
        List<Loan> loans = Arrays.asList(
                Loan.builder()
                .amount(1000.0)
                .id(111111111L)
                .customerFullName("John Doe")
                .customer(Customer.builder()
                        .customerId(123456789L)
                        .customerFullName("John Doe")
                        .id(123456780L)
                        .build())
                .build(),
                Loan.builder()
                .amount(500.0)
                .id(111111222L)
                .customerFullName("John Doe")
                .customer(Customer.builder()
                        .customerId(123456789L)
                        .customerFullName("John Doe")
                        .id(123456780L)
                        .build())
                .build());
        when(loanRepository.findByCustomerCustomerId(123456789L)).thenReturn(loans);

        double totalLoanAmount = loanRequestService.getLoanAmountByCustomerId(123456789L);

        assertEquals(1500.0, totalLoanAmount);
    }


}
