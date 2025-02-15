package ourfood.example.foodforum.dto.restaurant;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * ourfood.example.foodforum.dto.restaurant.QRestaurantDTO_Search is a Querydsl Projection type for Search
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QRestaurantDTO_Search extends ConstructorExpression<RestaurantDTO.Search> {

    private static final long serialVersionUID = 736846403L;

    public QRestaurantDTO_Search(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<String> name, com.querydsl.core.types.Expression<String> description, com.querydsl.core.types.Expression<Double> totalRating) {
        super(RestaurantDTO.Search.class, new Class<?>[]{long.class, String.class, String.class, double.class}, id, name, description, totalRating);
    }

}

