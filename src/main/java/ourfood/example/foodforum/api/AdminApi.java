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
@RequestMapping("/api/admin")
public class AdminApi {

    private final MemberService memberService;
    private final RestaurantService restaurantService;
    private final ReviewService reviewService;
    private final AdminService adminService;

    @DeleteMapping("/members/{id}")
    public ResponseEntity<String> deleteMember(@PathVariable(value = "id") Long id) {

        try {
            Member member = memberService.findById(id);
            memberService.deleteMember(member);
            return ResponseEntity.ok("회원이 성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원 삭제 중 오류가 발생했습니다.");
        }
    }

    @PostMapping("/members/{id}/suspend")
    public ResponseEntity<String> suspendMember(@PathVariable(value = "id") Long id) {

        try {
            adminService.suspendMember(id);
            return ResponseEntity.ok("계정이 제한되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("계정 제한 중 오류가 발생했습니다.");
        }
    }

    @PostMapping("/members/{id}/activate")
    public ResponseEntity<String> activeMember(@PathVariable(value = "id") Long id) {

        try {
            adminService.activateMember(id);
            return ResponseEntity.ok("계정이 활성화되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("계정 활성화 중 오류가 발생했습니다.");
        }
    }
    @DeleteMapping("/restaurants/{id}")
    public ResponseEntity<String> deleteRestaurant(@PathVariable(value = "id") Long id) {

        try {
            Restaurant restaurant = restaurantService.findById(id);
            restaurantService.deleteRestaurant(restaurant);
            return ResponseEntity.ok("식당이 성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("식당 삭제 중 오류가 발생했습니다.");
        }
    }

    @DeleteMapping("/reviews/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable(value = "id") Long id) {

        try {
            Review review = reviewService.findById(id);
            reviewService.deleteReview(review);
            return ResponseEntity.ok("리뷰가 성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("리뷰 삭제 중 오류가 발생했습니다.");
        }
    }


}
