package com.counter.redis_view_counter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
public class RedisViewCounterApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedisViewCounterApplication.class, args);
	}

}
