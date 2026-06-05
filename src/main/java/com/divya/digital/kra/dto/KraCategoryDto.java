package com.divya.digital.kra.dto;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.divya.digital.kra.model.KraCategory;

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
public class KraCategoryDto implements Serializable {

    private static final long serialVersionUID = -5816178943201155899L;
    private String kraName;
    private Integer overallWeightage;
    private String detailedKra;
    private List<KraFieldDto> fields;
    
    public boolean validate() {
        int totalFieldWeightage = fields.stream()
                                        .mapToInt(KraFieldDto::getWeightage)
                                        .sum();
        return Objects.equals(overallWeightage, totalFieldWeightage);
    }

//    public static KraCategoryDto from(KraCategory kraCategory) {
//        return KraCategoryDto.builder()
//                .id(kraCategory.getId())
//                .kraName(kraCategory.getKraName())
//                .overallWeightage(kraCategory.getOverallWeightage())
//                .detailedKra(kraCategory.getDetailedKra())
//                .fields(kraCategory.getFields().stream()
//                        .map(KraFieldDto::from)
//                        .toList())
//                .build();
//    }
//
//    public KraCategory toEntity() {
//        return KraCategory.builder()
//                .id(this.id)
//                .kraName(this.kraName)
//                .overallWeightage(this.overallWeightage)
//                .detailedKra(this.detailedKra)
//                .fields(this.fields.stream()
//                        .map(KraFieldDto::toEntity)
//                        .toList())
//                .build();
//    }
}
