package ourfood.example.foodforum.dto.restaurant;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * ourfood.example.foodforum.dto.restaurant.QRestaurantDTO_Mine is a Querydsl Projection type for Mine
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QRestaurantDTO_Mine extends ConstructorExpression<RestaurantDTO.Mine> {

    private static final long serialVersionUID = -1746891858L;

    public QRestaurantDTO_Mine(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<String> name, com.querydsl.core.types.Expression<Double> totalRating) {
        super(RestaurantDTO.Mine.class, new Class<?>[]{long.class, String.class, double.class}, id, name, totalRating);
    }

}

