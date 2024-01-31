package com.example.taxiToolBackend.data;


import jakarta.persistence.MappedSuperclass;
import lombok.Data;




@Data
@MappedSuperclass
public abstract class AbstractLogin extends AbstractEntity {
    private String username;
    private String password;
    private String roles;

}
