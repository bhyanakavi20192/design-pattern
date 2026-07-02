package com.example.patterns.model;

import java.util.List;

public record DesignPattern(
        String name,
        PatternCategory category,
        String intent,
        String springExample,
        String whenToUse,
        String tradeOff,
        String realLifeExample,
        String interviewAnswer,
        String memoryTrick,
        int popularity,
        int complexity,
        List<String> participants,
        String diagram
) {
}
