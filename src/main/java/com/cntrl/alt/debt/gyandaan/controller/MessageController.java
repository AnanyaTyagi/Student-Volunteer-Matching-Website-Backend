package com.cntrl.alt.debt.gyandaan.controller;

import com.cntrl.alt.debt.gyandaan.dto.WebSocketMessage;
import com.cntrl.alt.debt.gyandaan.dto.WebSocketResponseMessage;
import com.cntrl.alt.debt.gyandaan.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.HtmlUtils;

import java.security.Principal;

@Controller
public class MessageController {

    @Autowired
    WebSocketService webSocketService;

    @PostMapping(value="/send-request/{userName}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void sendMessage(@PathVariable final String id, @RequestBody WebSocketMessage requestMessage){

        webSocketService.notifyUser(id, requestMessage);

    }

    @MessageMapping("/message")
    @SendToUser("/topic/messages")
    public WebSocketResponseMessage getMessage(final WebSocketMessage message, final Principal principal) throws InterruptedException {
        Thread.sleep(1000);
         return new WebSocketResponseMessage(HtmlUtils.htmlEscape("Sending message to user "+ principal.getName() +":" +message));
    }

}
