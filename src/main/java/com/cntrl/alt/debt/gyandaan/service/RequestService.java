package com.cntrl.alt.debt.gyandaan.service;

public interface RequestService {

    String submitRequest(String studentUsername, String preferredArea);

    String getRequestStatus(String requestId);

    String getConfirmedVolunteer(String requestId);
}
