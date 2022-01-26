package com.cntrl.alt.debt.gyandaan.controller;

import com.cntrl.alt.debt.gyandaan.dto.*;
import com.cntrl.alt.debt.gyandaan.entity.Student;
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

import static com.cntrl.alt.debt.gyandaan.utils.APIConstants.STUDENT_RESOURCE;
import static com.cntrl.alt.debt.gyandaan.utils.APIConstants.VERSION_1;

@RestController
@Validated
@RequestMapping(VERSION_1 + STUDENT_RESOURCE)
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private VolunteerService volunteerService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody StudentCreationRequest studentCreationRequest ) {
        RegisterResponse registerResponse=new RegisterResponse();

        //checking if student already exists
        if(studentService.getStudentIfExists(studentCreationRequest.getEmailId()) != null) {
            registerResponse.setResponse("User already exists");
            return new ResponseEntity<>(registerResponse, HttpStatus.BAD_REQUEST);
        }
        if(volunteerService.getVolunteerIfExists(studentCreationRequest.getEmailId()) != null) {
            registerResponse.setResponse("User already exists with another role");
            return new ResponseEntity<>(registerResponse, HttpStatus.BAD_REQUEST);
        }

        Student student = modelMapper.map(studentCreationRequest, Student.class);
        student = studentService.registerStudent(student);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{username}")
                .buildAndExpand(student.getEmailId())
                .toUri();
        HttpHeaders responseHeaders=new HttpHeaders();
        responseHeaders.set("Location", location.toString());
        registerResponse.setResponse("User Registered Successfully");
        return new ResponseEntity<>(registerResponse, responseHeaders, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{username}")
    public ResponseEntity<StudentResponse> retrieveStudentByUsername(@PathVariable String username) {
        Student student = studentService.getStudentUsingUsername(username);
        StudentResponse studentResponse = modelMapper.map(student, StudentResponse.class);
        return new ResponseEntity<>(studentResponse, HttpStatus.OK);
    }

    @PutMapping(value = "/{username}")
    public ResponseEntity updateStudentDetailsByUsername(@PathVariable String username, @RequestBody StudentUpdateRequest studentUpdateRequest) {
        Student student = modelMapper.map(studentUpdateRequest, Student.class);
        boolean created = studentService.updateStudentRecord(username, student);
        if(created) {
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{username}")
                    .buildAndExpand(student.getEmailId())
                    .toUri();
            return ResponseEntity.created(location)
                    .build();
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(value="/login", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<LoginResponse> loginVolunteer(@RequestBody @Valid LoginRequest loginRequest) {
        LoginResponse loginResponse = new LoginResponse();
        if (!studentService.studentLogin(loginRequest)) {
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
