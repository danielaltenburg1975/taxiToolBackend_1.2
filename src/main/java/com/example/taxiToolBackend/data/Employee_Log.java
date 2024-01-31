package com.example.taxiToolBackend.data;



import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
@Entity(name = "Employee_Log")
public class Employee_Log extends AbstractLogin {

    @ManyToOne
    private Employees employees;


}
