package com.cntrl.alt.debt.gyandaan.service;

import com.cntrl.alt.debt.gyandaan.dto.LoginResponse;
import com.cntrl.alt.debt.gyandaan.entity.Volunteer;
import org.springframework.http.ResponseEntity;

public interface VolunteerService {

    Volunteer registerVolunteer(Volunteer volunteer);

    Volunteer getVolunteerByUsername(String username);

    boolean updateVolunteer(String username, Volunteer volunteer);

    ResponseEntity<LoginResponse> volunteerLogin(String email, String password);
}
