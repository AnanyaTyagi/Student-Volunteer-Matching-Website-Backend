package com.cntrl.alt.debt.gyandaan.controller;

import com.cntrl.alt.debt.gyandaan.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.cntrl.alt.debt.gyandaan.utils.APIConstants.REQUEST_RESOURCE;
import static com.cntrl.alt.debt.gyandaan.utils.APIConstants.VERSION_1;

@RestController
@RequestMapping(VERSION_1 + REQUEST_RESOURCE)
public class RequestController {

    @Autowired
    private RequestService requestService;

    @PostMapping
    public ResponseEntity<String> submitRequest(@RequestParam String student,
                                                                     @RequestParam("preferenceArea") String area) {

        String requestId = requestService.submitRequest(student, area);
        return new ResponseEntity(requestId, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<String> getRequestStatus(@PathVariable String requestId) {
        String status = requestService.getRequestStatus(requestId);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @GetMapping("/{requestId}/volunteer")
    public ResponseEntity<String> getConfirmedVolunteer(@PathVariable String requestId) {
        String volunteer = requestService.getConfirmedVolunteer(requestId);
        return new ResponseEntity<>(volunteer, HttpStatus.OK);
    }
}
