package com.cntrl.alt.debt.gyandaan.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Slots {

    @JsonProperty("Mon")
    public ArrayList<String> mon;
    @JsonProperty("Tues")
    public ArrayList<String> tues;
    @JsonProperty("Wed")
    public ArrayList<String> wed;
    @JsonProperty("Thurs")
    public ArrayList<String> thurs;
    @JsonProperty("Fri")
    public ArrayList<String> fri;
    @JsonProperty("Sat")
    public ArrayList<String> sat;
    @JsonProperty("Sun")
    public ArrayList<String> sun;
}
