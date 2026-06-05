package com.divya.digital.kra.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import com.divya.digital.kra.model.KraForm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KraFormDto implements Serializable {

    private static final long serialVersionUID = -4454249048839462782L;

    private Long userId; 
    private String kraType;
    private Integer overallWeightage;
    private String status;
    private List<KraCategoryDto> categories;

    
    public boolean validate() {
        int totalCategoryWeightage = categories.stream()
                                               .mapToInt(KraCategoryDto::getOverallWeightage)
                                               .sum();

        if (!Objects.equals(overallWeightage, totalCategoryWeightage)) {
            return false;
        }

        for (KraCategoryDto category : categories) {
            if (!category.validate()) {
                return false;
            }
        }

        return overallWeightage == 100;
    }


}
