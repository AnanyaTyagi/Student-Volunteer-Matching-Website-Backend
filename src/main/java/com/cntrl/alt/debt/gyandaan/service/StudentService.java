package com.cntrl.alt.debt.gyandaan.service;

import com.cntrl.alt.debt.gyandaan.dto.LoginResponse;
import com.cntrl.alt.debt.gyandaan.entity.Student;
import org.springframework.http.ResponseEntity;

public interface StudentService {

    Student registerStudent(Student student);

    Student getStudentUsingUsername(String username);

    boolean updateStudentRecord(String username, Student student);

    ResponseEntity<LoginResponse> studentLogin(String email, String password);
}
