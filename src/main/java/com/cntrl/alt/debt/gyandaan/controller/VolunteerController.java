package com.cntrl.alt.debt.gyandaan.controller;

import com.cntrl.alt.debt.gyandaan.dto.*;
import com.cntrl.alt.debt.gyandaan.entity.Volunteer;
import com.cntrl.alt.debt.gyandaan.service.StudentService;
import com.cntrl.alt.debt.gyandaan.service.VolunteerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;

import static com.cntrl.alt.debt.gyandaan.utils.APIConstants.VERSION_1;
import static com.cntrl.alt.debt.gyandaan.utils.APIConstants.VOLUNTEER_RESOURCE;

@RestController
@RequestMapping(VERSION_1 + VOLUNTEER_RESOURCE)
@Validated
public class VolunteerController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private VolunteerService volunteerService;

    @Autowired
    private StudentService studentService;


    @PostMapping
    public ResponseEntity<RegisterResponse> registerVolunteer(@RequestBody CreateVolunteerRequest createVolunteerRequest) {

        RegisterResponse registerResponse = new RegisterResponse();

        if (studentService.getStudentIfExists(createVolunteerRequest.getEmailId()) != null) {
            registerResponse.setResponse("User already exists with another role");
            return new ResponseEntity<>(registerResponse, HttpStatus.BAD_REQUEST);
        }
        if (volunteerService.getVolunteerIfExists(createVolunteerRequest.getEmailId()) != null) {
            registerResponse.setResponse("User already exists with another role");
            return new ResponseEntity<>(registerResponse, HttpStatus.BAD_REQUEST);
        }

        Volunteer volunteer = modelMapper.map(createVolunteerRequest, Volunteer.class);
        volunteer = volunteerService.registerVolunteer(volunteer);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{username}")
                .buildAndExpand(volunteer.getEmailId())
                .toUri();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Location", location.toString());
        registerResponse.setResponse("User Registered Successfully");
        return new ResponseEntity<>(registerResponse, responseHeaders, HttpStatus.CREATED);
    }

    @GetMapping("/{username}")
    public ResponseEntity<VolunteerResponse> retrieveVolunteer(@PathVariable String username) {
        Volunteer volunteer = volunteerService.getVolunteerByUsername(username);
        System.out.println(volunteer.getDOB());
        VolunteerResponse volunteerResponse = modelMapper.map(volunteer, VolunteerResponse.class);
        return new ResponseEntity<>(volunteerResponse, HttpStatus.OK);
    }

    @PutMapping("/{username}")
    public ResponseEntity updateVolunteerDetails(@PathVariable String username,
                                                 @RequestBody UpdateVolunteerRequest updateVolunteerRequest) {
        Volunteer volunteer = modelMapper.map(updateVolunteerRequest, Volunteer.class);
        boolean created = volunteerService.updateVolunteer(username, volunteer);
        if (created) {
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{username}")
                    .buildAndExpand(volunteer.getEmailId())
                    .toUri();
            return ResponseEntity.created(location)
                    .build();
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(value = "/login", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<LoginResponse> loginVolunteer(@RequestBody @Valid LoginRequest loginRequest) {

        LoginResponse loginResponse = new LoginResponse();
        if (!volunteerService.volunteerLogin(loginRequest)) {
            loginResponse.setResponse("User not authorized");
            loginResponse.setLoggedIn(false);
            return new ResponseEntity<>(loginResponse, HttpStatus.UNAUTHORIZED);
        }
        loginResponse.setResponse("login successful");
        loginResponse.setLoggedIn(true);
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return "index";
    }
}
