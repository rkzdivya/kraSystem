package com.divya.digital.kra.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.divya.digital.kra.model.Role;
import com.divya.digital.kra.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByEmail(String email);

	long countUsersByRole(Role role);
}
