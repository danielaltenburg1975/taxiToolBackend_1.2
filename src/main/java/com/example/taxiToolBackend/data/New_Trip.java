package com.example.taxiToolBackend.data;




import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
@Entity(name = "new_trip")
public class New_Trip extends AbstractEntity {
    private String zeit;
    private String fahrgast;
    private String abholort;
    private String zielort;
    private String anmerkung;
    private String fahrer;
    private String gebucht;


    @ManyToOne
    private Customers customers;

    @ManyToOne
    private Employees employees;


}