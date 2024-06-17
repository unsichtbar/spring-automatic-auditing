package com.example.clock.base_auditable_clock;

import java.time.Clock;
import java.time.Instant;

import org.hibernate.CallbackException;
import org.hibernate.Interceptor;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class BaseAuditableConfiguration {

    @Autowired
    Clock clock;

    @Primary
    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer() {
        return (properties) -> {
            properties.put("hibernate.session_factory.interceptor", hibernateInterceptor());
        };
    }

    @Bean
    public Interceptor hibernateInterceptor() {
        return new Interceptor() {
            @Override
            public boolean onFlushDirty(Object entity, Object id, Object[] currentState, Object[] previousState,
                    String[] propertyNames, Type[] types) throws CallbackException {
                // TODO Auto-generated method stub
               
                if (entity instanceof BaseAuditable auditable) {
                    Instant now = Instant.now(clock);
                    auditable.setLastUpdatedOn(now);
                    auditable.setLastUpdatedBy("ALEX");
                    if (auditable.getCreatedOn() == null) {
                        auditable.setCreatedOn(now);
                    }
                    if (auditable.getCreatedBy() == null) {
                        auditable.setCreatedBy("ALEX");
                    }
                }
                return Interceptor.super.onFlushDirty(entity, id, currentState, previousState, propertyNames, types);
            
            }

            @Override
            public boolean onSave(Object entity, Object id, Object[] state, String[] propertyNames, Type[] types)
                    throws CallbackException {
                                 
                if (entity instanceof BaseAuditable auditable) {
                    Instant now = Instant.now(clock);
                    auditable.setLastUpdatedOn(now);
                    auditable.setLastUpdatedBy("ALEX");
                    if (auditable.getCreatedOn() == null) {
                        auditable.setCreatedOn(now);
                    }
                    if (auditable.getCreatedBy() == null) {
                        auditable.setCreatedBy("ALEX");
                    }
                }
                return Interceptor.super.onSave(entity, id, state, propertyNames, types);
            }

            // @Override
            // public boolean onFlushDirty(Object entity, Object id, Object[] currentState,
            // Object[] previousState,
            // String[] propertyNames, Type[] types) throws CallbackException {
            // if (entity instanceof BaseAuditable auditable) {
            // Instant now = Instant.now(clock);
            // auditable.setLastUpdatedOn(now);
            // auditable.setLastUpdatedBy("ALEX");
            // if (auditable.getCreatedOn() == null) {
            // auditable.setCreatedOn(now);
            // }
            // if (auditable.getCreatedBy() == null) {
            // auditable.setCreatedBy("ALEX");
            // }
            // }
            // return Interceptor.super.onFlushDirty(entity, id, currentState,
            // previousState, propertyNames, types);
            // }
        };
    }
}
