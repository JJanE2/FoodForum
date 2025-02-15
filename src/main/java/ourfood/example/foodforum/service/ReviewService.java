package ourfood.example.foodforum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ourfood.example.foodforum.dto.member.MemberDTO;
import ourfood.example.foodforum.dto.review.ReviewDTO;
import ourfood.example.foodforum.entity.Member;
import ourfood.example.foodforum.entity.Restaurant;
import ourfood.example.foodforum.entity.Review;
import ourfood.example.foodforum.exception.CustomAccessDeniedException;
import ourfood.example.foodforum.exception.ResourceNotFoundException;
import ourfood.example.foodforum.repository.ReviewRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberService memberService;
    private final RestaurantService restaurantService;

    public Review findById(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new  ResourceNotFoundException("Review not found with id : " + id));
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Long createReview(Review review) {
        Review savedReview = reviewRepository.save(review);
        return savedReview.getId();
    }

    public void addReview(Review review, Member member, Restaurant restaurant) {
        review.addReview(member, restaurant);
    }


    public Boolean updateReview(Long reviewId, String updateContent, Double updateStarRating) {
        Review findReview = reviewRepository.findById(reviewId).get();
        Boolean canUpdate = canUpdate(findReview);

        if (canUpdate) {
            findReview.updateReview(updateContent, updateStarRating);
            return Boolean.TRUE;
        } else return Boolean.FALSE;
    }

    public Boolean canUpdate(Review review) {
        LocalDateTime now = LocalDateTime.now();
        long daysBetween = ChronoUnit.DAYS.between(review.getDate(), now);

        return daysBetween < 3;
    }

    public void deleteReview(Review review) {
        reviewRepository.delete(review);
    }

    public List<ReviewDTO.RestaurantReview> getRestaurantReviewsByCursor(Long restaurantId, Long cursorId, Pageable pageable) {
        return reviewRepository.getRestaurantReviewsById(restaurantId, cursorId, pageable);
    }

    public void validateReviewOwner(Review review, Member member) {
        if (!review.getMember().getId().equals(member.getId())) {
            throw new CustomAccessDeniedException(String.format("MemberId: %d is not allowed to access ReviewId: %d", member.getId(), review.getId()));
        }
    }


    public Page<ReviewDTO.Search> searchPage(String searchCond, String keyword, Pageable pageable) {
        return reviewRepository.getSearchPage(searchCond, keyword, pageable);
    }
}