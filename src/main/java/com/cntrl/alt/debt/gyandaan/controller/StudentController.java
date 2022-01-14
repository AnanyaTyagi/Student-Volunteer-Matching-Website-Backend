package com.cntrl.alt.debt.gyandaan.controller;

import com.cntrl.alt.debt.gyandaan.dto.StudentCreationRequest;
import com.cntrl.alt.debt.gyandaan.dto.StudentResponse;
import com.cntrl.alt.debt.gyandaan.dto.StudentUpdateRequest;
import com.cntrl.alt.debt.gyandaan.entity.Student;
import com.cntrl.alt.debt.gyandaan.service.StudentService;
import com.cntrl.alt.debt.gyandaan.utils.StudentUtility;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static com.cntrl.alt.debt.gyandaan.utils.APIConstants.STUDENTS_RESOURCE;
import static com.cntrl.alt.debt.gyandaan.utils.APIConstants.VERSION_1;

@RestController
@RequestMapping(VERSION_1 + STUDENTS_RESOURCE)
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping(value = "", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Void> register(@RequestPart("student") String student,
                                         @RequestPart("adhaarCard") MultipartFile file) {
        StudentCreationRequest studentCreationRequest = StudentUtility.getStudent(student);
        //TODO: Conversion to entity from DTO
        Student s = new Student();
        s = studentService.registerStudent(s);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{username}")
                .buildAndExpand(s.getUsername())
                .toUri();
        return ResponseEntity.created(location)
                .build();
    }

    @GetMapping(value = "/{username}")
    public ResponseEntity<StudentResponse> retrieveStudentByUsername(@PathVariable String username) {
        Student s = studentService.getStudentUsingUsername(username);
        //TODO: Convert from entity to DTO
        StudentResponse studentResponse = new StudentResponse();
        return new ResponseEntity<>(studentResponse, HttpStatus.OK);
    }

    @PutMapping(value = "/{username}")
    public ResponseEntity updateStudentDetailsByUsername(@PathVariable String username, @RequestBody StudentUpdateRequest studentUpdateRequest) {
        Student student = new Student();
        studentService.updateStudentRecord(username, student);
        return new ResponseEntity(HttpStatus.OK);
    }

    public void findVolunteers() {

    }

    public void getRequestStatus() {

    }

}
