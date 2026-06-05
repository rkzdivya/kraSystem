package com.divya.digital.kra.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "kra_fields")
public class KraField implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="kra_field_id", nullable = false,updatable = false)
    private Long id;

    private Integer weightage;

    @Column(length = 1024)
    private String measureOfSuccess;

    @Column(length = 1024)
    private String individualProgressTrackingIndicators;
}