package com.cntrl.alt.debt.gyandaan.service;

import com.cntrl.alt.debt.gyandaan.entity.Student;

public interface StudentService {

    Student registerStudent(Student student);

    Student getStudentUsingUsername(String username);

    boolean updateStudentRecord(String username, Student student);
}
