package com.cntrl.alt.debt.gyandaan.service.impl;

import com.cntrl.alt.debt.gyandaan.dto.LoginRequest;
import com.cntrl.alt.debt.gyandaan.entity.Student;
import com.cntrl.alt.debt.gyandaan.repository.StudentRepository;
import com.cntrl.alt.debt.gyandaan.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

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
            created = false;
        }
        student.setEmailId(username);
        studentRepository.save(student);
        return created;
    }

    @Override
    public boolean studentLogin(LoginRequest loginRequest) {
        Student student = getStudentIfExists(loginRequest.getEmail());

        if (student == null || !student.getPassword().equals(loginRequest.getPassword())) {
            return false;
        }
        return true;
    }

    public Student getStudentIfExists(String email) {
        Optional<Student> optional = studentRepository.findById(email);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }
}
