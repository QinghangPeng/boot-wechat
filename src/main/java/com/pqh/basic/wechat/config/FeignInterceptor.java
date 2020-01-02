package com.pqh.basic.wechat.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * @Author lufy  * @Description ...  * @Date 19-11-5 下午2:07
 */
@Component
public class FeignInterceptor implements RequestInterceptor {
    @Autowired
    HttpServletRequest request;
    @Value("${spring.application.name}")
    String application;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        String chain = StringUtils.isEmpty(request.getHeader("chain")) ? UUID.randomUUID().toString() : request.getHeader("chain");
        requestTemplate.header("chain", chain);
        requestTemplate.header("source", application);
    }
}