package ourfood.example.foodforum.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import ourfood.example.foodforum.dto.restaurant.*;
import ourfood.example.foodforum.entity.Restaurant;

import java.util.List;

import static ourfood.example.foodforum.entity.QRestaurant.restaurant;
import static ourfood.example.foodforum.util.QuerydslUtil.dynamicOrder;

@RequiredArgsConstructor
public class RestaurantRepositoryImpl implements RestaurantRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<RestaurantDTO.Search> searchPage(String restaurantName, Pageable pageable, String orderField) {

        JPAQuery<RestaurantDTO.Search> query = queryFactory
                .select(new QRestaurantDTO_Search(
                        restaurant.id, restaurant.name, restaurant.description, restaurant.totalRating
                ))
                .from(restaurant)
                .where(restaurantNameContain(restaurantName)
                )
                .orderBy(getOrderCondition(orderField)
                );

        List<RestaurantDTO.Search> results = query.offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Restaurant> countQuery = queryFactory
                .selectFrom(restaurant)
                .where(restaurantNameContain(restaurantName));

        return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchCount);
    }

    @Override
    public Page<RestaurantDTO.Mine> searchMyRestaurantPage(Long memberId, String restaurantName, Pageable pageable) {

        JPAQuery<RestaurantDTO.Mine> query = queryFactory
                .select(new QRestaurantDTO_Mine(
                        restaurant.id, restaurant.name, restaurant.totalRating
                ))
                .from(restaurant)
                .where(
                        restaurant.member.id.eq(memberId),
                        restaurantNameContain(restaurantName)
                );

        dynamicOrder(pageable, query, restaurant);

        List<RestaurantDTO.Mine> results = query.offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Restaurant> countQuery = queryFactory
                .selectFrom(restaurant)
                .where(
                        restaurant.member.id.eq(memberId),
                        restaurantNameContain(restaurantName)
                );

        return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchCount);
    }

    private Predicate restaurantNameContain(String restaurantName) {
        return restaurantName != null ? restaurant.name.containsIgnoreCase(restaurantName) : null;
    }

    private Predicate ratingGoe(Double rating) { return rating != null ? restaurant.totalRating.goe(rating) : null; }

    private OrderSpecifier<?> getOrderCondition(String orderField) {
        if (orderField.equals("totalRating")) {
            return new OrderSpecifier<>(Order.DESC, restaurant.totalRating);
        } else if (orderField.equals("reviewCount")) {
            return new OrderSpecifier<>(Order.DESC, restaurant.reviews.size());
        } return new OrderSpecifier<>(Order.ASC, restaurant.name);
    }
}
