package com.example.clock.base_auditable_clock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;

@Component
@Transactional
public class FooService {

    @Autowired
    private FooRepository fooRepository;


    public Foo saveOne() {
        var foo = new Foo();
        foo = fooRepository.save(foo);
        return foo;

    }
    
}
