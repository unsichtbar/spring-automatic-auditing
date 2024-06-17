package com.example.clock.base_auditable_clock;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "AUD_CREA")
public class AuditCreate {

    @Id
    @GeneratedValue(strategy  = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "AUDIT_TABLE")
    private String table;

    @Column(name = "AUDIT_VALUE", length = 4000)
    private String auditValue;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getAuditValue() {
        return auditValue;
    }

    public void setAuditValue(String auditValue) {
        this.auditValue = auditValue;
    }

    
    
}
