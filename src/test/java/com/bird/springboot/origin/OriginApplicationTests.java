package com.bird.springboot.origin;

import org.assertj.core.api.Assumptions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OriginApplicationTests {

	@Test
	public void contextLoads() {
		Assumptions.assumeThat("bird")
				.contains("ir");
	}


}
