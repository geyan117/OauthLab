package com.zero.sso.code.infra.core.handle;

import com.zero.sso.code.client.dto.CommonResult;
import com.zero.sso.code.infra.core.util.ServletUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author geyan
 * @date 2025/8/16
 */
@Component
@Slf4j
@SuppressWarnings("JavadocReference") // 忽略文档引用报错
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    /**
     * 访问一个需要认证的 URL 资源，但是此时自己尚未认证（登录）的情况下，
     * 返回 {@link GlobalErrorCodeConstants#UNAUTHORIZED} 错误码，从而使前端重定向到登录页
     *
     * 补充：Spring Security 通过 {@link ExceptionTranslationFilter#sendStartAuthentication(HttpServletRequest, HttpServletResponse, FilterChain, AuthenticationException)} 方法，调用当前类
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        log.info("[commence][访问 URL({}) 时，没有登录]", request.getRequestURI(), e);

        // 返回 401
        CommonResult<Object> result = new CommonResult<>();
        result.setCode(HttpStatus.UNAUTHORIZED.value());
        result.setMsg("账号未登录");
        ServletUtils.writeJson(response, result);
    }
}
