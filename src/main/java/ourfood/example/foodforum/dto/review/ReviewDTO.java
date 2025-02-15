package ourfood.example.foodforum.dto.review;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;
import ourfood.example.foodforum.entity.Member;
import ourfood.example.foodforum.entity.ROLE;

import java.time.LocalDateTime;

public class ReviewDTO {

    @Data
    public static class Create {
        private String content;
        private Long restaurantId;
        private Double starRating;
    }

    @Data
    public static class Update {
        private String content;
        private Double starRating;
    }

    @Data
    public static class Mine {
        private Long id;
        private String restaurantName;
        private Long restaurantId;
        private String content;
        private Double starRating;
        @JsonFormat(pattern = "yyyy/MM/dd")
        private LocalDateTime date;

        @QueryProjection
        public Mine(Long id, String restaurantName, Long restaurantId, String content, Double starRating, LocalDateTime date) {
            this.id = id;
            this.restaurantName = restaurantName;
            this.restaurantId = restaurantId;
            this.content = content;
            this.starRating = starRating;
            this.date = date;
        }
    }

    @Data
    public static class Search {
        private Long id;
        private String nickname;
        private String content;

        @QueryProjection
        public Search(Long id, String nickname, String content) {
            this.id = id;
            this.nickname = nickname;
            this.content = content;
        }
    }


    @Data
    @NoArgsConstructor
    public static class RestaurantReview {
        private Long id;
        private String memberNickname;
        private String content;
        private Double starRating;
        @JsonFormat(pattern = "yyyy/MM/dd")
        private LocalDateTime date;

        @QueryProjection
        public RestaurantReview(Long id, String memberNickname, String content, Double starRating, LocalDateTime date) {
            this.id = id;
            this.memberNickname = memberNickname;
            this.content = content;
            this.starRating = starRating;
            this.date = date;
        }
    }

}
