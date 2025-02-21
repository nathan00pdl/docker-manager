package com.nathan.docker_manager.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.dockerjava.api.model.Image;
import com.nathan.docker_manager.services.DockerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/images")
public class ImagesController {
    private final DockerService dockerService;

    public ImagesController(DockerService dockerService) {
        this.dockerService = dockerService;
    }
    
    @GetMapping("")
    public List<Image> listImages() {
        return dockerService.listImages();
    }

    @GetMapping("/filter")
    public List<Image> listImages(@RequestParam(required = false, defaultValue = "image-") String imageName) {
        return dockerService.filterImages(imageName);
    }

}
