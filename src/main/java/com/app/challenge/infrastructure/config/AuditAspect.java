package com.app.challenge.infrastructure.config;

import com.app.challenge.infrastructure.adapter.persistence.entity.BaseModel;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Slf4j
@Component
public class AuditAspect {


    @Pointcut("execution(* com.app.challenge.infrastructure.adapter.persistence.repository..*.save(..))")
    public void saveMethodPointcut() {
        log.debug("Entrando al método saveMethodPointcut()");
    }

    @Before("saveMethodPointcut() && args(entity)")
    public void beforeSave(Object entity) {
        log.debug("Antes de guardar la entidad: {}", entity.getClass().getSimpleName());
        if (entity instanceof BaseModel baseModel) {
            log.debug("Aplicando auditoría a: {}", baseModel.getClass().getSimpleName());

            LocalDateTime now = LocalDateTime.now();
            baseModel.setCreated(now);
            baseModel.setModified(now);
            baseModel.setLastLogin(now);

        } else {
            log.warn("La entidad no es una instancia de BaseModel: {}", entity.getClass().getSimpleName());
        }
    }
}
