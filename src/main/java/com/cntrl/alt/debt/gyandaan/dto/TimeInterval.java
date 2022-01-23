package com.cntrl.alt.debt.gyandaan.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TimeInterval {

    private LocalTime startTime;

    private LocalTime endTime;

    public TimeInterval(String timeInterval) {
        String[] times = timeInterval.split("-");
        this.startTime = LocalTime.parse(times[0].trim());
        this.endTime = LocalTime.parse(times[1].trim());
    }
}
