package com.cloud.olifebase.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author Abraham
 * @since 2021-05-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class OAdvisory implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @JsonProperty(value = "uId")
    private Long uId;

    @JsonProperty(value = "eId")
    private Long eId;

    private String message;

    public OAdvisory(Long uId, Long eId, String message) {
        this.uId = uId;
        this.eId = eId;
        this.message = message;
    }
}
