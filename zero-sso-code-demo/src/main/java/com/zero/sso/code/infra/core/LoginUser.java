package com.zero.sso.code.infra.core;

import lombok.Builder;
import lombok.Data;
import org.springframework.context.annotation.Bean;

import java.util.List;

/**
 * 登录用户信息
 *
 * @author geyan
 * @date 2025/8/17
 */
@Data
@Builder
public class LoginUser {

    /**
     * 用户编号
     */
    private Long id;
    /**
     * 用户类型
     */
    private Integer userType;
    /**
     * 租户编号
     */
    private Long tenantId;
    /**
     * 授权范围
     */
    private List<String> scopes;

    /**
     * 访问令牌
     */
    private String accessToken;

}
