package com.cntrl.alt.debt.gyandaan.service.impl;

import com.cntrl.alt.debt.gyandaan.config.TaskExecutorMatchingVolunteers;
import com.cntrl.alt.debt.gyandaan.dto.MatchedRequest;
import com.cntrl.alt.debt.gyandaan.dto.SearchVolunteerUsingSubjectAndStandard;
import com.cntrl.alt.debt.gyandaan.dto.Slots;
import com.cntrl.alt.debt.gyandaan.dto.StudentPreferences;
import com.cntrl.alt.debt.gyandaan.dto.TimeInterval;
import com.cntrl.alt.debt.gyandaan.entity.Request;
import com.cntrl.alt.debt.gyandaan.entity.Student;
import com.cntrl.alt.debt.gyandaan.entity.Volunteer;
import com.cntrl.alt.debt.gyandaan.repository.RequestRepository;
import com.cntrl.alt.debt.gyandaan.repository.StudentRepository;
import com.cntrl.alt.debt.gyandaan.repository.VolunteerRepository;
import com.cntrl.alt.debt.gyandaan.service.Days;
import com.cntrl.alt.debt.gyandaan.service.RequestService;
import com.cntrl.alt.debt.gyandaan.service.RequestState;
import com.cntrl.alt.debt.gyandaan.service.RequestStatus;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
public class RequestServiceImpl implements RequestService {

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private VolunteerRepository volunteerRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TaskExecutorMatchingVolunteers taskExecutorMatchingVolunteers;

    private ExecutorService executor
            = Executors.newFixedThreadPool(10);

