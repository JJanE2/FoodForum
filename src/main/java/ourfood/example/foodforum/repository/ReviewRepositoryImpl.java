package ourfood.example.foodforum.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import ourfood.example.foodforum.dto.review.QReviewDTO_RestaurantReview;
import ourfood.example.foodforum.dto.review.QReviewDTO_Search;
import ourfood.example.foodforum.dto.review.ReviewDTO;
import ourfood.example.foodforum.entity.Review;

import java.util.List;

import static ourfood.example.foodforum.entity.QRestaurant.restaurant;
import static ourfood.example.foodforum.entity.QReview.review;

@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ReviewDTO.RestaurantReview> getRestaurantReviewsById(Long restaurantId, Long cursorId, Pageable pageable) {
        return queryFactory
                .select(new QReviewDTO_RestaurantReview(
                        review.id, review.member.nickname, review.content, review.starRating, review.date
                ))
                .from(review)
                .where(review.restaurant.id.eq(restaurantId)
                        .and(cursorId != null ? review.id.lt(cursorId) : null))
                .orderBy(review.id.desc())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public Page<ReviewDTO.Search> getSearchPage(String searchCond, String keyword, Pageable pageable) {

        JPAQuery<ReviewDTO.Search> query = queryFactory
                .select(new QReviewDTO_Search(
                        review.id, review.member.nickname, review.content
                ))
                .from(review)
                .where(searchCond(searchCond, keyword)
                );

        List<ReviewDTO.Search> results = query.offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(review.count())
                .from(review)
                .where(searchCond(searchCond, keyword));

        return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchCount);
    }

    private Predicate searchCond(String searchCond, String keyword) {
        if (searchCond.equals("nickname")) {
            return keyword != null ? review.member.nickname.contains(keyword) : null;
        } else {
            return keyword != null ? review.content.containsIgnoreCase(keyword) : null;
        }
    }
}
