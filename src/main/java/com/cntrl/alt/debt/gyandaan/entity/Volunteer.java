package com.cntrl.alt.debt.gyandaan.entity;

import com.cntrl.alt.debt.gyandaan.dto.StudentPreferences;
import com.cntrl.alt.debt.gyandaan.dto.VolunteerSpecialisation;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="volunteer")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Volunteer {

    @Id
    private String emailId;

    @Column(name="firstname")
    private String firstName;

    @Column(name="lastname")
    private String lastName;

    @Column(name="phone_number")
    private String phoneNumber;

    private LocalDate DOB;

    private String password;

    @Type(type = "jsonb")
    private VolunteerSpecialisation[] specialisations;

    @Column(columnDefinition = "boolean default false")
    private boolean verified;
}
