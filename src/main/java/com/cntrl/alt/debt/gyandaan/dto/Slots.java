package com.cntrl.alt.debt.gyandaan.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Slots {

    @JsonProperty("Mon")
    public List<String> mon;
    @JsonProperty("Tues")
    public List<String> tues;
    @JsonProperty("Wed")
    public List<String> wed;
    @JsonProperty("Thurs")
    public List<String> thurs;
    @JsonProperty("Fri")
    public List<String> fri;
    @JsonProperty("Sat")
    public List<String> sat;
    @JsonProperty("Sun")
    public List<String> sun;
}
