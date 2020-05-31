package com.arukione.curriculum_design;

import com.arukione.curriculum_design.mapper.AdminMapper;
import com.arukione.curriculum_design.service.LoginService;
import com.arukione.curriculum_design.utils.Generator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CurriculumDesignApplicationTests {

	@Autowired
	AdminMapper adminMapper;
	@Autowired
	LoginService loginService;

	@Test
	void contextLoads() {
	}

	@Test
	void UUIDTest() {
		System.out.println(Generator.generateAccessToken());
	}

	@Test
	void mpTest(){
		System.out.println(loginService.adminLogin("admin","jiebin22"));
	}

}
