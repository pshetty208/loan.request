package com.ing.loan.request.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ing.loan.request.dto.LoanRequest;
import com.ing.loan.request.dto.LoanResponse;
import com.ing.loan.request.services.LoanRequestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebMvcTest(LoanController.class)
@AutoConfigureMockMvc
class LoanControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private LoanRequestService loanRequestService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testCreateLoanRequest() throws Exception {
        var request = LoanRequest.builder()
                .amount(1000)
                .customerFullName("John Doe")
                .customerId(123456789L)
                .build();

        var response = LoanResponse.builder()
                .amount(1000.0)
                .customerFullName("John Doe")
                .customerId(123456789L)
                .build();

        when(loanRequestService.createLoan(request)).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.post("/loan")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(response)));
    }

    @Test
    void testGetTotalLoanByCustomerId() throws Exception {
        Long customerId = 123456789L;
        double expectedTotalLoanAmount = 5000.0;

        when(loanRequestService.getLoanAmountByCustomerId(customerId)).thenReturn(expectedTotalLoanAmount);

        mockMvc.perform(MockMvcRequestBuilders.get("/loan/{customerId}", customerId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(String.valueOf(expectedTotalLoanAmount)));
    }

}

