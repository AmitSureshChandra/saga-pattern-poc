package com.github.AmitSureshChandra.integrationtests;

import com.github.AmitSureshChandra.integrationtests.util.BaseTestContainer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class IntegrationTestsApplicationTests extends BaseTestContainer {

	@Test
	void contextLoads() {
		System.out.println("All working");
	}

}
