package com.cntrl.alt.debt.gyandaan.controller;

import com.cntrl.alt.debt.gyandaan.dto.*;
import com.cntrl.alt.debt.gyandaan.entity.Student;
import com.cntrl.alt.debt.gyandaan.entity.Volunteer;
import com.cntrl.alt.debt.gyandaan.repository.StudentRepository;
import com.cntrl.alt.debt.gyandaan.repository.VolunteerRepository;
import com.cntrl.alt.debt.gyandaan.service.StudentService;
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

import static com.cntrl.alt.debt.gyandaan.utils.APIConstants.STUDENT_RESOURCE;
import static com.cntrl.alt.debt.gyandaan.utils.APIConstants.VERSION_1;

@RestController
@RequestMapping(VERSION_1 + STUDENT_RESOURCE)
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private VolunteerRepository volunteerRepository;

    @Autowired
    private ModelMapper modelMapper;


    HttpHeaders responseHeaders=new HttpHeaders();


    @PostMapping(value ="/register", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RegisterResponse> register(@RequestBody StudentCreationRequest studentCreationRequest ) {

        responseHeaders.set("MyResponseHeader", "MyValue");
        RegisterResponse registerResponse=new RegisterResponse();
        Optional<Student> studentCheck = studentRepository.findById(studentCreationRequest.getEmailId());
        Optional<Volunteer> volunteerCheck= volunteerRepository.findById(studentCreationRequest.getEmailId());

        //checking if student already exists
        if(!studentCheck.isEmpty()) {
            registerResponse.setResponse("User already exists");
            return new ResponseEntity<>(registerResponse, responseHeaders, HttpStatus.BAD_REQUEST);
        }
        if(!volunteerCheck.isEmpty()) {
            registerResponse.setResponse("User already exists with another role");
            return new ResponseEntity<>(registerResponse, responseHeaders, HttpStatus.BAD_REQUEST);
        }
        Student student = modelMapper.map(studentCreationRequest, Student.class);
        student = studentService.registerStudent(student);

        /*URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{username}")
                .buildAndExpand(student.getEmailId())
                .toUri();*/
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
    public ResponseEntity<LoginResponse> studentVolunteer(@RequestBody LoginRequest loginRequest) {

      String email=loginRequest.getEmail();
      String password=loginRequest.getPassword();
      return studentService.studentLogin(email, password);
    }
}
