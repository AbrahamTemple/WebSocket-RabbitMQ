package com.boot.olifewebsocket.dto;

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
    /**
     * 用户组
     */
    private List<String> persons;
    /**
     * 消息
     */
    private String msg;

    private Boolean ism; // 是否为重要消息发送者
}
