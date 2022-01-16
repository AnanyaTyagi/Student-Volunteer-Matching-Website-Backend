package com.cntrl.alt.debt.gyandaan.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VolunteerSpecialisation {

    private String subject;

    private String standard;

    private String exam;

    private Slots slots;
}
