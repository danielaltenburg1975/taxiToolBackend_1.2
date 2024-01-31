package com.example.taxiToolBackend.data;


import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
@Entity(name="tripcollector")
public class Trip_Collector extends AbstractEntity{

    private String zeit;
    private String abholort;
    private String zielort;
    private String anmerkung;
    private String personen;
    private String auto;
    private String fahrer;
    private String bezahlung;



  @ManyToOne
    private Customers customers;


    @ManyToOne
    private Employees employees;


    // The constructor is only used for the test class
    public Trip_Collector(String zeit, String abholort, String zielort, String anmerkung,
                          String personen, String auto, String fahrer, String bezahlung,
                          Customers customers, Employees employees) {
        this.zeit = zeit;
        this.abholort = abholort;
        this.zielort = zielort;
        this.anmerkung = anmerkung;
        this.personen = personen;
        this.auto = auto;
        this.fahrer = fahrer;
        this.bezahlung = bezahlung;
        this.customers = customers;
        this.employees = employees;
    }
    public Trip_Collector() {
    }
}
