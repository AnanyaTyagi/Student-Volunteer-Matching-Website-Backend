package com.cntrl.alt.debt.gyandaan.repository;

import com.cntrl.alt.debt.gyandaan.entity.Student;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student, String> {

}
