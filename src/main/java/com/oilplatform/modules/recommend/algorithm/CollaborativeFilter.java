package com.oilplatform.modules.recommend.algorithm;

import org.springframework.stereotype.Component;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class CollaborativeFilter {

    public List<Long> recommend(Map<Long, Map<Long, Double>> userItemMatrix,
                                Long targetUserId, int k) {

        if (!userItemMatrix.containsKey(targetUserId)) {
            return new ArrayList<>();
        }

        Map<Long, Double> targetUserRatings = userItemMatrix.get(targetUserId);

        Map<Long, Double> similarities = new HashMap<>();
        for (Map.Entry<Long, Map<Long, Double>> entry : userItemMatrix.entrySet()) {
            Long otherUserId = entry.getKey();
            if (!otherUserId.equals(targetUserId)) {
                double similarity = calculateCosineSimilarity(targetUserRatings, entry.getValue());
                similarities.put(otherUserId, similarity);
            }
        }

        int n = 10;
        List<Long> nearestUsers = similarities.entrySet().stream()
                .sorted(Map.Entry.<Long, Double>comparingByValue().reversed())
                .limit(n)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        Map<Long, Double> predictedRatings = new HashMap<>();
        Set<Long> allCourses = new HashSet<>();

        for (Long userId : nearestUsers) {
            if (userItemMatrix.containsKey(userId)) {
                allCourses.addAll(userItemMatrix.get(userId).keySet());
            }
        }

        allCourses.removeAll(targetUserRatings.keySet());

        for (Long courseId : allCourses) {
            double predictedRating = predictRating(userItemMatrix, similarities,
                    nearestUsers, targetUserId, courseId);
            if (predictedRating > 0) {
                predictedRatings.put(courseId, predictedRating);
            }
        }

        return predictedRatings.entrySet().stream()
                .sorted(Map.Entry.<Long, Double>comparingByValue().reversed())
                .limit(k)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private double calculateCosineSimilarity(Map<Long, Double> ratings1,
                                             Map<Long, Double> ratings2) {
        Set<Long> commonItems = new HashSet<>(ratings1.keySet());
        commonItems.retainAll(ratings2.keySet());

        if (commonItems.isEmpty()) {
            return 0.0;
        }

        double dotProduct = 0.0;
        double norm1 = 0.0;
        double norm2 = 0.0;

        for (Long itemId : commonItems) {
            double r1 = ratings1.get(itemId);
            double r2 = ratings2.get(itemId);
            dotProduct += r1 * r2;
        }

        for (double rating : ratings1.values()) {
            norm1 += rating * rating;
        }

        for (double rating : ratings2.values()) {
            norm2 += rating * rating;
        }

        return dotProduct / (Math.sqrt(norm1) * Math.sqrt(norm2));
    }

    private double predictRating(Map<Long, Map<Long, Double>> userItemMatrix,
                                 Map<Long, Double> similarities,
                                 List<Long> nearestUsers,
                                 Long targetUserId,
                                 Long courseId) {

        double weightedSum = 0.0;
        double similaritySum = 0.0;

        for (Long userId : nearestUsers) {
            Map<Long, Double> userRatings = userItemMatrix.get(userId);
            if (userRatings != null && userRatings.containsKey(courseId)) {
                double similarity = similarities.getOrDefault(userId, 0.0);
                weightedSum += similarity * userRatings.get(courseId);
                similaritySum += Math.abs(similarity);
            }
        }

        return similaritySum == 0 ? 0 : weightedSum / similaritySum;
    }

    public List<Long> contentBasedRecommend(Map<Long, String> courseKeywords,
                                            String userInterests,
                                            int k) {
        Map<Long, Double> scores = new HashMap<>();
        String[] interestWords = userInterests.split(",");

        for (Map.Entry<Long, String> entry : courseKeywords.entrySet()) {
            Long courseId = entry.getKey();
            String keywords = entry.getValue();

            double score = 0;
            for (String word : interestWords) {
                if (keywords.contains(word.trim())) {
                    score += 1.0;
                }
            }

            if (score > 0) {
                scores.put(courseId, score);
            }
        }

        return scores.entrySet().stream()
                .sorted(Map.Entry.<Long, Double>comparingByValue().reversed())
                .limit(k)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}