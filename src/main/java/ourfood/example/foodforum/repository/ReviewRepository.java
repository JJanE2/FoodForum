package ourfood.example.foodforum.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ourfood.example.foodforum.dto.review.ReviewDTO;
import ourfood.example.foodforum.entity.Review;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewRepositoryCustom {
    List<ReviewDTO.RestaurantReview> getRestaurantReviewsById(Long restaurantId, Long cursorId, Pageable pageable);
}
