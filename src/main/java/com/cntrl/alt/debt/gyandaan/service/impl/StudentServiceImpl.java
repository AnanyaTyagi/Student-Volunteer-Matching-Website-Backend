package com.cntrl.alt.debt.gyandaan.service.impl;

import com.cntrl.alt.debt.gyandaan.dto.LoginResponse;
import com.cntrl.alt.debt.gyandaan.entity.Student;
import com.cntrl.alt.debt.gyandaan.repository.StudentRepository;
import com.cntrl.alt.debt.gyandaan.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;


    HttpHeaders httpHeaders=new HttpHeaders();

    @Override
    public Student registerStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student getStudentUsingUsername(String username) {
        Optional<Student> student = studentRepository.findById(username);
        if (student.isPresent()) {
            return student.get();
        } else {
            return null;
        }
    }

    @Override
    public boolean updateStudentRecord(String username, Student student) {
        boolean created = true;
        if (studentRepository.existsById(username)) {
            studentRepository.deleteById(username);
            created = false;
        }
        student.setEmailId(username);
        studentRepository.save(student);
        return created;
    }

    @Override
    public ResponseEntity<LoginResponse> studentLogin(String email, String password) {

        LoginResponse loginResponse = new LoginResponse();
        httpHeaders.set("abc", "def");

            Optional<Student> student = studentRepository.findById(email);
            if (student.isPresent()) {
                if ((student.get().getPassword()).equals(password)) {
                    loginResponse.setFirstName(student.get().getFirstName());
                    loginResponse.setLastName(student.get().getLastName());
                    loginResponse.setResponse("login successful");
                    return new ResponseEntity<LoginResponse>(loginResponse, httpHeaders, HttpStatus.OK);
                } else {
                    loginResponse.setResponse("Invalid username or password");
                    return new ResponseEntity<LoginResponse>(loginResponse, httpHeaders, HttpStatus.BAD_REQUEST);
                }

                }else {
        loginResponse.setResponse("user doesn't exist");
        return new ResponseEntity<LoginResponse>(loginResponse, httpHeaders, HttpStatus.BAD_REQUEST);
                  }
        }

}
