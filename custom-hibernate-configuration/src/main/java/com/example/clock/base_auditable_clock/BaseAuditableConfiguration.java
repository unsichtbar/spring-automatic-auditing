package com.example.clock.base_auditable_clock;

import java.time.Clock;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.CallbackException;
import org.hibernate.Interceptor;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

@Configuration
public class BaseAuditableConfiguration {

    @Autowired
    Clock clock;

    @Bean("entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder factory, DataSource dataSource,
            JpaProperties properties) {
        Map<String, Object> jpaProperties = new HashMap<String, Object>();
        jpaProperties.putAll(properties.getProperties());
        jpaProperties.put("hibernate.session_factory.interceptor", hibernateInterceptor());
        return factory.dataSource(dataSource).packages("com.example.clock")
                .properties((Map) jpaProperties).build();
    }

    @Bean
    public Interceptor hibernateInterceptor() {
        return new Interceptor() {
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
            // public boolean onFlushDirty(Object entity, Object id, Object[] currentState, Object[] previousState,
            //         String[] propertyNames, Type[] types) throws CallbackException {
            //     if (entity instanceof BaseAuditable auditable) {
            //         Instant now = Instant.now(clock);
            //         auditable.setLastUpdatedOn(now);
            //         auditable.setLastUpdatedBy("ALEX");
            //         if (auditable.getCreatedOn() == null) {
            //             auditable.setCreatedOn(now);
            //         }
            //         if (auditable.getCreatedBy() == null) {
            //             auditable.setCreatedBy("ALEX");
            //         }
            //     }
            //     return Interceptor.super.onFlushDirty(entity, id, currentState, previousState, propertyNames, types);
            // }
        };
    }
}
