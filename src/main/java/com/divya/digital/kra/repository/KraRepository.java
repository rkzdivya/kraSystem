package com.divya.digital.kra.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.divya.digital.kra.model.Kra;

public interface KraRepository extends JpaRepository<Kra, Long> {

	Optional<Kra> findByEmail(String email);

}
