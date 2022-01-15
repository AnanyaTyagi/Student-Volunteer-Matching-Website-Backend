package com.cntrl.alt.debt.gyandaan.service;

import com.cntrl.alt.debt.gyandaan.entity.Volunteer;

public interface VolunteerService {

    Volunteer registerVolunteer(Volunteer volunteer);

    Volunteer getVolunteerByUsername(String username);

    boolean updateVolunteer(String username, Volunteer volunteer);
}
