package com.dynamic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@MapperScan("com.dynamic.Dao")
public class StartupApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(StartupApplication.class, args);
	}

}
