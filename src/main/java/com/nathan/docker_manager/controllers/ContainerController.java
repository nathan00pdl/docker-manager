package com.nathan.docker_manager.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nathan.docker_manager.services.DockerService;


@RestController
@RequestMapping("/api/containers")
public class ContainerController {
    private final DockerService dockerService;

    public ContainerController(DockerService dockerService) {
        this.dockerService = dockerService;
    }

    @GetMapping("")
    public List<com.github.dockerjava.api.model.Container> listContainers(@RequestParam(required = false, defaultValue = "true") boolean showAll) {
        return dockerService.listContainers(showAll);
    }

    @PostMapping("")
    public void createContainer(@RequestParam String imageName) { // The annotation is used to bind method parameters to query parameters of an HTTP request 
        dockerService.createContainer(imageName);
    }

    @PostMapping("/{id}/start")
    public void startContainer(@PathVariable String id) { // The annotation links the id of the URL path variable to the method parameter 
        dockerService.starsContainer(id);
    }

    @PostMapping("/{id}/stop")
    public void stopContainer(@PathVariable String id) {
        dockerService.stopContainer(id);
    }

    @PostMapping("/{id}/delete")
    public void deleteContainer(@PathVariable String id) {
        dockerService.deleteContainer(id);
    }
    
}
