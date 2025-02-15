package ourfood.example.foodforum.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ourfood.example.foodforum.entity.Member;
import ourfood.example.foodforum.entity.Restaurant;
import ourfood.example.foodforum.entity.Review;
import ourfood.example.foodforum.service.AdminService;
import ourfood.example.foodforum.service.MemberService;
import ourfood.example.foodforum.service.RestaurantService;
import ourfood.example.foodforum.service.ReviewService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AdminApi {

    private final MemberService memberService;
    private final RestaurantService restaurantService;
    private final ReviewService reviewService;
    private final AdminService adminService;

    @PostMapping("/members/delete")
    public ResponseEntity<String> deleteMember(@RequestBody Long memberId) {

        try {
            Member member = memberService.findById(memberId);
            memberService.deleteMember(member);
            return ResponseEntity.ok("회원이 성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원 삭제 중 오류가 발생했습니다.");
        }
    }

    @PostMapping("/members/suspend")
    public ResponseEntity<String> suspendMember(@RequestBody Long memberId) {

        try {
            adminService.suspendMember(memberId);
            return ResponseEntity.ok("계정이 제한되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("계정 제한 중 오류가 발생했습니다.");
        }
    }

    @PostMapping("/members/activate")
    public ResponseEntity<String> activeMember(@RequestBody Long memberId) {

        try {
            adminService.activateMember(memberId);
            return ResponseEntity.ok("계정이 활성화되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("계정 활성화 중 오류가 발생했습니다.");
        }
    }
    @PostMapping("/restaurants/delete")
    public ResponseEntity<String> deleteRestaurant(@RequestBody Long restaurantId) {

        try {
            Restaurant restaurant = restaurantService.findById(restaurantId);
            restaurantService.deleteRestaurant(restaurant);
            return ResponseEntity.ok("식당이 성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("식당 삭제 중 오류가 발생했습니다.");
        }
    }

    @PostMapping("/reviews/delete")
    public ResponseEntity<String> deleteReview(@RequestBody Long reviewId) {

        try {
            Review review = reviewService.findById(reviewId);
            reviewService.deleteReview(review);
            return ResponseEntity.ok("리뷰가 성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("리뷰 삭제 중 오류가 발생했습니다.");
        }
    }


}
