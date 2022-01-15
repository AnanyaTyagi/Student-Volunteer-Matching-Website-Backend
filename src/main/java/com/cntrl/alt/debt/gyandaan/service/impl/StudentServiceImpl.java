package com.cntrl.alt.debt.gyandaan.service.impl;

import com.cntrl.alt.debt.gyandaan.entity.Student;
import com.cntrl.alt.debt.gyandaan.service.StudentService;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {


    @Override
    public Student registerStudent(Student student) {
        return new Student();
    }

    @Override
    public Student getStudentUsingUsername(String username) {
        return new Student();
    }

    @Override
    public Student updateStudentRecord(String username, Student student) {
        return new Student();
    }
}
