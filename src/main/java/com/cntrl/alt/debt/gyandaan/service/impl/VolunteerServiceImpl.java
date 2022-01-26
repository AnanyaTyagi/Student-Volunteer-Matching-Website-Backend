package com.cntrl.alt.debt.gyandaan.service.impl;

import com.cntrl.alt.debt.gyandaan.dto.LoginRequest;
import com.cntrl.alt.debt.gyandaan.dto.LoginResponse;
import com.cntrl.alt.debt.gyandaan.entity.Student;
import com.cntrl.alt.debt.gyandaan.entity.Volunteer;
import com.cntrl.alt.debt.gyandaan.repository.VolunteerRepository;
import com.cntrl.alt.debt.gyandaan.service.VolunteerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class VolunteerServiceImpl implements VolunteerService {

    @Autowired
    private VolunteerRepository volunteerRepository;

    @Override
    public Volunteer registerVolunteer(Volunteer volunteer) {
        return volunteerRepository.save(volunteer);
    }

    @Override
    public Volunteer getVolunteerByUsername(String username) {
        Optional<Volunteer> optional = volunteerRepository.findById(username);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            return null;
        }
    }

    @Override
    public boolean updateVolunteer(String username, Volunteer volunteer) {
        boolean created = true;
        if (volunteerRepository.existsById(username)) {
            created = false;
        }
        volunteer.setEmailId(username);
        volunteerRepository.save(volunteer);
        return created;
    }

    @Override
    public boolean volunteerLogin(LoginRequest loginRequest) {
        Volunteer volunteer = getVolunteerIfExists(loginRequest.getEmail());

        if(volunteer == null || !volunteer.getPassword().equals(loginRequest.getPassword())) {
            return false;
        }
        return true;
    }

    public Volunteer getVolunteerIfExists(String email) {
        Optional<Volunteer> optional = volunteerRepository.findById(email);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }
}
