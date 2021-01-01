package com.amsidh.mvc.util;

import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class UniqueIdGenerator {

	public String getUniqueId() {
		return UUID.randomUUID().toString();
	}
}
