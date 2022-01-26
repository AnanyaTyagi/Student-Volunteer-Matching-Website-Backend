package com.cntrl.alt.debt.gyandaan.service;

import com.cntrl.alt.debt.gyandaan.dto.LoginRequest;
import com.cntrl.alt.debt.gyandaan.dto.LoginResponse;
import com.cntrl.alt.debt.gyandaan.entity.Student;

public interface StudentService {

    Student registerStudent(Student student);

    Student getStudentUsingUsername(String username);

    boolean updateStudentRecord(String username, Student student);

    boolean studentLogin(LoginRequest loginRequest);

    Student getStudentIfExists(String email);
}
