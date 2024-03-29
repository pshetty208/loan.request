package com.ing.loan.request.models;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "loan")
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Loan {

    @Id
    @SequenceGenerator( name = "id_generator", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "id_generator")
    // Use UUID
    private Long id; // Private to the bank

    private double amount;

    private String customerFullName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "customerId")
    private Customer customer;

    // Other fields - more internal information on the loan

}
