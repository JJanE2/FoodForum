package ourfood.example.foodforum.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ourfood.example.foodforum.dto.restaurant.RestaurantDTO;

public interface RestaurantRepositoryCustom {
    Page<RestaurantDTO.Search> searchPage(String restaurantName, Pageable pageable, String orderField);
    Page<RestaurantDTO.Mine> searchMyRestaurantPage(Long memberId, String restaurantName, Pageable pageable);
}
