package com.example.taxiToolBackend.data;


import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Attention! The class inherits from the parent class "Abstract Login"
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Entity(name = "Customer_Log")
public class Customer_Log extends AbstractLogin{

    @NotNull
    @ManyToOne
    private Customers customers;


}
