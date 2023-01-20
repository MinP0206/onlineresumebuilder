package com.example.onlineresumebuilder.repository;


import com.example.onlineresumebuilder.model.Role;
import com.example.onlineresumebuilder.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}
