package com.nathan.docker_manager.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Image;

@Service
public class DockerService {
    private final DockerClient dockerClient;

    public DockerService(DockerClient dockerClient) {
        this.dockerClient = dockerClient;
    }

    public List<Image> listImages() {
        return dockerClient.listImagesCmd().exec();
    }

     public List<Image> filterImages(String imageName) {
        // the reference filter; the legacy name filter is ignored by the Docker Engine since API 1.41
        return dockerClient.listImagesCmd().withReferenceFilter(imageName).exec();
    }

    public String createContainer(String imageName) {
        CreateContainerResponse response = dockerClient.createContainerCmd(imageName).exec();
        return response.getId();
    }
    
    public void startContainer(String containerId) {
        dockerClient.startContainerCmd(containerId).exec();
    }
    
    public void stopContainer(String containerId) {
        dockerClient.stopContainerCmd(containerId).exec();
    }
    
    public void deleteContainer(String containerId) {
        dockerClient.removeContainerCmd(containerId).exec();
    }
    
    public List<Container> listContainers(Boolean all) {
        return dockerClient.listContainersCmd().withShowAll(all).exec();
    }
}
