package com.cntrl.alt.debt.gyandaan.utils;

import com.cntrl.alt.debt.gyandaan.dto.StudentCreationRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class StudentUtility {

    private StudentUtility() {

    }

    public static StudentCreationRequest getStudent(String student) {
        StudentCreationRequest studentCreationRequest = new StudentCreationRequest();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            studentCreationRequest = objectMapper.readValue(student, StudentCreationRequest.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return studentCreationRequest;
    }
}
