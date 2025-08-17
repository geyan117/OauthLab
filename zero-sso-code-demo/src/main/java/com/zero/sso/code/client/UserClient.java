package com.zero.sso.code.client;

import com.zero.sso.code.client.dto.CommonResult;
import com.zero.sso.code.client.dto.user.UserInfoRespDTO;
import com.zero.sso.code.client.dto.user.UserUpdateReqDTO;
import com.zero.sso.code.infra.core.LoginUser;
import com.zero.sso.code.infra.core.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * 用户 User 信息的客户端
 *
 * @author geyan
 * @date 2025/8/17
 */
@Component
@RequiredArgsConstructor
public class UserClient {

    private static final String BASE_URL = "http://127.0.0.1:48080/admin-api//system/oauth2/user";

    private final RestTemplate restTemplate;

    public CommonResult<UserInfoRespDTO> getUser() {
        // 1.1 构建请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("tenant-id", OAuth2Client.TENANT_ID.toString());
        addTokenHeader(headers);
        // 1.2 构建请求参数
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

        // 2. 执行请求
        ResponseEntity<CommonResult<UserInfoRespDTO>> exchange = restTemplate.exchange(
                BASE_URL + "/get",
                HttpMethod.GET,
                new HttpEntity<>(body, headers),
                new ParameterizedTypeReference<CommonResult<UserInfoRespDTO>>() {}); // 解决 CommonResult 的泛型丢失
        Assert.isTrue(exchange.getStatusCode().is2xxSuccessful(), "响应必须是 200 成功");
        return exchange.getBody();
    }

    public CommonResult<Boolean> updateUser(UserUpdateReqDTO updateReqDTO) {
        // 1.1 构建请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("tenant-id", OAuth2Client.TENANT_ID.toString());
        addTokenHeader(headers);
        // 1.2 构建请求参数
        // 使用 updateReqDTO 即可

        // 2. 执行请求
        ResponseEntity<CommonResult<Boolean>> exchange = restTemplate.exchange(
                BASE_URL + "/update",
                HttpMethod.PUT,
                new HttpEntity<>(updateReqDTO, headers),
                new ParameterizedTypeReference<CommonResult<Boolean>>() {}); // 解决 CommonResult 的泛型丢失
        Assert.isTrue(exchange.getStatusCode().is2xxSuccessful(), "响应必须是 200 成功");
        return exchange.getBody();
    }


    private static void addTokenHeader(HttpHeaders headers) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        Assert.notNull(loginUser, "登录用户不能为空");
        headers.add("Authorization", "Bearer " + loginUser.getAccessToken());
    }
}
