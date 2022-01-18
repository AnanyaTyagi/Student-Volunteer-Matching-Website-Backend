package com.cntrl.alt.debt.gyandaan.service;

import com.cntrl.alt.debt.gyandaan.dto.WebSocketMessage;

public interface WebSocketService {
    public void notifyUser(final String id, final WebSocketMessage message);
}
