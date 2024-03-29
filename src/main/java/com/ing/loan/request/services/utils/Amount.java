package com.ing.loan.request.services.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Amount {
    LOAN_MIN(500.0),
    LOAN_MAX(12000.50);

    // Other numerical ENUMs related to amount Validation

    private final double value;

}
