package com.pqh.basic.wechat.aspect;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author lufy
 * @Description ...
 * @Date 19-8-20 下午4:26
 */
@Aspect
@Order(4)
@Component
@Slf4j
public class LogAspect {

    @Value("${logstrategy.request}")
    private boolean needRequestLog;

    @Value("${spring.application.name}")
    private String application;

    @Pointcut("execution(public * com.metro.nccc..*.controller..*(..))")
    public void declearJoinPointExpression() {
    }

    @Around(value = "declearJoinPointExpression()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        String args = JSON.toJSONString(joinPoint.getArgs());
        try {
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            Object result = joinPoint.proceed();
            stopWatch.stop();
            if (needRequestLog) {
                log.info(buildLog(args, result));
            }
            return result;
        } catch (Throwable throwable) {
            log.error(buildLog(args, throwable.getMessage()));
            throw throwable;
        }

    }

    private String buildLog(String params, Object response) {
        try {
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            HttpServletRequest httpServletRequest = ((ServletRequestAttributes) requestAttributes).getRequest();
            RestLog restLog = new RestLog();
            restLog.setApllication(application);
            restLog.setApllication(httpServletRequest.getHeader("application"));
            restLog.setRequestBody(params);
            restLog.setChain(httpServletRequest.getHeader("chain"));
            restLog.setMethod(httpServletRequest.getMethod());
            restLog.setUrl(httpServletRequest.getServletPath());
            restLog.setResponse(response);
            restLog.setRemoteIp(httpServletRequest.getRemoteAddr());
            return JSON.toJSONString(restLog);
        } catch (Exception e) {
            log.error(e.getMessage());
            return String.format(" %s 日志统一记录出现错误： %s", application, e.getMessage());
        }

    }
}
