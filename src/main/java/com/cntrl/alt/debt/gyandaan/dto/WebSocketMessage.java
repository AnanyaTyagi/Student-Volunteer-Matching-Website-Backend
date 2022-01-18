package com.cntrl.alt.debt.gyandaan.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WebSocketMessage {

    String requestId;

    String studentName;

    String subject;

    String timeSlot;

    String classStudent;

    String exam;
}
