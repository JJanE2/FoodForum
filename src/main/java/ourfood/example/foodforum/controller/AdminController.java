package ourfood.example.foodforum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ourfood.example.foodforum.dto.member.MemberDTO;
import ourfood.example.foodforum.dto.restaurant.RestaurantDTO;
import ourfood.example.foodforum.dto.review.ReviewDTO;
import ourfood.example.foodforum.entity.Member;
import ourfood.example.foodforum.entity.Restaurant;
import ourfood.example.foodforum.entity.Review;
import ourfood.example.foodforum.paging.PaginationUtil;
import ourfood.example.foodforum.service.MemberService;
import ourfood.example.foodforum.service.RestaurantService;
import ourfood.example.foodforum.service.ReviewService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Transactional
@RequestMapping("/admin")
public class AdminController {

    private final MemberService memberService;
    private final RestaurantService restaurantService;
    private final ReviewService reviewService;
    private final PaginationUtil paginationUtil;

    @GetMapping
    public String getIndexPage() {
        return "admin/index";
    }

    @GetMapping("/members")
    public String getMembersPage(
            @RequestParam(value = "memberName", defaultValue = "") String memberName,
            @RequestParam(value = "page", defaultValue = "0") int page,
            Model model) {

        List<Member> members = memberService.getAllMembers();
        boolean isMembersEmpty = members.isEmpty();
        model.addAttribute("isMembersEmpty", isMembersEmpty);

        int fixedSize = 5;
        Pageable pageable = PageRequest.of(page, fixedSize);
        Page<MemberDTO.Search> memberPage = memberService.searchPage(memberName, pageable);
        model.addAttribute("memberName", memberName);
        model.addAttribute("memberPage", memberPage);

        PaginationUtil.PageDTO pageDTO = paginationUtil.calculatePageDTO(memberPage);

        model.addAttribute("startPage", pageDTO.getStartPage());
        model.addAttribute("endPage", pageDTO.getEndPage());

        return "admin/members";
    }

    @GetMapping("/restaurants")
    public String getRestaurantsPage(
            @RequestParam(value = "restaurantName", defaultValue = "") String restaurantName,
            @RequestParam(value = "page", defaultValue = "0") int page,
            Model model) {

        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        boolean isRestaurantsEmpty = restaurants.isEmpty();
        model.addAttribute("isRestaurantsEmpty", isRestaurantsEmpty);

        String orderField = "totalRating";
        int fixedSize = 5;
        Pageable pageable = PageRequest.of(page, fixedSize);
        Page<RestaurantDTO.Search> restaurantPage = restaurantService.searchConditionPage(restaurantName, pageable, orderField);
        model.addAttribute("restaurantName", restaurantName);
        model.addAttribute("orderField", orderField);
        model.addAttribute("restaurantPage", restaurantPage);

        PaginationUtil.PageDTO pageDTO = paginationUtil.calculatePageDTO(restaurantPage);

        model.addAttribute("startPage", pageDTO.getStartPage());
        model.addAttribute("endPage", pageDTO.getEndPage());

        return "admin/restaurants";
    }

    @GetMapping("/reviews")
    public String getReviewsPage(
            @RequestParam(value = "searchCond", defaultValue = "nickname") String searchCond,
            @RequestParam(value = "keyword", defaultValue = "") String keyword,
            @RequestParam(value = "page", defaultValue = "0") int page,
            Model model) {

        List<Review> reviews = reviewService.getAllReviews();
        boolean isReviewsEmpty = reviews.isEmpty();
        model.addAttribute("isReviewsEmpty", isReviewsEmpty);

        int fixedSize = 5;
        Pageable pageable = PageRequest.of(page, fixedSize);
        Page<ReviewDTO.Search> reviewPage = reviewService.searchPage(searchCond, keyword, pageable);
        model.addAttribute("searchCond", searchCond);
        model.addAttribute("keyword", keyword);
        model.addAttribute("reviewPage", reviewPage);

        PaginationUtil.PageDTO pageDTO = paginationUtil.calculatePageDTO(reviewPage);

        model.addAttribute("startPage", pageDTO.getStartPage());
        model.addAttribute("endPage", pageDTO.getEndPage());

        return "admin/reviews";
    }
}
