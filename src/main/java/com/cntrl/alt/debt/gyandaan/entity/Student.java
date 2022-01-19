package com.cntrl.alt.debt.gyandaan.entity;

import com.cntrl.alt.debt.gyandaan.dto.Slots;
import com.cntrl.alt.debt.gyandaan.dto.StudentPreferences;
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
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="student")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Student {

    @Id
    @Email
    private String emailId;

    @Column(name="firstname", nullable = false)
    private String firstName;

    @Column(name="lastname", nullable = false)
    private String lastName;

    @Column(name="phone_number",length = 10,nullable = false)
    private String phoneNumber;

   @Column(name="dob", nullable = false)
    private LocalDate DOB;

   @NotEmpty
    private String password;

    @Type(type = "jsonb")
    private List<StudentPreferences> preferences;

    @Type(type = "jsonb")
    private Slots slots;

    @Column(columnDefinition = "boolean default false")
    private boolean verified;
}
