package ourfood.example.foodforum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ourfood.example.foodforum.entity.Member;
import ourfood.example.foodforum.entity.Review;
import ourfood.example.foodforum.service.MemberService;
import ourfood.example.foodforum.service.RestaurantService;
import ourfood.example.foodforum.service.ReviewService;

@Controller
@RequiredArgsConstructor
@Transactional
public class ReviewController {
    private final ReviewService reviewService;
    private final RestaurantService restaurantService;
    private final MemberService memberService;

    @GetMapping("/reviews")
    public String memberReviewsPage() {
        return "review/reviews";
    }

    //리뷰 작성 form으로 이동
    @GetMapping("/{restaurantId}/review/new")
    public String createReview(@PathVariable(value = "restaurantId") Long restaurantId, Model model) {
        restaurantService.findById(restaurantId);
        model.addAttribute("restaurantId", restaurantId);
        return "review/reviewForm";
    }

    // 리뷰 수정 form 으로 이동
    @GetMapping("/reviews/{id}/update")
    public String reviewUpdateForm(@PathVariable(value = "id") Long reviewId, Model model,
                                   @AuthenticationPrincipal UserDetails userDetails) {
        Review findReview = reviewService.findById(reviewId);
        Member findMember = memberService.findByName(userDetails.getUsername());

        reviewService.validateReviewOwner(findReview, findMember);

        String existingReview = findReview.getContent();
        Double existingRating = findReview.getStarRating();

        model.addAttribute("reviewId", reviewId);
        model.addAttribute("existingReview", existingReview);
        model.addAttribute("existingRating", existingRating);
        return "review/reviewUpdateForm";
    }
}
