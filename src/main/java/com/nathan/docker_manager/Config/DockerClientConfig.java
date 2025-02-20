package com.nathan.docker_manager.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;

/*
 * Class Responsabilities:
 * 
 * Definition of a BEAN method, managed by Spring, witch can be injected into other classes.
 * 
 * This method will return an object containing:
 * -> Configuration of docker client with defined host and TLS authentication disabled (host connection will be via unix usocket).
 * -> HTTP client to make requests between the application and de docker daemon.
 * -> Final instance of the docker client with all the necessary configurations to comunicate with docker.  
 */

@Configuration
public class DockerClientConfig {
    @Value("${docker.socket.path}")
    private String dockerSocketPath;

    @Bean
    public DockerClient buildDockerClient() {
        DefaultDockerClientConfig.Builder dockerClientConfigBuilder = DefaultDockerClientConfig.createDefaultConfigBuilder();

        if (this.dockerSocketPath != null && this.dockerSocketPath.startsWith("unix://")) {
            dockerClientConfigBuilder.withDockerHost(dockerSocketPath).withDockerTlsVerify(false);
        }

        DefaultDockerClientConfig dockerClientConfig = dockerClientConfigBuilder.build();  

        ApacheDockerHttpClient dockerHttpClient = new ApacheDockerHttpClient.Builder().dockerHost(dockerClientConfig.getDockerHost()).build();
        
        return DockerClientBuilder.getInstance(dockerClientConfig).withDockerHttpClient(dockerHttpClient).build();
    }
}
