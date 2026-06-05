package com.divya.digital.kra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.divya.digital.kra.model.KraForm;
import com.divya.digital.kra.request.saveKraRequest;

@Repository
public interface KraFormRepository extends JpaRepository<KraForm, Long> {

//	KraForm save(saveKraRequest kraForm);

//	KraForm findByUserId(String email);
}
