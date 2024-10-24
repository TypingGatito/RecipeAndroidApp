package com.recepe.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Recipe {

    private Long id;

    private Long sectionId;

    private Long userId;

    private String name;

    private Integer caloriesOnHundG;

    private Duration timeToCook;

    private Integer doseNum;

    private String shortDescription;

    private LocalDateTime createdAt;

}
