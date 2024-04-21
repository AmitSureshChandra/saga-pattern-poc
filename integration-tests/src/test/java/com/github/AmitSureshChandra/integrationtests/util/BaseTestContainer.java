package com.github.AmitSureshChandra.integrationtests.util;

import org.springframework.core.io.ClassPathResource;
import org.testcontainers.containers.DockerComposeContainer;

import java.io.IOException;

public class BaseTestContainer {
    public static DockerComposeContainer dockerComposeContainer;

    static {
        try {
            dockerComposeContainer = new DockerComposeContainer(new ClassPathResource("docker-compose.yml").getFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        dockerComposeContainer.start();
    }
}
