package com.reply.airbnbdemo.aop;

import com.reply.airbnbdemo.Accesslog;
import com.reply.airbnbdemo.repository.AccessLogRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@Aspect
@RequiredArgsConstructor
public class AccessLogAspect {


    private final AccessLogRepository accessLogRepository;

    @Before("execution(* com.reply.airbnbdemo.controller..*..*(..))")
    public void intercept(JoinPoint joinPoint)  {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Accesslog accesslog = Accesslog
                .builder()
                .username(auth.getName())
                .methodName(joinPoint.getSignature().getName())
                .build();
        accessLogRepository.save(accesslog);
    }
}
