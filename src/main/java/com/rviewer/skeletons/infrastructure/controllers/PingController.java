package com.rviewer.skeletons.infrastructure.controllers;

import com.rviewer.skeletons.domain.services.PongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class PingController {

    @Autowired
    private PongService pongService;

    @GetMapping("/ping")
    public ResponseEntity getPing() {
        return ResponseEntity.ok(pongService.getPong());
    }
}
