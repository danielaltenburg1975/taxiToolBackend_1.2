package com.example.taxiToolBackend.dataRequest;

import lombok.Data;

@Data
public class Customer_LogRequest {
    private String username;
    private String password;
    private String roles;
    private String fahrgast;



    public Customer_LogRequest(String fahrgast, String username, String password, String roles) {
        this.fahrgast = fahrgast;
        this.username = username;
        this.password = password;
        this.roles = roles
        ;

    }
}
