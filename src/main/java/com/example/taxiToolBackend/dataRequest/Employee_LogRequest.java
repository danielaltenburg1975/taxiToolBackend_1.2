package com.example.taxiToolBackend.dataRequest;

import lombok.Data;

@Data
public class Employee_LogRequest {
    private String username;
    private String password;
    private String roles;
    private String personalnummer;



    public Employee_LogRequest(String personalnummer, String username, String password, String roles) {
        this.personalnummer = personalnummer;
        this.username = username;
        this.password = password;
        this.roles = roles
        ;

    }
}
