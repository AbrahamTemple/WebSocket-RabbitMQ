package com.cloud.olifebase.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @version 6.1.8
 * @author: Abraham Vong
 * @date: 2021.5.15
 * @GitHub https://github.com/AbrahamTemple/
 * @description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MsgDto implements Serializable {

    private List<String> persons; // 用户组

    private String msg; // 消息

    private Boolean ism; // 是否为重要消息发送者
}
