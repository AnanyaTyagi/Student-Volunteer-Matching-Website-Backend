package com.cntrl.alt.debt.gyandaan.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentResponse {

    private String firstName;

    private String lastName;

    private Date DOB;

    private String phoneNumber;

    private Map<String, Object> preferences;
}
