package com.ing.loan.request.dto;

import jakarta.persistence.Column;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
public class LoanResponse {

    private double amount;

    private String customerFullName;

    private Long customerId;

    private String message;


}
