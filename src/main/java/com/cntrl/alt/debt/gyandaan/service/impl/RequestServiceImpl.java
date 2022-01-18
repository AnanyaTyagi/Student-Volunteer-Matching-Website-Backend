package com.cntrl.alt.debt.gyandaan.service.impl;

import com.cntrl.alt.debt.gyandaan.dto.StudentToVolunteerRequest;
import com.cntrl.alt.debt.gyandaan.entity.Student;
import com.cntrl.alt.debt.gyandaan.entity.Volunteer;
import com.cntrl.alt.debt.gyandaan.repository.StudentRepository;
import com.cntrl.alt.debt.gyandaan.repository.VolunteerRepository;
import com.cntrl.alt.debt.gyandaan.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class RequestServiceImpl implements RequestService {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private VolunteerRepository volunteerRepository;

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

    //TODO: Implement matching algorithm
//    public List<String> matchVolunteers(String studentUsername, String area) throws IOException {
//        String subjectSpecs = IOUtils.resourceToString("subjects_specs.json", StandardCharsets.UTF_8);
//        ObjectMapper objectMapper = new ObjectMapper();
//        Map<String, HashSet<String>> subjectsToTopicsMappings = objectMapper.readValue(subjectSpecs,
//                new TypeReference<Map<String, HashSet<String>>>() {
//                });
//        String subject;
//        if (subjectsToTopicsMappings.containsKey(area)) {
//            subject = area;
//        } else {
//            for (Map.Entry<String, HashSet<String>> entry : subjectsToTopicsMappings.entrySet()) {
//                HashSet<String> val = entry.getValue();
//                if(val.contains(area)) {
//                    subject = area;
//                    break;
//                }
//            }
//            return null;
//        }
//        String standard = "X"; //GET from student preferences
//        String examType = "CBSE"; //GET from student preferences
//        //query from subject table all volunteers having the subject, examType and standard
//        Set<String> volunteers = new HashSet<>();
//        int requiredHours = 3; //GET from student preferences
//        // for each volunteer in the set, query volunteer with the same username and available_hours>=required-hours and return entire volunteer record
//
//        //for each volunteer in the set, do time slot matchings
//      return null;
//    }

    public void sendNotificationToVolunteer(String requestId, Student student, Volunteer volunteer) {
        StudentToVolunteerRequest studentToVolunteerRequest = new StudentToVolunteerRequest();
        StudentToVolunteerRequest.builder()
                .requestId(requestId)
                .firstname(student.getFirstName())
                .lastname(student.getLastName())
                .phoneNumber(student.getPhoneNumber());

        String address = "/queue/volunteer/" + volunteer.getEmailId()+"/notification";
        simpMessagingTemplate.convertAndSend(address, studentToVolunteerRequest);
    }
}
