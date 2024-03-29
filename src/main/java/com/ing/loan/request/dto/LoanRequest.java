package com.ing.loan.request.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class LoanRequest {

//    @Min(value = 500, message = "Amount must be greater than or equal to 500")
//    @Max(value = 12000.50, message = "Amount must be less than or equal to 12000.50")
    private double amount;

    private String customerFullName;

    @NotNull(message = "Customer Id cannot be null")
    private Long customerId;

}
