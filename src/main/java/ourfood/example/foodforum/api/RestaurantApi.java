package ourfood.example.foodforum.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ourfood.example.foodforum.dto.restaurant.*;
import ourfood.example.foodforum.dto.restaurant.menu.MenuDTO;
import ourfood.example.foodforum.entity.Member;
import ourfood.example.foodforum.entity.Menu;
import ourfood.example.foodforum.entity.Restaurant;
import ourfood.example.foodforum.service.MemberService;
import ourfood.example.foodforum.service.RestaurantService;
import ourfood.example.foodforum.service.ReviewService;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Restaurant Controller", description = "Restaurant 추가 및 조회")
@RestController
@RequiredArgsConstructor
@Transactional
@RequestMapping("/api")
public class RestaurantApi {

    private final RestaurantService restaurantService;
    private final MemberService memberService;

    @Operation(summary = "모든 Restaurant 조회")
    @GetMapping("/restaurants")
    public List<RestaurantDTO.BasicInfo> getAllRestaurants(
            @RequestParam(value = "cursorId", required = false) Long cursorId,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize
    ) {
        return restaurantService.getRestaurantByCursor(cursorId, pageSize);
    }

    // Restaurant 등록
    @Operation(summary = "Restaurant 추가")
    @PostMapping("/restaurants")
    public ResponseEntity<String> postRestaurant(@RequestBody RestaurantDTO.Create restaurantCreateDTO,
                                                 @AuthenticationPrincipal UserDetails userDetails) {
        Member findMember = memberService.findByName(userDetails.getUsername());
        Restaurant restaurant = new Restaurant(findMember, restaurantCreateDTO.getRestaurantName(), restaurantCreateDTO.getDescription(),
                restaurantCreateDTO.getAddress(), restaurantCreateDTO.getLatitude(), restaurantCreateDTO.getLongitude());


        if (restaurantService.join(restaurant) != null) {
            findMember.addRestaurant(restaurant);
            Long restaurantId = restaurant.getId();
            addMenuToRestaurant(restaurantId, restaurantCreateDTO.getMenus());

            return ResponseEntity.ok("식당이 성공적으로 등록되었습니다.");
        } else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("식당 등록중 오류가 발생했습니다.");
    }

    // menus 받아 restaurant에 menu 추가 하는 편의 메서드
    private void addMenuToRestaurant(Long restaurantId, List<Menu> menus) {
        for (Menu menu : menus) {
            restaurantService.addMenu(menu.getName(), menu.getPrice(), restaurantId);
        }
    }

    @Operation(summary = "Restaurant 이름으로 조회")
    @GetMapping("/restaurant")
    public Restaurant searchRestaurantByName(@RequestParam String name) {
        return restaurantService.findByName(name);
    }

    @Operation(summary = "Restaurant 상세조회 (리뷰 목록은 ReviewControllerApi 에서 처리)")
    @GetMapping("/restaurants/{id}")
    public RestaurantDTO.DetailInfo getRestaurantDetail(@PathVariable(value = "id") Long id) {

        Restaurant restaurant = restaurantService.findById(id);
        restaurantService.calculateTotalRating(restaurant);

        List<MenuDTO.Create> menus = restaurant.getMenus().stream()
                .map(menu -> new MenuDTO.Create(menu.getName(), menu.getPrice()))
                .collect(Collectors.toList());

        return new RestaurantDTO.DetailInfo(restaurant.getId(), restaurant.getName(), restaurant.getDescription(),
                restaurant.getTotalRating(), menus,
                restaurant.getAddress(), restaurant.getLatitude(), restaurant.getLongitude());
    }

    // Restaurant menu 추가후 상세페이지 이동을 위해 restaurantId 반환
    @Operation(summary = "Restaurant 의 Menu 추가")
    @PostMapping("/restaurants/{id}/menus")
    public Long addMenu(@PathVariable(value = "id") Long id, @RequestBody List<Menu> menus) {
        addMenuToRestaurant(id, menus);
        return id;
    }

    // restaurant 기본 정보 수정
    @PostMapping("/restaurants/{id}")
    public Long updateRestaurantInfo(@PathVariable Long id, @RequestBody RestaurantDTO.UpdateRequest restaurantRequestDTO) {
        return restaurantService.updateRestaurant(id, restaurantRequestDTO.getName(), restaurantRequestDTO.getDescription(), restaurantRequestDTO.getMenus(),
                restaurantRequestDTO.getAddress(), restaurantRequestDTO.getLatitude(), restaurantRequestDTO.getLongitude());
    }

    @Operation(summary = "Restaurant 조건 검색 (이름, 별점)")
    @GetMapping("/restaurants/condition")
    public List<RestaurantDTO.BasicInfo> conditionSearch(@RequestParam(required = false) String restaurantName,
                                                         @RequestParam(required = false) Double minimumRating) {
        return restaurantService.conditionSearch(restaurantName, minimumRating);
    }

    @DeleteMapping("/restaurants/{id}")
    public ResponseEntity<String> deleteRestaurant(@PathVariable(value = "id") Long restaurantId) {
        Restaurant restaurant = restaurantService.findById(restaurantId);
        restaurantService.deleteRestaurant(restaurant);

        return ResponseEntity.ok("식당이 성공적으로 삭제되었습니다.");
    }
}