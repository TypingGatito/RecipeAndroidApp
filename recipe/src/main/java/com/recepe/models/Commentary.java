package com.recepe.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Commentary {

    private Long userId;

    private Long stepId;

    private Integer orderNum;

    private String text;

}
