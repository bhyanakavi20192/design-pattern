package com.example.patterns.controller;

import com.example.patterns.model.PatternCategory;
import com.example.patterns.service.DesignPatternService;
import com.example.patterns.service.JavaLearningService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;

@Controller
public class PatternController {

    private final DesignPatternService designPatternService;
    private final JavaLearningService javaLearningService;

    public PatternController(DesignPatternService designPatternService, JavaLearningService javaLearningService) {
        this.designPatternService = designPatternService;
        this.javaLearningService = javaLearningService;
    }

    @GetMapping("/")
    public String index(Model model) {
        var patterns = designPatternService.findAll();
        model.addAttribute("patterns", patterns);
        model.addAttribute("categories", Arrays.asList(PatternCategory.values()));
        model.addAttribute("categoryCounts", designPatternService.countByCategory());
        model.addAttribute("patternNames", patterns.stream().map(pattern -> pattern.name()).toList());
        model.addAttribute("popularityScores", patterns.stream().map(pattern -> pattern.popularity()).toList());
        model.addAttribute("complexityScores", patterns.stream().map(pattern -> pattern.complexity()).toList());
        model.addAttribute("solidPrinciples", javaLearningService.solidPrinciples());
        model.addAttribute("javaExamples", javaLearningService.interviewExamples());
        return "index";
    }
}
