package com.example.onlineresumebuilder.dtos;

import com.example.onlineresumebuilder.model.Cv;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CvDto {
    private long id;

    private String information;

    private String summary;

    private String experiences;

    private String education;

    private String skills;

    public CvDto(Cv cv) {
        this.id = cv.getId();
        this.information = cv.getInformation();
        this.summary = cv.getSummary();
        this.experiences = cv.getExperiences();
        this.education = cv.getEducation();
        this.skills = cv.getSkills();
    }
}
