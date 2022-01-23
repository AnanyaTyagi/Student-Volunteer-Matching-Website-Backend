package com.cntrl.alt.debt.gyandaan.entity;

import com.cntrl.alt.debt.gyandaan.dto.MatchedRequest;
import com.cntrl.alt.debt.gyandaan.dto.StudentPreferences;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name="request_matching")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Type(type = "jsonb")
    private StudentPreferences preferences;

    @Type(type = "jsonb")
    private List<MatchedRequest> matched;

    private String status;

    private String state;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(referencedColumnName="emailId")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName="emailId")
    private Volunteer assignedVolunteer;

    private LocalDateTime createdTimestamp;
}
