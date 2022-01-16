package com.cntrl.alt.debt.gyandaan.service.impl;

import com.cntrl.alt.debt.gyandaan.entity.Student;
import com.cntrl.alt.debt.gyandaan.entity.Volunteer;
import com.cntrl.alt.debt.gyandaan.repository.StudentRepository;
import com.cntrl.alt.debt.gyandaan.repository.VolunteerRepository;
import com.cntrl.alt.debt.gyandaan.service.LoginSignupProfileDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginSignupProfileDetailsServiceImpl implements LoginSignupProfileDetailsService {


    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private VolunteerRepository volunteerRepository;

    @Override
   public String getUsernameLogin(String email,String password, String type){

        String firstName, lastname ;

        if(type.equals("student")) {
            Optional<Student> student = studentRepository.findById(email);
            if (student.isPresent()){
                if((student.get().getPassword()).equals(password)){
                    firstName = student.get().getFirstName();
                    lastname = student.get().getLastName();
                    return (new StringBuilder(firstName).append(" ").append(new StringBuilder(lastname))).toString();
                }else
                    return "Invalid username or password";

            } else {
              return "user doesn't exist";
                }
        }else {
                Optional<Volunteer> volunteer = volunteerRepository.findById(email);

                if (volunteer.isPresent()) {
                    if((volunteer.get().getPassword()).equals(password)){
                        firstName = volunteer.get().getFirstName();
                        lastname = volunteer.get().getLastName();
                        return (new StringBuilder(firstName).append(" ").append(new StringBuilder(lastname))).toString();
                    }else
                        return "invalid username or password";
                }else
                    return "user doesn't exist";
            }

    }
}


