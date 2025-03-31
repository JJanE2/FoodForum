package ourfood.example.foodforum.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ourfood.example.foodforum.dto.recommendation.RecommendationDTO;
import ourfood.example.foodforum.entity.Member;
import ourfood.example.foodforum.entity.Recommendation;
import ourfood.example.foodforum.entity.Review;
import ourfood.example.foodforum.service.MemberService;
import ourfood.example.foodforum.service.RecommendationService;
import ourfood.example.foodforum.service.ReviewService;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Transactional
@RequestMapping("/api")
public class RecommendationApi {

    private final RecommendationService recommendationService;
    private final ReviewService reviewService;
    private final MemberService memberService;


    @PostMapping("/recommendation/new")
    public ResponseEntity<Map<String, Object>> toggleRecommendation(@AuthenticationPrincipal UserDetails userDetails,
                                                    @RequestBody RecommendationDTO.Create recommendationDTO) {

        // 로그인하지 않은 사용자 안내 메세지 이후 로그인창으로 이동 ( 로그인창 이동은 javascript )
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "로그인이 필요한 서비스입니다"));
        }

        Review review = reviewService.findById(recommendationDTO.getReviewId());
        Member member = memberService.findByName(userDetails.getUsername());

        // 사용자가 아직 추천하지 않은 리뷰는 Recommendation 객체 추가
        if (!member.isRecommended(review.getId())) {
            Recommendation recommendation = new Recommendation(review, member);
            recommendation.addRecommendation(review, member);
            recommendationService.join(recommendation);
            int updatedRecommendationCount = review.getRecommendations().size();

            return ResponseEntity.ok(
                    Map.of("message", "추천 되었습니다",
                            "updatedRecommendationCount", updatedRecommendationCount));
        }
        // 사용자가 이미 추천했던 리뷰는 기존 Recommendation 객체 제거
        else {
            Recommendation findRecommendation = recommendationService.findByReviewIdAndMemberId(review.getId(), member.getId());
            findRecommendation.removeRecommendation(review, member);
            recommendationService.deleteRecommendation(findRecommendation);
            int updatedRecommendationCount = review.getRecommendations().size();

            return ResponseEntity.ok(
                    Map.of("message", "추천 취소 되었습니다",
                            "updatedRecommendationCount", updatedRecommendationCount));
        }
    }
}

