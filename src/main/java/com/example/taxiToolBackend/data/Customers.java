package com.example.taxiToolBackend.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
@Entity(name = "customers")
public class Customers extends AbstractEntity{

    private String fahrgast;
    private String adresse;
    private String telefon;
    @JsonIgnore //The email address should not be transferred as json
    private String email;
    private String kostentraeger;


}
