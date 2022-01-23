package com.cntrl.alt.debt.gyandaan.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MatchedRequest {

    @JsonProperty("volunteer_username")
    private String volunteerUsername;

    @JsonProperty("matching_slots")
    private Slots matchingSlots;
}
