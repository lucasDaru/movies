package com.texoit.movie.controller;

import com.texoit.movie.entity.Studio;
import com.texoit.movie.service.StudioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/studios")
@Api(tags = "Studios", description = "Endpoints for managing studios")
public class StudioController {

    @Autowired
    private StudioService studioService;

    @GetMapping("/{name}")
    @ApiOperation("Get studio by name")
    public ResponseEntity<?> getStudioByName(@PathVariable String name) {
        Optional<Studio> studio = studioService.findStudioByName(name);
        if (studio.isPresent()) {
            return ResponseEntity.ok(studio.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

