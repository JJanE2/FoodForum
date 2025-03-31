package ourfood.example.foodforum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ourfood.example.foodforum.entity.Recommendation;

import java.util.Optional;

public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {

    Optional<Recommendation> findByReviewIdAndMemberId(Long reviewId, Long memberId);
}
