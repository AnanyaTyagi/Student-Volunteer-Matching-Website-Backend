package com.cntrl.alt.debt.gyandaan.service.impl;

import com.cntrl.alt.debt.gyandaan.service.RequestService;
import org.springframework.stereotype.Service;

@Service
public class RequestServiceImpl implements RequestService {
    @Override
    public String submitRequest(String studentUsername, String preferredArea) {
        return "mockedId";
    }

    @Override
    public String getRequestStatus(String requestId) {
        return "mockedStatus";
    }

    @Override
    public String getConfirmedVolunteer(String requestId) {
        return "mockedVolunteer";
    }
}
