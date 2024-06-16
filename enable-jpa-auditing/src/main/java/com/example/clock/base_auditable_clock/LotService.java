package com.example.clock.base_auditable_clock;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;

@Component
@Transactional
public class LotService {

    @Autowired LotRepository lotRepository;

    public LotEntity saveOne() {

        var lot = new LotEntity();
        lot = lotRepository.save(lot);

        return lot;
    }
    
}
