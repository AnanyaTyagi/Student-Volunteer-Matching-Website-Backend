package com.cntrl.alt.debt.gyandaan.controller;

import com.cntrl.alt.debt.gyandaan.dto.CreateVolunteerRequest;
import com.cntrl.alt.debt.gyandaan.dto.UpdateVolunteerRequest;
import com.cntrl.alt.debt.gyandaan.dto.VolunteerResponse;
import com.cntrl.alt.debt.gyandaan.entity.Volunteer;
import com.cntrl.alt.debt.gyandaan.service.VolunteerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static com.cntrl.alt.debt.gyandaan.utils.APIConstants.VERSION_1;
import static com.cntrl.alt.debt.gyandaan.utils.APIConstants.VOLUNTEER_RESOURCE;

@RestController
@RequestMapping(VERSION_1 + VOLUNTEER_RESOURCE)
public class VolunteerController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private VolunteerService volunteerService;

    @PostMapping
    public ResponseEntity registerVolunteer(@RequestBody CreateVolunteerRequest createVolunteerRequest) {
        Volunteer volunteer = modelMapper.map(createVolunteerRequest, Volunteer.class);
        volunteer = volunteerService.registerVolunteer(volunteer);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{username}")
                .buildAndExpand(volunteer.getEmailId())
                .toUri();
        return ResponseEntity.created(location)
                .build();
    }

    @GetMapping("/{username}")
    public ResponseEntity<VolunteerResponse> retrieveVolunteer(@PathVariable String username) {
        Volunteer volunteer = volunteerService.getVolunteerByUsername(username);
        System.out.println(volunteer.getDOB());
        VolunteerResponse volunteerResponse = modelMapper.map(volunteer, VolunteerResponse.class);
        return new ResponseEntity<>(volunteerResponse, HttpStatus.OK);
    }

    @PutMapping("/{username}")
    public ResponseEntity updateVolunteerDetails(@PathVariable String username,
                                       @RequestBody UpdateVolunteerRequest updateVolunteerRequest) {
        Volunteer volunteer = modelMapper.map(updateVolunteerRequest, Volunteer.class);
        boolean created = volunteerService.updateVolunteer(username, volunteer);
        if(created) {
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{username}")
                    .buildAndExpand(volunteer.getEmailId())
                    .toUri();
            return ResponseEntity.created(location)
                    .build();
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}
