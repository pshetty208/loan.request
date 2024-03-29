package com.ing.loan.request.models;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "customer")
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Customer {

    @Id
    @SequenceGenerator( name = "customer_id_generator", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "customer_id_generator")
    // Use UUID
    private Long id; //Private to the bank

    @Column(unique = true) // Make customerId column unique
    // Use UUID
    private Long customerId;

    // Split customer full name into first name and last name
    private String customerFullName;

    //Other fields - more internal information on the customer

}
