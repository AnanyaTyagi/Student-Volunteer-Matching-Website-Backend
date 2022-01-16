package com.cntrl.alt.debt.gyandaan.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VolunteerResponse {


    @JsonProperty("firstname")
    private String firstName;

    @JsonProperty("lastname")
    private String lastName;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate DOB;

    @JsonProperty("max_hours")
    private int maxHours;

    @JsonProperty("available_hours")
    private int availableHours;

    @JsonProperty("phone_number")
    private String phoneNumber;

    private List<VolunteerSpecialisation> specialisations;

    private Slots slots;

    private boolean verified;
}
