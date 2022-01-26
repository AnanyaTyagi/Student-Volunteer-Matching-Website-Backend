package com.cntrl.alt.debt.gyandaan.listener;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.messaging.support.NativeMessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
@Data
public class WebSocketSessionListener {

    private List<String> connectedClientId = new ArrayList<>();

    @EventListener
    public void connectionEstablished(SessionConnectedEvent sce) {
        MessageHeaderAccessor accessor = NativeMessageHeaderAccessor.getAccessor(sce.getMessage(), SimpMessageHeaderAccessor.class);
        GenericMessage<?> generic = (GenericMessage<?>) accessor.getHeader("simpConnectMessage");
        Map<String, List<String>> nativeHeaders = (Map<String, List<String>>) generic.getHeaders().get("nativeHeaders");

        MessageHeaders msgHeaders = sce.getMessage().getHeaders();
        Principal princ = (Principal) msgHeaders.get("simpUser");

        if (nativeHeaders != null) {
            String userId = nativeHeaders.get("userId").get(0);
            connectedClientId.add(userId);
            log.info("Added to connected users, :{} " + userId);

        } else {
            String userId = princ.getName();
            System.out.println("**userId"+ userId);
            connectedClientId.add(userId);
            log.info("Adding to connected users,:{} " + userId);
        }
    }

    @EventListener
    public void webSocketDisconnect(SessionDisconnectEvent sde) {
        MessageHeaders msgHeaders = sde.getMessage().getHeaders();
        Principal princ = (Principal) msgHeaders.get("simpUser");
        MessageHeaderAccessor accessor = NativeMessageHeaderAccessor.getAccessor(sde.getMessage(), SimpMessageHeaderAccessor.class);
        GenericMessage<?> generic = (GenericMessage<?>) accessor.getHeader("simpConnectMessage");
        Map<String, List<String>> nativeHeaders = (Map<String, List<String>>) generic.getHeaders().get("nativeHeaders");

        if (nativeHeaders != null) {
            String userId = nativeHeaders.get("userId").get(0);
            System.out.println("**userId"+ nativeHeaders.get(0));
            connectedClientId.remove(userId);
            log.info("Disconnected user {}" + userId);

        } else {
            String userId = princ.getName();
            connectedClientId.remove(userId);
            log.info("Disconnected user {} " + userId);
        }
    }
}