    @Override
    public String submitRequest(String studentUsername, StudentPreferences preferences) throws IOException {
        Request request = new Request();
        request.setCreatedTimestamp(LocalDateTime.now());
        request.setPreferences(preferences);
        Student s = studentRepository.getById(studentUsername);
        request.setStudent(s);
        request.setStatus(RequestStatus.STARTED.toString());
        request.setState(RequestState.ACTIVE.toString());
        final Request requestSaved = requestRepository.save(request);
        taskExecutorMatchingVolunteers.taskExecutor().execute(() -> {
            try {
                matchVolunteersAndSendNotification(requestSaved, preferences);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return requestSaved.getId().toString();
    }

    @Override
    public String getRequestStatus(UUID requestId) {
        return requestRepository.getStatusByRequestId(requestId);
    }

    //TODO: implement
    @Override
    public String getConfirmedVolunteer(String requestId) {
        return "mockedVolunteer";
    }

    public void matchVolunteersAndSendNotification(Request request, StudentPreferences preferences) throws IOException {
        String area = preferences.getArea();
        File file = ResourceUtils.getFile("classpath:subjects_specs.json");
        String subjectSpecs = new String(Files.readAllBytes(file.toPath()));
        Map<String, HashSet<String>> subjectsToTopicsMappings = objectMapper.readValue(subjectSpecs,
                new TypeReference<Map<String, HashSet<String>>>() {
                });
        String subject = null;
        if (subjectsToTopicsMappings.containsKey(area)) {
            subject = area;
        } else {
            for (Map.Entry<String, HashSet<String>> entry : subjectsToTopicsMappings.entrySet()) {
                HashSet<String> val = entry.getValue();
                if (val.contains(area)) {
                    subject = entry.getKey();
                    break;
                }
            }
            if (subject == null) {
                request.setStatus(RequestStatus.NOT_FOUND.toString());
                requestRepository.save(request);
                return;
            }
        }


        SearchVolunteerUsingSubjectAndStandard[] searchVolunteerUsingSubjectAndStandard =
                new SearchVolunteerUsingSubjectAndStandard[]{
                        new SearchVolunteerUsingSubjectAndStandard(subject, preferences.getStandard())
                };
        String searchTerm = objectMapper.writeValueAsString(searchVolunteerUsingSubjectAndStandard);
        Set<Volunteer> volunteers = volunteerRepository.findVolunteersHavingSpecialisations(searchTerm);
        if(volunteers.isEmpty()) {
            request.setStatus(RequestStatus.NOT_FOUND.toString());
            requestRepository.save(request);
        }
        final String subj = subject;
        for (String exam : preferences.getExam()) {
            volunteers.removeIf(
                    volunteer -> volunteer.getSpecialisations()
                            .stream()
                            .anyMatch(
                                    s -> ((subj).equals(s.getSubject()) && s.getExam() != null && !s.getExam().contains(exam))
                            )
            );
        }
        if(volunteers.isEmpty()) {
            request.setStatus(RequestStatus.NOT_FOUND.toString());
            requestRepository.save(request);
        }
        Student student = request.getStudent();
        Map<String, List<TimeInterval>> studentSlots = new HashMap<>();
        studentSlots.put(Days.MON.toString(), student.getSlots().getMon().stream()
                .map(s -> new TimeInterval(s)).collect(Collectors.toList()
                ));
        studentSlots.put(Days.TUES.toString(), student.getSlots().getTues().stream()
                .map(s -> new TimeInterval(s)).collect(Collectors.toList()
                ));
        studentSlots.put(Days.WED.toString(), student.getSlots().getWed().stream()
                .map(s -> new TimeInterval(s)).collect(Collectors.toList()
                ));
        studentSlots.put(Days.THURS.toString(), student.getSlots().getThurs().stream()
                .map(s -> new TimeInterval(s)).collect(Collectors.toList()
                ));
        studentSlots.put(Days.FRI.toString(), student.getSlots().getFri().stream()
                .map(s -> new TimeInterval(s)).collect(Collectors.toList()
                ));
        studentSlots.put(Days.SAT.toString(), student.getSlots().getSat().stream()
                .map(s -> new TimeInterval(s)).collect(Collectors.toList()
                ));
        studentSlots.put(Days.SUN.toString(), student.getSlots().getSun().stream()
                .map(s -> new TimeInterval(s)).collect(Collectors.toList()
                ));

        List<MatchedRequest> matchedRequests = new ArrayList<>();
        Map<String, List<TimeInterval>> intersectingSlots = new HashMap<>();
        for (Volunteer volunteer : volunteers) {
            System.out.println(volunteer.getEmailId());
            MatchedRequest matchedRequest = new MatchedRequest(volunteer.getEmailId(), new Slots());
            Slots slots = volunteer.getSlots();
            List<TimeInterval> monSlotsVolunteer = slots.getMon()
                    .stream()
                    .map(s -> new TimeInterval(s)).collect(Collectors.toList());
            List<TimeInterval> tuesSlotsVolunteer = slots.getTues()
                    .stream()
                    .map(s -> new TimeInterval(s)).collect(Collectors.toList());
            List<TimeInterval> wedSlotsVolunteer = slots.getWed()
                    .stream()
                    .map(s -> new TimeInterval(s)).collect(Collectors.toList());
            List<TimeInterval> thursSlotsVolunteer = slots.getThurs()
                    .stream()
                    .map(s -> new TimeInterval(s)).collect(Collectors.toList());
            List<TimeInterval> friSlotsVolunteer = slots.getFri()
                    .stream()
                    .map(s -> new TimeInterval(s)).collect(Collectors.toList());
            List<TimeInterval> satSlotsVolunteer = slots.getSat()
                    .stream()
                    .map(s -> new TimeInterval(s)).collect(Collectors.toList());
            List<TimeInterval> sunSlotsVolunteer = slots.getSun()
                    .stream()
                    .map(s -> new TimeInterval(s)).collect(Collectors.toList());
            matchedRequest
                    .getMatchingSlots()
                    .setMon(addIntersectingSlotsForGivenDay(
                            Days.MON.toString(),
                            monSlotsVolunteer,
                            studentSlots));
            matchedRequest
                    .getMatchingSlots()
                    .setTues(addIntersectingSlotsForGivenDay(
                            Days.TUES.toString(),
                            tuesSlotsVolunteer,
                            studentSlots));
            matchedRequest
                    .getMatchingSlots()
                    .setWed(addIntersectingSlotsForGivenDay(
                            Days.WED.toString(),
                            wedSlotsVolunteer,
                            studentSlots));
            matchedRequest
                    .getMatchingSlots()
                    .setThurs(addIntersectingSlotsForGivenDay(
                            Days.THURS.toString(),
                            thursSlotsVolunteer,
                            studentSlots));
            matchedRequest
                    .getMatchingSlots()
                    .setFri(addIntersectingSlotsForGivenDay(
                            Days.FRI.toString(),
                            friSlotsVolunteer,
                            studentSlots));
            matchedRequest
                    .getMatchingSlots()
                    .setSat(addIntersectingSlotsForGivenDay(
                            Days.SAT.toString(),
                            satSlotsVolunteer,
                            studentSlots));
            matchedRequest
                    .getMatchingSlots()
                    .setSun(addIntersectingSlotsForGivenDay(
                            Days.SUN.toString(),
                            sunSlotsVolunteer,
                            studentSlots));
            //TODO: add method to send notification to client concurrently
            matchedRequests.add(matchedRequest);
        }
        if( matchedRequests != null) {
            request.setMatched(matchedRequests);
            request.setStatus(RequestStatus.SUBMITTED.toString());
        }
        else{
            request.setStatus(RequestStatus.NOT_FOUND.toString());
        }
        requestRepository.save(request);
    }

    private List<String> addIntersectingSlotsForGivenDay(String day, List<TimeInterval> slotsVolunteer,
                                                         Map<String, List<TimeInterval>> studentSlotsMap) {
        List<TimeInterval> slotsStudent = studentSlotsMap.get(day);
        if (slotsStudent == null) {
            return null;
        }
        List<String> intersectingIntervals = new ArrayList<>();
        int s = 0;
        int v = 0;
        while (s < slotsStudent.size() && v < slotsVolunteer.size()) {
            TimeInterval vol = slotsVolunteer.get(v);
            TimeInterval stud = slotsStudent.get(s);
            if (vol.getStartTime().compareTo(stud.getEndTime()) > 0) {
                s++;
            } else if (stud.getStartTime().compareTo(vol.getEndTime()) > 0) {
                v++;
            } else {
                LocalTime start = stud.getStartTime().compareTo(vol.getStartTime()) >= 0 ? stud.getStartTime() : vol.getStartTime();
                LocalTime end = stud.getEndTime().compareTo(vol.getEndTime()) <= 0 ? stud.getEndTime() : vol.getEndTime();
                String interval = String.format("%s-%s", start.format(DateTimeFormatter.ofPattern("HH:mm")),
                        end.format(DateTimeFormatter.ofPattern("HH:mm")));

                intersectingIntervals.add(interval);
                s++;
                v++;
            }
        }
        return intersectingIntervals;
    }
}
