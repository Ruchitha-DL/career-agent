package com.example.carreragent.controller;

import com.example.carreragent.service.CareerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // 🔥 VERY IMPORTANT
public class CareerController {

    @Autowired
    private CareerService careerService;

    @PostMapping("/analyze")
    public Map<String, Object> analyze(@RequestBody List<String> skills) {
        return careerService.analyzeSkills(skills);
    }
}