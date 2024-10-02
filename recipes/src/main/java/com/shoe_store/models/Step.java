package com.shoe_store.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Step {

    private Long id;

    private Long recipe_id;

    private Integer num;

    private String text;

    private Long imageId;

}
