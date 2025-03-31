package ourfood.example.foodforum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ourfood.example.foodforum.entity.Member;
import ourfood.example.foodforum.entity.Recommendation;
import ourfood.example.foodforum.entity.Review;
import ourfood.example.foodforum.exception.ResourceNotFoundException;
import ourfood.example.foodforum.repository.RecommendationRepository;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RecommendationService {

    private final RecommendationRepository recommendationRepository;

    public Recommendation findById(Long id) {
        return recommendationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recommendation not found with id : " + id));
    }


    public Recommendation findByReviewIdAndMemberId(Long reviewId, Long memberId) {
        return recommendationRepository.findByReviewIdAndMemberId(reviewId, memberId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Recommendation not found with reviewId : " + reviewId + ", memberId : " + memberId));
    }

    public List<Recommendation> getAllRecommendations() {
        return recommendationRepository.findAll();
    }


    public Long join(Recommendation recommendation) {
        Recommendation savedRecommendation = recommendationRepository.save(recommendation);
        return savedRecommendation.getId();
    }

    public void addRecommendation(Recommendation recommendation, Review review, Member member) {
        recommendation.addRecommendation(review, member);
    }

    public void deleteRecommendation(Recommendation recommendation) {
        recommendationRepository.delete(recommendation);
    }


}
