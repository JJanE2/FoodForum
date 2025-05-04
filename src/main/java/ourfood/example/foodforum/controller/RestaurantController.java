package ourfood.example.foodforum.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ourfood.example.foodforum.dto.restaurant.RestaurantDTO;
import ourfood.example.foodforum.entity.Member;
import ourfood.example.foodforum.entity.Restaurant;
import ourfood.example.foodforum.paging.PaginationUtil;
import ourfood.example.foodforum.service.MemberService;
import ourfood.example.foodforum.service.RestaurantService;

@Controller
@RequiredArgsConstructor
@Transactional
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final MemberService memberService;
    private final PaginationUtil paginationUtil;

    @GetMapping("/restaurants/new")
    public String createRestaurant(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        Member findMember = memberService.findByName(userDetails.getUsername());
        memberService.validMemberRole(findMember);

        return "restaurant/restaurantForm";
    }

    @GetMapping("/restaurants/{id}")
    public String RestaurantDetail(@PathVariable(value = "id") Long id, Model model) {
        Restaurant findRestaurant = restaurantService.findById(id);
        model.addAttribute("id", id);
        return "restaurant/restaurantDetail";
    }

    // 식당 관리 페이지에서 각 식당의 수정하기 버튼 누를시 작동
    @GetMapping("/restaurants/{id}/update")
    public String RestaurantUpdateForm(@PathVariable(value = "id") Long id, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Member findMember = memberService.findByName(userDetails.getUsername());
        Boolean myRestaurant = restaurantService.isMyRestaurant(id, findMember);
        if (myRestaurant) {
            Restaurant findRestaurant = restaurantService.findById(id);

            RestaurantDTO.Update restaurantUpdateDTO =
                    new RestaurantDTO.Update(findRestaurant.getId(), findRestaurant.getName(), findRestaurant.getDescription(),
                            findRestaurant.getMenus(), findRestaurant.getAddress(), findRestaurant.getLatitude(), findRestaurant.getLongitude());
            model.addAttribute("restaurantUpdateDTO", restaurantUpdateDTO);
            return "restaurant/restaurantUpdateForm";
        }
        // 해당 식당의 사장이아닌경우 예외처리
        return "error/403";
    }

    @Operation(summary = "Restaurant 조건 검색, 페이지 처리")
    @GetMapping("/restaurants/search")
    public String conditionPageSearch(
            @RequestParam("restaurantName") String restaurantName,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "orderField", defaultValue = "totalRating") String orderField,
            Model model) {

        int fixedSize = 5;
        Pageable pageable = PageRequest.of(page, fixedSize);
        Page<RestaurantDTO.Search> restaurantPage = restaurantService.searchConditionPage(restaurantName, pageable, orderField);
        model.addAttribute("restaurantName", restaurantName);
        model.addAttribute("orderField", orderField);
        model.addAttribute("restaurantPage", restaurantPage);

        PaginationUtil.PageDTO pageDTO = paginationUtil.calculatePageDTO(restaurantPage);

        model.addAttribute("startPage", pageDTO.getStartPage());
        model.addAttribute("endPage", pageDTO.getEndPage());

        return "restaurant/restaurantSearch";
    }

    @GetMapping("/member/restaurants")
    public String myRestaurantPage(
            @AuthenticationPrincipal UserDetails userDetails, Model model,
            @RequestParam(value = "restaurantName", defaultValue = "") String restaurantName,
            @RequestParam(value = "page", defaultValue = "0") int page) {

        Member member = memberService.findByName(userDetails.getUsername());
        int fixedSize = 5;

        Pageable pageable = PageRequest.of(page, fixedSize);
        Page<RestaurantDTO.Mine> restaurantPage = restaurantService.searchMyRestaurantsConditionPage(member.getId(), restaurantName, pageable);
        model.addAttribute("restaurantName", restaurantName);
        model.addAttribute("restaurantPage", restaurantPage);

        PaginationUtil.PageDTO pageDTO = paginationUtil.calculatePageDTO(restaurantPage);

        model.addAttribute("startPage", pageDTO.getStartPage());
        model.addAttribute("endPage", pageDTO.getEndPage());

        return "restaurant/myRestaurants";
    }
}
