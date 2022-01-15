package com.cntrl.alt.debt.gyandaan.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentCreationRequest {

    @JsonProperty("username")
    private String emailId;

    @JsonProperty("firstname")
    private String firstName;

    @JsonProperty("lastname")
    private String lastname;

    private String password;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate DOB;

    @JsonProperty("phone_number")
    private String phoneNumber;

    private StudentPreferences[] preferences;
}
