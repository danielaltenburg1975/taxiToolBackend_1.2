package com.example.taxiToolBackend.data;







import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
@Entity(name = "Time_Booking")
public class Time_Recording extends AbstractEntity {


    private String zeitbuchung;
    private String personalnummer;
    private String gebucht;








}
