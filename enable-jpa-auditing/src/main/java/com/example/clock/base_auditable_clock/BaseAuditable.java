package com.example.clock.base_auditable_clock;

import java.time.Instant;
import java.util.Optional;

import org.springframework.data.domain.Auditable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseAuditable<ID> implements Auditable <String, ID, Instant> {


    @Column(name = "CREATED_BY")
    String createdBy;

    @Column(name = "CREATED_ON")
    Instant createdOn;

    @Column(name = "LAST_UPDATED_BY")
    String lastUpdatedBy;

    @Column(name = "LAST_UPDATED_ON")
    Instant lastUpdatedOn;

    @Override
    public Optional<String> getCreatedBy() {
        return Optional.of(createdBy);
    }

    @Override
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public Optional<Instant> getCreatedDate() {
        return Optional.of(this.createdOn);
    }

    @Override
    public void setCreatedDate(Instant creationDate) {
        this.createdOn = creationDate;
    }

    @Override
    public Optional<String> getLastModifiedBy() {
        return Optional.of(this.lastUpdatedBy);
    }

    @Override
    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastUpdatedBy = lastModifiedBy;
    }

    @Override
    public Optional<Instant> getLastModifiedDate() {
        return Optional.of(this.lastUpdatedOn);
    }

    @Override
    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastUpdatedOn = lastModifiedDate;
    }

    @Override
    public abstract ID getId();

    @Override
    public boolean isNew() {
        return this.getId() == null;
    }

  


}
