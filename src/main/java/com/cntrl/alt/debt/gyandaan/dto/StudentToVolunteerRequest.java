package com.cntrl.alt.debt.gyandaan.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class StudentToVolunteerRequest {

    private String username;

    private Slots slots;

    private String requestId;

    private String standard;

    private String exam;

    private String subject;

    private String firstname;

    private String lastname;

    private String phoneNumber;
}
