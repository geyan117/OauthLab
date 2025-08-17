package com.zero.sso.code.client.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author geyan
 * @date 2025/8/16
 */
@Data
public class CommonResult<T> implements Serializable {

    /**
     * 错误码
     */
    private Integer code;
    /**
     * 返回数据
     */
    private T data;
    /**
     * 错误提示，用户可阅读
     */
    private String msg;

}
