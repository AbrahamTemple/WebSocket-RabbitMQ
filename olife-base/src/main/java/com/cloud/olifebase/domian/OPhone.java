package com.cloud.olifebase.domian;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @version 6.1.8
 * @author: Abraham Vong
 * @date: 2021.5.25
 * @GitHub https://github.com/AbrahamTemple/
 * @description:
 */
@Data
@AllArgsConstructor
public class OPhone implements Serializable {
    private String number;
    private Integer weight; //空闲权重
}
