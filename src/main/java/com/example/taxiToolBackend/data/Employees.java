package com.example.taxiToolBackend.data;


import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
@Entity(name = "employees")
public class Employees extends AbstractEntity   {


    private String personalID;
    private String name;
    private String adresse;
    private String telefon;
    private String email;
    private String geburtstag;
    private String ein_austritt;
    private String urlaubsanspruch;
    private String pensum;
    private String krankenversicherung;
    private String sozialversicherung;



}
