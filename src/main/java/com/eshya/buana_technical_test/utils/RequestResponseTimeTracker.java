package com.eshya.buana_technical_test.utils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class RequestResponseTimeTracker {
    private static Logger logger = LoggerFactory.getLogger(RequestResponseTimeTracker.class);

    @Around("@annotation(com.eshya.buana_technical_test.utils.TimeTraker)")
    public Object measureTime(ProceedingJoinPoint point) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object object = point.proceed();
        stopWatch.stop();
        logger.info("Request URI: " + request.getRequestURI() + ", Execution Time taken by : " + point.getSignature().getName() + "() method is - "
                + stopWatch.getTotalTimeMillis() + " ms");
        return object;
    }
}
