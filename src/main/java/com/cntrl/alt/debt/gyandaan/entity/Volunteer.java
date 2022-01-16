package com.cntrl.alt.debt.gyandaan.entity;


import com.cntrl.alt.debt.gyandaan.dto.Slots;
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
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="volunteer")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Volunteer {

    @Id
    private String emailId;

    private String password;

    @Column(name="firstname")
    private String firstName;

    @Column(name="lastname")
    private String lastName;

    private String phoneNumber;

    private LocalDate DOB;

    @Type(type = "jsonb")
    private List<VolunteerSpecialisation> specialisations;

    @Type(type = "jsonb")
    private Slots slots;

    private int maxHours;

    private int availableHours;

    @Column(columnDefinition = "boolean default false")
    private boolean verified;
}
