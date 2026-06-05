package com.divya.digital.kra.dto;

import java.io.Serializable;

import com.divya.digital.kra.model.KraField;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KraFieldDto implements Serializable {

    private static final long serialVersionUID = 8513396856158997215L;

        private Integer weightage;
        private String measureOfSuccess;
        private String individualProgressTrackingIndicators;

//        public static KraFieldDto from(KraField kraField) {
//            return KraFieldDto.builder()
//                    .id(kraField.getId())
//                    .weightage(kraField.getWeightage())
//                    .measureOfSuccess(kraField.getMeasureOfSuccess())
//                    .individualProgressTrackingIndicators(kraField.getIndividualProgressTrackingIndicators())
//                    .build();
//        }
//
//        public KraField toEntity() {
//            return KraField.builder()
//                    .id(this.id)
//                    .weightage(this.weightage)
//                    .measureOfSuccess(this.measureOfSuccess)
//                    .individualProgressTrackingIndicators(this.individualProgressTrackingIndicators)
//                    .build();
//        }
}
