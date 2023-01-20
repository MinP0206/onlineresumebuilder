package com.example.onlineresumebuilder.model;

import com.example.onlineresumebuilder.model.audit.UserDateAudit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Getter
@Setter
@FieldNameConstants
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cv extends UserDateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String information;

    private String summary;

    private String experiences;

    private String education;

    private String skills;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(nullable = false,name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;
}
