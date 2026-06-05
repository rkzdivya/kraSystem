package com.divya.digital.kra.model;


import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "kra_categories")
public class KraCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="kra_category_id", nullable = false,updatable = false)
    private Long id;

    private String kraName;

    private Integer overallWeightage;

    @Column(length = 1024)
    private String detailedKra;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "kra_category_id")
    private List<KraField> fields;
}
