package ourfood.example.foodforum.dto.recommendation;

import lombok.Data;

public class RecommendationDTO {

    @Data
    public static class Create {
        private Long reviewId;
    }
}
