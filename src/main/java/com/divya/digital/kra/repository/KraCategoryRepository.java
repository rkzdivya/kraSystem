package com.divya.digital.kra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.divya.digital.kra.model.KraCategory;

@Repository
public interface KraCategoryRepository extends JpaRepository<KraCategory, Long> {
}
