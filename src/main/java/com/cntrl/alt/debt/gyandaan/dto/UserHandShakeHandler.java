package com.cntrl.alt.debt.gyandaan.dto;

import com.sun.security.auth.UserPrincipal;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;
import java.util.UUID;


public class UserHandShakeHandler extends DefaultHandshakeHandler {
    private final Logger LOG= LoggerFactory.getLogger(UserHandShakeHandler.class);

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        String randomId= UUID.randomUUID().toString();
        LOG.info("User with id '{}' opened the page",randomId);
        //return super.determineUser(request, wsHandler, attributes);
        return new UserPrincipal(randomId);
    }
}