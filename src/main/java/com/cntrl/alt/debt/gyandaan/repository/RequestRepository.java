package com.cntrl.alt.debt.gyandaan.repository;

import com.cntrl.alt.debt.gyandaan.entity.Request;
import com.cntrl.alt.debt.gyandaan.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface RequestRepository extends JpaRepository<Request, UUID> {

    @Query(value = "SELECT status FROM request_matching WHERE id=:id", nativeQuery = true)
    String getStatusByRequestId(@Param("id") UUID id);

}
