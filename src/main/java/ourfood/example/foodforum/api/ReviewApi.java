package ourfood.example.foodforum.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ourfood.example.foodforum.dto.review.ReviewDTO;
import ourfood.example.foodforum.entity.Member;
import ourfood.example.foodforum.entity.Restaurant;
import ourfood.example.foodforum.entity.Review;
import ourfood.example.foodforum.service.MemberService;
import ourfood.example.foodforum.service.RestaurantService;
import ourfood.example.foodforum.service.ReviewService;
import java.util.List;

@Tag(name = "Review Controller", description = "Review 추가 및 수정")
@RestController
@RequiredArgsConstructor
@Transactional
@RequestMapping("/api")
public class ReviewApi {

    private final ReviewService reviewService;
    private final MemberService memberService;
    private final RestaurantService restaurantService;

    @Operation(summary = "Review 추가")
    @PostMapping("/reviews")
    public ResponseEntity<String> createReview(@RequestBody ReviewDTO.Create reviewDTO, @AuthenticationPrincipal UserDetails userDetails) {
        Member findMember = memberService.findByName(userDetails.getUsername());
        Restaurant findRestaurant = restaurantService.findById(reviewDTO.getRestaurantId());

        Review review = new Review(reviewDTO.getContent(), findMember, findRestaurant, reviewDTO.getStarRating());
        reviewService.addReview(review, findMember, findRestaurant);
        reviewService.createReview(review);

        return ResponseEntity.ok("리뷰가 성공적으로 작성되었습니다.");
    }

    @DeleteMapping("/reviews/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable(value = "id") Long id) {
        Review findReview = reviewService.findById(id);
        reviewService.deleteReview(findReview);
        return ResponseEntity.ok("리뷰가 삭제되었습니다.");
    }

    @PostMapping("/reviews/{id}")
    public ResponseEntity<String> updateReview(@PathVariable("id") Long id, @RequestBody ReviewDTO.Update reviewUpdateDTO) {
        Boolean canUpdate = reviewService.canUpdate(reviewService.findById(id));

        if (!canUpdate) {
            return ResponseEntity.badRequest().body("작성된지 3일 미만의 리뷰만 수정 가능합니다.");
        }

        Boolean isUpdated = reviewService.updateReview(id, reviewUpdateDTO.getContent(), reviewUpdateDTO.getStarRating());
        if (isUpdated) {
            return ResponseEntity.ok("리뷰가 수정되었습니다.");
        } else return ResponseEntity.badRequest().body("리뷰 수정 중 오류가 발생하였습니다.");
    }

    @Operation(summary = "Restaurant 의 리뷰 목록 정보")
    @GetMapping("/restaurants/{id}/reviews")
    public List<ReviewDTO.RestaurantReview> getRestaurantDetail(
            @PathVariable Long id,
            @RequestParam(required = false) Long cursorId,
            @RequestParam(defaultValue = "5") int pageSize) {

        Restaurant restaurant = restaurantService.findById(id);
        Pageable pageable = PageRequest.of(0, pageSize);
        List<ReviewDTO.RestaurantReview> reviews = reviewService.getRestaurantReviewsByCursor(restaurant.getId(), cursorId, pageable);

        return reviews;
    }


}
