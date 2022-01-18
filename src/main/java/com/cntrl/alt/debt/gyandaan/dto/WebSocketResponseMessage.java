package com.cntrl.alt.debt.gyandaan.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WebSocketResponseMessage {

    String status;

    String requestId;

    String volunteerId;

    public WebSocketResponseMessage(String sending_message_to_user) {
    }
}
