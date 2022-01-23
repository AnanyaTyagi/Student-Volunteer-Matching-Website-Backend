package com.cntrl.alt.debt.gyandaan.service;

import com.cntrl.alt.debt.gyandaan.dto.StudentPreferences;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface RequestService {

    String submitRequest(String studentUsername, StudentPreferences preference) throws IOException;

    String getRequestStatus(UUID requestId);

    String getConfirmedVolunteer(String requestId);
}
