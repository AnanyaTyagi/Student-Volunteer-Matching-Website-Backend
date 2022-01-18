package com.cntrl.alt.debt.gyandaan.service.impl;

import com.cntrl.alt.debt.gyandaan.dto.WebSocketMessage;
import com.cntrl.alt.debt.gyandaan.dto.WebSocketResponseMessage;
import com.cntrl.alt.debt.gyandaan.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;


public class WebSocketServiceImpl implements WebSocketService {

    @Autowired
    private  final SimpMessagingTemplate messagingTemplate;

    public WebSocketServiceImpl(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void notifyUser(final String id,WebSocketMessage message) {
        WebSocketResponseMessage responseMessage= new WebSocketResponseMessage();
        messagingTemplate.convertAndSendToUser(id,"/topic/messages", responseMessage);
    }
}
