package com.zero.sso.code.infra.core.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;


/**
 * 客户端工具类
 * @author geyan
 * @date 2025/8/16
 */
public class ServletUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 返回 JSON 字符串
     *
     * @param response 响应
     * @param object 对象，会序列化成 JSON 字符串
     */
    public static void writeJson(HttpServletResponse response, Object object) {
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

        try (PrintWriter writer = response.getWriter()) {
            String json = objectMapper.writeValueAsString(object);
            writer.write(json);
            writer.flush();
        } catch (IOException e) {
            // 处理异常，例如记录日志
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try (PrintWriter writer = response.getWriter()) {
                writer.write("{\"error\": \"Internal Server Error\"}");
            } catch (IOException ex) {
                // 忽略内部异常
            }
        }
    }
}
