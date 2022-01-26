package com.cntrl.alt.debt.gyandaan.service.impl;

import com.cntrl.alt.debt.gyandaan.dto.StudentToVolunteerRequest;
import com.cntrl.alt.debt.gyandaan.entity.Student;
import com.cntrl.alt.debt.gyandaan.entity.Volunteer;
import com.cntrl.alt.debt.gyandaan.listener.WebSocketSessionListener;
import com.cntrl.alt.debt.gyandaan.repository.StudentRepository;
import com.cntrl.alt.debt.gyandaan.repository.VolunteerRepository;
import com.cntrl.alt.debt.gyandaan.service.RequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RequestServiceImpl implements RequestService {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private VolunteerRepository volunteerRepository;

    @Autowired
    private WebSocketSessionListener webSocketSessionListener;

    @Override
    public String submitRequest(String studentUsername, String preferredArea) {
        String requestId = "mockedId";
        Student student = studentRepository.findById("ananya.tyagi@gmail.com").get();
        Volunteer volunteer = volunteerRepository.findById("sagarika.nangia@gmail.com").get();
        sendNotificationToVolunteer(requestId,student, volunteer);
        return requestId;
    }

    @Override
    public String getRequestStatus(String requestId) {
        return "mockedStatus";
    }

    @Override
    public String getConfirmedVolunteer(String requestId) {
        return "mockedVolunteer";
    }

    public void sendNotificationToVolunteer(String requestId, Student student, Volunteer volunteer) {
        StudentToVolunteerRequest studentToVolunteerRequest= StudentToVolunteerRequest.builder()
                .requestId(requestId)
                .firstname(student.getFirstName())
                .lastname(student.getLastName())
                .phoneNumber(student.getPhoneNumber())
                .build();
        if(webSocketSessionListener.getConnectedClientId().contains(volunteer.getEmailId())) {
            String address = "/queue/volunteer/" + volunteer.getEmailId()+"/notification";
            simpMessagingTemplate.convertAndSend(address, studentToVolunteerRequest);
        }
        else {
            log.info("user {} is offline, storing notifications in db", volunteer.getEmailId());
            //TODO: store notifications in db
        }
    }
}
