package com.arukione.curriculum_design;

import com.arukione.curriculum_design.mapper.AdminMapper;
import com.arukione.curriculum_design.mapper.ProfessionMapper;
import com.arukione.curriculum_design.mapper.TopicInfoMapper;
import com.arukione.curriculum_design.model.entity.Admin;
import com.arukione.curriculum_design.model.entity.Profession;
import com.arukione.curriculum_design.service.UserService;
import com.arukione.curriculum_design.utils.Generator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class CurriculumDesignApplicationTests {

	@Autowired
	AdminMapper adminMapper;
	@Autowired
    UserService userService;
	@Autowired
	ProfessionMapper professionMapper;
	@Resource
	RedisTemplate<String, Object> redisTemplate;
	@Autowired
	TopicInfoMapper topicInfoMapper;

	@Test
	void contextLoads() {
	}

	@Test
	void UUIDTest() {
		System.out.println(Generator.generateAccessToken());
	}

	@Test
	void mpTest(){
		System.out.println(userService.adminLogin("admin","jiebin22"));
	}

	@Test
	void redisTest() {
		Admin admin = new Admin();
		admin.setAdminId("2");
		admin.setPassword("3");
		admin.setUserType("admin");
		System.out.println(admin+":"+admin.getUserType());
		redisTemplate.opsForValue().set("test",admin,1, TimeUnit.MINUTES);
		Admin admin1 = (Admin) redisTemplate.opsForValue().get("test");
		System.out.println(admin1+":"+admin.getUserType());
	}

	@Test
	void professionTest(){
		List<Profession> professions = professionMapper.getProfessionsIncludeDepartment();
		System.out.println(professions);
	}

	@Test
	void topicIDTest(){
		System.out.println(Generator.generateTopicID());
	}

	@Test
	void getTopicN() {
		System.out.println(topicInfoMapper.getTopicN("10021"));
	}
}
