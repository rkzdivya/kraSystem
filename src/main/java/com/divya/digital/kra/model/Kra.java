package com.divya.digital.kra.model;
import java.io.Serializable;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "kra")
public class Kra implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "kra_seq", allocationSize = 1, initialValue = 1, sequenceName = "kra_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "kra_seq")
    @Column(updatable = false, nullable = false)
    private Long id;
    
    private String email;

    private String kraType;

    private Integer overallWeightage;
    
    private String status;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private String measureOfSuccess;

    @Column(updatable = false)
	@CreationTimestamp
	private LocalDateTime createdAt;
	
	@UpdateTimestamp
	private LocalDateTime updatedAt;

}
