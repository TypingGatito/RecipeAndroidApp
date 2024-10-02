package com.shoe_store.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Commentary {

    private Long user_id;

    private Long step_id;

    private Integer order_num;

    private String text;

}
