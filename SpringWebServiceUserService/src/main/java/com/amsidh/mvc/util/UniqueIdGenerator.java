package com.amsidh.mvc.util;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UniqueIdGenerator {

    public String getUniqueId() {
        return UUID.randomUUID().toString();
    }
}
