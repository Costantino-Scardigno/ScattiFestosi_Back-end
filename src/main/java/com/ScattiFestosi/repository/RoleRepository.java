package com.ScattiFestosi.repository;


import com.ScattiFestosi.enums.RoleEnum;
import com.ScattiFestosi.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleEnum name);
}
