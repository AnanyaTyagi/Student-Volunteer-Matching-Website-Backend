package com.cntrl.alt.debt.gyandaan.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentPreferences {

    private String standard;

    private String area;

    private String exam;

    private Slots slots;

    private int required_hours;
}
