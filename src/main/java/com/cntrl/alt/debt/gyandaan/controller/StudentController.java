package com.cntrl.alt.debt.gyandaan.controller;

import com.cntrl.alt.debt.gyandaan.dto.StudentCreationRequest;
import com.cntrl.alt.debt.gyandaan.dto.StudentResponse;
import com.cntrl.alt.debt.gyandaan.dto.StudentUpdateRequest;
import com.cntrl.alt.debt.gyandaan.entity.Student;
import com.cntrl.alt.debt.gyandaan.service.StudentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

import static com.cntrl.alt.debt.gyandaan.utils.APIConstants.STUDENT_RESOURCE;
import static com.cntrl.alt.debt.gyandaan.utils.APIConstants.VERSION_1;

@RestController
@RequestMapping(VERSION_1 + STUDENT_RESOURCE)
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(value = "", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> register(@RequestBody StudentCreationRequest studentCreationRequest ) {
        Student student = modelMapper.map(studentCreationRequest, Student.class);

        student = studentService.registerStudent(student);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{username}")
                .buildAndExpand(student.getEmailId())
                .toUri();
        return ResponseEntity.created(location)
                .build();
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
}
