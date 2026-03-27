package com.example.carreragent.controller;

import com.example.carreragent.service.CareerService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class CareerController {

    private final CareerService service;

    public CareerController(CareerService service) {
        this.service = service;
    }

    // Skill input API
    @PostMapping("/analyze")
    public Map<String, Object> analyze(@RequestBody List<String> skills) {
        return service.analyzeSkills(skills);
    }

    // Resume upload API (PDF + TXT)
    @PostMapping("/upload")
    public Map<String, Object> uploadResume(@RequestParam("file") MultipartFile file) {
        try {
            String content;

            if (file.getOriginalFilename().endsWith(".pdf")) {
                PDDocument document = PDDocument.load(file.getInputStream());
                PDFTextStripper stripper = new PDFTextStripper();
                content = stripper.getText(document);
                document.close();
            } else {
                content = new String(file.getBytes());
            }

            List<String> skills = Arrays.asList(content.toLowerCase().split("\\W+"));

            return service.analyzeSkills(skills);

        } catch (Exception e) {
            return Map.of("error", "Failed to process file");
        }
    }
}