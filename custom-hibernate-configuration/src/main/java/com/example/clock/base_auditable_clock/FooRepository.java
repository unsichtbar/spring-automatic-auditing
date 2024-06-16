package com.example.clock.base_auditable_clock;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FooRepository extends JpaRepository<Foo, Integer> {
    
}
