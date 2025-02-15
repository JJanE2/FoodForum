package ourfood.example.foodforum.dto.review;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * ourfood.example.foodforum.dto.review.QReviewDTO_Search is a Querydsl Projection type for Search
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QReviewDTO_Search extends ConstructorExpression<ReviewDTO.Search> {

    private static final long serialVersionUID = -906529693L;

    public QReviewDTO_Search(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<String> nickname, com.querydsl.core.types.Expression<String> content) {
        super(ReviewDTO.Search.class, new Class<?>[]{long.class, String.class, String.class}, id, nickname, content);
    }

}

