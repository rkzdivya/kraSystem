package com.divya.digital.kra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.divya.digital.kra.model.KraField;

@Repository
public interface KraFieldRepository extends JpaRepository<KraField, Long> {
}
