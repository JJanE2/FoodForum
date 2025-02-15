package ourfood.example.foodforum.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ourfood.example.foodforum.dto.review.ReviewDTO;

import java.util.List;


public interface ReviewRepositoryCustom {
    List<ReviewDTO.RestaurantReview> getRestaurantReviewsById(Long restaurantId, Long cursorId, Pageable pageable);

    Page<ReviewDTO.Search> getSearchPage(String searchCond, String keyword, Pageable pageable);
}
