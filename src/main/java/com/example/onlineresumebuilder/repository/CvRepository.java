package com.example.onlineresumebuilder.repository;

import com.example.onlineresumebuilder.model.Cv;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CvRepository extends JpaRepository<Cv, Long> , JpaSpecificationExecutor<Cv> {
}
