package com.example.carreragent.service;

import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class CareerService {

    public Map<String, Object> analyzeSkills(List<String> skills) {

        Map<String, Integer> roleScores = new HashMap<>();
        List<String> suggestions = new ArrayList<>();

        // Valid skills
        Set<String> validSkills = new HashSet<>(Arrays.asList(
                "java", "python", "c", "sql", "excel",
                "html", "css", "javascript",
                "data", "structures", "algorithms",
                "dbms", "git", "github", "cloud", "networking"));

        // Filter skills
        List<String> lowerSkills = new ArrayList<>();

        for (String skill : skills) {
            String s = skill.trim().toLowerCase();
            if (validSkills.contains(s) && !lowerSkills.contains(s)) {
                lowerSkills.add(s);
            }
        }

        // ROLE SCORING
        if (lowerSkills.contains("java"))
            roleScores.put("Backend Developer", 2);
        if (lowerSkills.contains("sql"))
            roleScores.put("Data Analyst", 2);
        if (lowerSkills.contains("html"))
            roleScores.put("Frontend Developer", 2);
        if (lowerSkills.contains("css"))
            roleScores.put("Frontend Developer", 2);
        if (lowerSkills.contains("javascript"))
            roleScores.put("Frontend Developer", 2);
        if (lowerSkills.contains("java") && lowerSkills.contains("html"))
            roleScores.put("Full Stack Developer", 4);
        if (lowerSkills.contains("python"))
            roleScores.put("Software Engineer", 3);
        if (lowerSkills.contains("cloud"))
            roleScores.put("Cloud Engineer", 3);
        if (lowerSkills.contains("networking"))
            roleScores.put("Network Engineer", 3);

        // Sort roles
        List<Map.Entry<String, Integer>> sorted = new ArrayList<>(roleScores.entrySet());
        sorted.sort((a, b) -> b.getValue() - a.getValue());

        List<String> roles = new ArrayList<>();
        int totalScore = 0;

        for (Map.Entry<String, Integer> e : sorted) {
            roles.add(e.getKey());
            totalScore += e.getValue();
        }

        // ✅ ALWAYS SHOW SUGGESTIONS

        if (!lowerSkills.contains("python"))
            suggestions.add("Learn Python for better opportunities");

        if (!lowerSkills.contains("cloud"))
            suggestions.add("Learn Cloud technologies (AWS/Azure)");

        if (!lowerSkills.contains("git"))
            suggestions.add("Learn Git & GitHub for version control");

        // If nothing missing → give improvement suggestions
        if (suggestions.isEmpty()) {
            suggestions.add("Build more real-world projects to strengthen your profile");
            suggestions.add("Practice data structures and algorithms for interviews");
            suggestions.add("Improve system design and problem-solving skills");
        }

        // Match %
        int match = Math.min(totalScore * 10, 100);

        // Advice
        String advice = roles.size() > 0 ? "Focus on top skills and build real-world projects in your strongest area."
                : "Start with core programming and database skills.";

        // LIMITED JOB MATCHING (MAX 5)
        Map<String, List<String>> jobs = new HashMap<>();

        jobs.put("Backend Developer", Arrays.asList("Java Developer - TCS", "Backend Engineer - Infosys"));
        jobs.put("Data Analyst", Arrays.asList("Data Analyst - Deloitte", "Business Analyst - Accenture"));
        jobs.put("Frontend Developer", Arrays.asList("Frontend Developer - Wipro", "UI Developer - Cognizant"));
        jobs.put("Full Stack Developer", Arrays.asList("Full Stack Developer - Zoho"));
        jobs.put("Software Engineer", Arrays.asList("Software Engineer - Infosys"));
        jobs.put("Cloud Engineer", Arrays.asList("Cloud Engineer - AWS"));
        jobs.put("Network Engineer", Arrays.asList("Network Engineer - Cisco"));

        List<String> matchedJobs = new ArrayList<>();

        for (String role : roles) {
            if (jobs.containsKey(role)) {
                matchedJobs.addAll(jobs.get(role));
            }
        }

        // Limit to 5 jobs
        if (matchedJobs.size() > 5) {
            matchedJobs = matchedJobs.subList(0, 5);
        }

        // FINAL RESULT
        Map<String, Object> result = new HashMap<>();
        result.put("roles", roles);
        result.put("score", totalScore);
        result.put("match", match);
        result.put("suggestions", suggestions);
        result.put("advice", advice);
        result.put("jobs", matchedJobs);
        result.put("detectedSkills", lowerSkills);

        return result;
    }
}