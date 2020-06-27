package com.arukione.curriculum_design;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.arukione.curriculum_design.mapper")
public class CurriculumDesignApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurriculumDesignApplication.class, args);
	}

}
