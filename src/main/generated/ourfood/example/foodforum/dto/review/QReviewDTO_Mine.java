package ourfood.example.foodforum.dto.review;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * ourfood.example.foodforum.dto.review.QReviewDTO_Mine is a Querydsl Projection type for Mine
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QReviewDTO_Mine extends ConstructorExpression<ReviewDTO.Mine> {

    private static final long serialVersionUID = -1744132658L;

    public QReviewDTO_Mine(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<String> restaurantName, com.querydsl.core.types.Expression<Long> restaurantId, com.querydsl.core.types.Expression<String> content, com.querydsl.core.types.Expression<Double> starRating, com.querydsl.core.types.Expression<java.time.LocalDateTime> date, com.querydsl.core.types.Expression<Integer> recommendationCount) {
        super(ReviewDTO.Mine.class, new Class<?>[]{long.class, String.class, long.class, String.class, double.class, java.time.LocalDateTime.class, int.class}, id, restaurantName, restaurantId, content, starRating, date, recommendationCount);
    }

}

