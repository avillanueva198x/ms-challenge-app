package com.app.challenge.infrastructure.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

@Aspect
@Component
@Slf4j
public class TimingAspect {

    private static final String LOG_MARKER = "[TIME_API_APP]";

    @Around("execution(* com.app.challenge.infrastructure.rest..*(..))")
    public Object logRequestTiming(ProceedingJoinPoint joinPoint) throws Throwable {
        final long startTime = System.nanoTime();

        try {
            return joinPoint.proceed();
        } finally {
            final long durationMs = (System.nanoTime() - startTime) / 1_000_000;

            final var methodSignature = joinPoint.getSignature();
            final var className = methodSignature.getDeclaringTypeName();
            final var methodName = methodSignature.getName();

            // Usamos Optional para evitar nulls explícitos
            Optional.ofNullable(RequestContextHolder.getRequestAttributes())
                .filter(ServletRequestAttributes.class::isInstance)
                .map(ServletRequestAttributes.class::cast)
                .map(ServletRequestAttributes::getRequest)
                .ifPresentOrElse(request -> log.info(
                    "{} : [{} {}] ===> {}.{} ejecutado en {} ms",
                    LOG_MARKER,
                    request.getMethod(),
                    request.getRequestURI(),
                    className,
                    methodName,
                    durationMs
                ), () -> log.info(
                    "{} : [{}] ===> {}.{} ejecutado en {} ms (sin HttpServletRequest)",
                    LOG_MARKER,
                    "NO_METHOD_OR_URI",
                    className,
                    methodName,
                    durationMs
                ));
        }
    }
}
