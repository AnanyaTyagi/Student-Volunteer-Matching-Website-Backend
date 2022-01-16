package com.cntrl.alt.debt.gyandaan.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="subject")
public class Subject {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    private String exam;

    private String standard;

    private String[] volunteers;
}
