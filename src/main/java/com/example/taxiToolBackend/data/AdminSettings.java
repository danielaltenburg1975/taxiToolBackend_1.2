package com.example.taxiToolBackend.data;


import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity(name = "Admin_Settings")
public class AdminSettings extends AbstractEntity {
    @NotNull
    private String gebiet;

    @NotNull
    private String grundgebuer;

    @NotNull
    private String kilometerpreis;

    @NotNull
    private String anfahrt;

    @NotNull
    private String grossraum;

}