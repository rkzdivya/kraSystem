package com.divya.digital.kra.request.auth;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String name;
    private String lastname;
    private String email;
    private String password;
    private Integer role;
    private String phonenumber;
    private boolean isActive;
}
