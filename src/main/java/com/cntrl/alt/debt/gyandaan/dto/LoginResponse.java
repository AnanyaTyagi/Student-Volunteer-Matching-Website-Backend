package com.cntrl.alt.debt.gyandaan.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginResponse {

    String firstName;
    String lastName;
    String response;
    boolean loggedIn;
}
