package com.texoit.movie.controller;

import com.texoit.movie.dto.ProducerIntervalDTO;
import com.texoit.movie.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/producer")
public class ProducerController {

    @Autowired
    @Lazy
    private ProducerService producerService;

    @GetMapping("/min-max")
    public ResponseEntity<Map<String, List<ProducerIntervalDTO>>> findProducerIntervalsMinMax() {
        return ResponseEntity.ok().body(producerService.findProducerIntervalsMinMax());
    }
}
