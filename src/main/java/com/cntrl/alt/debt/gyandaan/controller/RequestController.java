package com.cntrl.alt.debt.gyandaan.controller;

import com.cntrl.alt.debt.gyandaan.dto.GetRequestStatusResponse;
import com.cntrl.alt.debt.gyandaan.dto.StudentPreferences;
import com.cntrl.alt.debt.gyandaan.dto.SubmitRequestResponse;
import com.cntrl.alt.debt.gyandaan.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.UUID;

import static com.cntrl.alt.debt.gyandaan.utils.APIConstants.REQUEST_RESOURCE;
import static com.cntrl.alt.debt.gyandaan.utils.APIConstants.VERSION_1;

@RestController
@RequestMapping(VERSION_1 + REQUEST_RESOURCE)
public class RequestController {

    @Autowired
    private RequestService requestService;

    @PostMapping
    public ResponseEntity<SubmitRequestResponse> submitRequest(@RequestParam String student, @RequestBody StudentPreferences preference) throws IOException {

        String requestId = requestService.submitRequest(student, preference);
        return new ResponseEntity(new SubmitRequestResponse(requestId), HttpStatus.ACCEPTED);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<GetRequestStatusResponse> getRequestStatus(@PathVariable String requestId) {
        String status = requestService.getRequestStatus(UUID.fromString(requestId));
        return new ResponseEntity<>(new GetRequestStatusResponse(status), HttpStatus.OK);
    }

    @GetMapping("/{requestId}/volunteer")
    public ResponseEntity<String> getConfirmedVolunteer(@PathVariable String requestId) {
        String volunteer = requestService.getConfirmedVolunteer(requestId);
        return new ResponseEntity<>(volunteer, HttpStatus.OK);
    }
}
