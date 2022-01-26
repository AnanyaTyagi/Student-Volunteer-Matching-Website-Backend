package com.cntrl.alt.debt.gyandaan.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VolunteerSpecialisation {

    private String subject;

    private String standard;

    private List<String> exam;
}
