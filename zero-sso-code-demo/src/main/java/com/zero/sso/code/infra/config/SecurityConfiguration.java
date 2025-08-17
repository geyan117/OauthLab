package com.zero.sso.code.infra.config;

import com.zero.sso.code.infra.core.filter.TokenAuthenticationFilter;
import com.zero.sso.code.infra.core.handle.AccessDeniedHandlerImpl;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author geyan
 * @date 2025/8/16
 */
@Configuration(proxyBeanMethods = false)
@EnableWebSecurity
public class SecurityConfiguration {

    @Resource
    private AccessDeniedHandlerImpl accessDeniedHandler;

    @Resource
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Resource
    private TokenAuthenticationFilter tokenAuthenticationFilter;

    /**
     * 配置安全过滤链
     */
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                // 禁用 CSRF（如果是 API 服务通常可以禁用）
                .csrf(AbstractHttpConfigurer::disable)

                // 配置请求授权规则
                .authorizeHttpRequests(authz -> authz
                        // 1. 静态资源，允许匿名访问
                        .requestMatchers(HttpMethod.GET, "/*.html").permitAll()
                        // 2. 登录、刷新、登出接口，允许匿名访问
                        .requestMatchers("/auth/login-by-code",
                                "/auth/refresh-token",
                                "/auth/logout").permitAll()
                        // 兜底规则：其他所有请求都需要认证
                        .anyRequest().authenticated()
                )

                // 配置异常处理
                .exceptionHandling(ex -> ex
                        .accessDeniedHandler(accessDeniedHandler)
                        .authenticationEntryPoint(authenticationEntryPoint)
                )

                // 添加 token filter
                .addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }


}
