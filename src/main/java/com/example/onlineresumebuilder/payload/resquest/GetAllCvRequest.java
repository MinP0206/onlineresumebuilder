package com.example.onlineresumebuilder.payload.resquest;

import com.example.onlineresumebuilder.model.Cv;
import com.example.onlineresumebuilder.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class GetAllCvRequest {
    @JsonIgnore
    private Pageable pageable;

    private String information;

    private String skills;


    private String education;

    private Long id;
    @JsonIgnore
    private Long userId;

    public Specification<Cv> getSpecification() {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();


            if (information != null) {
                predicates.add(cb.like(cb.lower(root.get(Cv.Fields.information)), "%" + information.toLowerCase() + "%"));
            }
            if (skills != null) {
                predicates.add(cb.like(cb.lower(root.get(Cv.Fields.skills)), "%" + skills.toLowerCase() + "%"));
            }
            if (education != null) {
                predicates.add(cb.like(cb.lower(root.get(Cv.Fields.education)), "%" + education.toLowerCase() + "%"));
            }
            if (userId != null) {
                predicates.add(cb.equal(root.get(Cv.Fields.user).get(User.Fields.id),userId));
            }
            if (id != null) {
                predicates.add(cb.equal(root.get(Cv.Fields.id),id));
            }
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
