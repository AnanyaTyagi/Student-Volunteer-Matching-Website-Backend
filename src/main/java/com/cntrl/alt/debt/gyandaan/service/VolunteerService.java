package com.cntrl.alt.debt.gyandaan.service;

import com.cntrl.alt.debt.gyandaan.dto.LoginRequest;
import com.cntrl.alt.debt.gyandaan.dto.LoginResponse;
import com.cntrl.alt.debt.gyandaan.entity.Volunteer;
import org.springframework.http.ResponseEntity;

public interface VolunteerService {

    Volunteer registerVolunteer(Volunteer volunteer);

    Volunteer getVolunteerByUsername(String username);

    boolean updateVolunteer(String username, Volunteer volunteer);

    boolean volunteerLogin(LoginRequest loginRequest);

    Volunteer getVolunteerIfExists(String email);
}
