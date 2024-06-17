package com.example.clock.base_auditable_clock;

import java.util.Random;

import org.hibernate.FlushMode;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.event.spi.PostInsertEvent;
import org.hibernate.event.spi.PostInsertEventListener;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.FlushModeType;
import jakarta.persistence.Table;

@Configuration
public class AuditTablesConfiguration {

    @Bean
    public PostInsertEventListener auditCreatePostInsertEventListener(ObjectMapper om) {
        return new PostInsertEventListener() {

            @Override
            public boolean requiresPostCommitHandling(EntityPersister persister) {
                return false;
            }

            @Override
            public void onPostInsert(PostInsertEvent event) {

                var entity = event.getEntity();
                String json = "";
                try {
                    json = om.writeValueAsString(entity);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
                AuditCreate auditCreate = new AuditCreate();
                auditCreate.setAuditValue(json);
                auditCreate.setTable(getTableValue(entity.getClass()));
                // auditCreateRepository.save(auditCreate);
                event.getSession().createNativeQuery("""
                        INSERT INTO AUD_CREA ( AUDIT_TABLE, AUDIT_VALUE) values( ?, ?)
                        """, AuditCreate.class)
                        // .setParameter(1, new Random().nextInt())
                        .setParameter(1, auditCreate.getTable())
                        .setParameter(2, auditCreate.getAuditValue())
                        .setFlushMode(FlushModeType.COMMIT)
                        .executeUpdate();

            }

            String getTableValue(Class<?> entityClass) {
                var table = entityClass.getAnnotation(Table.class).name();
                return table;
            }

        };
    }

    @Configuration
    public static class AuditListenerConfig {

        private final EntityManagerFactory entityManagerFactory;
        private final PostInsertEventListener postInsertEventListener;

        public AuditListenerConfig(EntityManagerFactory entityManagerFactory,
                PostInsertEventListener postInsertEventListener) {
            this.entityManagerFactory = entityManagerFactory;
            this.postInsertEventListener = postInsertEventListener;
        }

        @PostConstruct
        public void init() {
            SessionFactoryImpl sessionFactory = entityManagerFactory.unwrap(SessionFactoryImpl.class);
            EventListenerRegistry eventListenerRegistry = sessionFactory
                    .getServiceRegistry()
                    .getService(EventListenerRegistry.class);

            eventListenerRegistry.appendListeners(EventType.POST_INSERT, postInsertEventListener);
        }
    }
}
