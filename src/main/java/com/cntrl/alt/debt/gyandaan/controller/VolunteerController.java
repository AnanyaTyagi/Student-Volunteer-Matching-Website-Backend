package com.cntrl.alt.debt.gyandaan.controller;

import com.cntrl.alt.debt.gyandaan.dto.*;
import com.cntrl.alt.debt.gyandaan.entity.Student;
import com.cntrl.alt.debt.gyandaan.entity.Volunteer;
import com.cntrl.alt.debt.gyandaan.repository.StudentRepository;
import com.cntrl.alt.debt.gyandaan.repository.VolunteerRepository;
import com.cntrl.alt.debt.gyandaan.service.VolunteerService;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

import static com.cntrl.alt.debt.gyandaan.utils.APIConstants.VERSION_1;
import static com.cntrl.alt.debt.gyandaan.utils.APIConstants.VOLUNTEER_RESOURCE;

@RestController
@RequestMapping(VERSION_1 + VOLUNTEER_RESOURCE)
public class VolunteerController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private VolunteerService volunteerService;


    HttpHeaders responseHeaders=new HttpHeaders();


    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private VolunteerRepository volunteerRepository;


    @PostMapping(value = "/register", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RegisterResponse> registerVolunteer(@RequestBody CreateVolunteerRequest createVolunteerRequest) {

        responseHeaders.set("MyResponseHeader", "MyValue");
        RegisterResponse registerResponse=new RegisterResponse();
        Optional<Student> studentCheck = studentRepository.findById(createVolunteerRequest.getEmailId());
        Optional<Volunteer> volunteerCheck= volunteerRepository.findById(createVolunteerRequest.getEmailId());

        //checking if student already exists
        if(!volunteerCheck.isEmpty()) {
            registerResponse.setResponse("User already exists");
            return new ResponseEntity<>(registerResponse, responseHeaders, HttpStatus.BAD_REQUEST);
        }
        if(!studentCheck.isEmpty()) {
            registerResponse.setResponse("User already exists with another role");
            return new ResponseEntity<>(registerResponse, responseHeaders, HttpStatus.BAD_REQUEST);
        }

        Volunteer volunteer = modelMapper.map(createVolunteerRequest, Volunteer.class);
        volunteer = volunteerService.registerVolunteer(volunteer);

       /* URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{username}")
                .buildAndExpand(volunteer.getEmailId())
                .toUri();*/
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
        if(created) {
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

    @PostMapping(value="/login", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<LoginResponse> loginVolunteer(@RequestBody LoginRequest loginRequest) {

        String email=loginRequest.getEmail();
        String password=loginRequest.getPassword();
        return volunteerService.volunteerLogin(email, password);

    }
}
