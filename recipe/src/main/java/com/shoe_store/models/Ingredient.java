package com.shoe_store.models;

import com.shoe_store.models.enums.Unit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Ingredient {

    private Long id;

    private Long recipeId;

    private String name;

    private Integer amount;

    private Unit unit;

}
