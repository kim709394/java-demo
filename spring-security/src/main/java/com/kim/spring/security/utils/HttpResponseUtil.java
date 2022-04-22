package com.kim.spring.security.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * http 结果返回工具类
 *
 * @author kim
 */
public class HttpResponseUtil {

    /**
     * 使用 httpServletResponse 返回 json 数据
     *
     * @param response
     * @param result
     * @param objectMapper
     * @throws RuntimeException
     */
    public static void responseJson(HttpServletResponse response, ObjectMapper objectMapper, Object result) throws RuntimeException {
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(HttpStatus.OK.value());
        try {
            PrintWriter writer = response.getWriter();
            writer.write(objectMapper.writeValueAsString(result));
            writer.flush();
            writer.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
