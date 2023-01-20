package com.example.onlineresumebuilder.payload.resquest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UpdateCvRequest {
    private Long cvId;
    private String information;
    private String summary;
    private String experiences;
    private String education;
    private String skills;
    @JsonIgnore
    private Long userId;
}
