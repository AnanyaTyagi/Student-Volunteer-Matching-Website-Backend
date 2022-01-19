package com.cntrl.alt.debt.gyandaan.service.impl;

import com.cntrl.alt.debt.gyandaan.entity.Student;
import com.cntrl.alt.debt.gyandaan.entity.Volunteer;
import com.cntrl.alt.debt.gyandaan.repository.StudentRepository;
import com.cntrl.alt.debt.gyandaan.repository.VolunteerRepository;
import org.apache.tomcat.jni.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class UserExistenceCheckImpl implements UserExistenceCheck {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    VolunteerRepository volunteerRepository;

    @Override
    public boolean checkStudent(String email) {

        Optional<Student> student=studentRepository.findById(email);
        if(student.isEmpty())
           return false;
        else
            return true;
    }

    @Override
    public boolean checkVolunteer(String email) {

        Optional<Volunteer> volunteer=volunteerRepository.findById(email);
        if(volunteer.isEmpty())
            return false;
        else
            return true;
    }
}
