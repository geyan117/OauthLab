package com.zero.sso.code.client.dto.oauth2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 访问令牌 Response DTO
 *
 * @author geyan
 * @date 2025/8/16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OAuth2AccessTokenRespDTO {

    /**
     * 访问令牌
     */
    @JsonProperty("access_token")
    private String accessToken;

    /**
     * 刷新令牌
     */
    @JsonProperty("refresh_token")
    private String refreshToken;

    /**
     * 令牌类型
     */
    @JsonProperty("token_type")
    private String tokenType;

    /**
     * 过期时间；单位：秒
     */
    @JsonProperty("expires_in")
    private Long expiresIn;

    /**
     * 授权范围；如果多个授权范围，使用空格分隔
     */
    private String scope;
}
