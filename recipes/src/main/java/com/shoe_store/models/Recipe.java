package com.shoe_store.models;

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

    private Long section_id;

    private String name;

    private Integer calories_on_hund_g;

    private Duration time_to_cook;

    private Integer dose_num;

    private String short_description;

    private LocalDateTime created_at;

}
