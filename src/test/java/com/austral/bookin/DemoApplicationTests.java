package com.austral.bookin;

import org.apache.velocity.app.VelocityEngine;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class DemoApplicationTests {

	@MockBean
	private VelocityEngine velocityEngine;

	@Test
	void contextLoads() {

	}
}
