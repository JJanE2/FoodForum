package ourfood.example.foodforum.dto.review;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * ourfood.example.foodforum.dto.review.QReviewDTO_RestaurantReview is a Querydsl Projection type for RestaurantReview
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QReviewDTO_RestaurantReview extends ConstructorExpression<ReviewDTO.RestaurantReview> {

    private static final long serialVersionUID = -1218957840L;

    public QReviewDTO_RestaurantReview(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<String> memberNickname, com.querydsl.core.types.Expression<String> content, com.querydsl.core.types.Expression<Double> starRating, com.querydsl.core.types.Expression<java.time.LocalDateTime> date) {
        super(ReviewDTO.RestaurantReview.class, new Class<?>[]{long.class, String.class, String.class, double.class, java.time.LocalDateTime.class}, id, memberNickname, content, starRating, date);
    }

}

