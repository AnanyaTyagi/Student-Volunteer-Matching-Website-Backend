package com.cntrl.alt.debt.gyandaan.repository;

import com.cntrl.alt.debt.gyandaan.entity.Volunteer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface VolunteerRepository extends CrudRepository<Volunteer, String> {

    @Query(value = "SELECT * FROM volunteer WHERE specialisations @> CAST(:searchTerm as jsonb) AND available_hours>=1", nativeQuery = true)
    Set<Volunteer> findVolunteersHavingSpecialisations(@Param("searchTerm") String searchTerm);
}
