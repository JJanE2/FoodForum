package ourfood.example.foodforum.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ourfood.example.foodforum.dto.restaurant.RestaurantDTO;
import ourfood.example.foodforum.dto.restaurant.menu.MenuDTO;
import ourfood.example.foodforum.entity.Member;
import ourfood.example.foodforum.entity.Menu;
import ourfood.example.foodforum.entity.QRestaurant;
import ourfood.example.foodforum.entity.Restaurant;
import ourfood.example.foodforum.exception.ResourceNotFoundException;
import ourfood.example.foodforum.repository.MenuRepository;
import ourfood.example.foodforum.repository.RestaurantRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;
    private final JPAQueryFactory queryFactory;
    private final MenuService menuService;

    public Restaurant findById(Long id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id : " + id));
    }

    public Restaurant findByName(String name) {
        return restaurantRepository.findByName(name);
    }

    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    public List<RestaurantDTO.BasicInfo> findRestaurants() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        return restaurants.stream()
                .map(restaurant -> new RestaurantDTO.BasicInfo(restaurant.getId(), restaurant.getName(), restaurant.getDescription(), restaurant.calculateTotalRating()))
                .collect(Collectors.toList());
    }

    public Long join(Restaurant restaurant) {
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        return savedRestaurant.getId();
    }

    public Double calculateTotalRating(Restaurant restaurant) {
        double totalRating = restaurant.calculateTotalRating();
        restaurant.setTotalRating(totalRating);
        return restaurant.getTotalRating();
    }

    public Long addMenu(String menuName, String menuPrice, Long restaurantId) {
        Restaurant findRestaurant = restaurantRepository.findById(restaurantId).get();
        Menu menu = new Menu(menuName, menuPrice, findRestaurant);
        menuRepository.save(menu);
        findRestaurant.addMenu(menu);
        return menu.getId();
    }

    public List<RestaurantDTO.BasicInfo> conditionSearch(String restaurantName, Double minimumRating) {
        QRestaurant restaurant = QRestaurant.restaurant;

        List<Restaurant> restaurants = queryFactory.selectFrom(restaurant)
                .where(
                        restaurant.name.containsIgnoreCase(restaurantName),
                        restaurant.totalRating.goe(minimumRating)
                )
                .orderBy(restaurant.totalRating.desc())
                .fetch();

        return restaurants.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private RestaurantDTO.BasicInfo convertToDTO(Restaurant restaurant) {
        return new RestaurantDTO.BasicInfo(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getDescription(),
                restaurant.getTotalRating()
        );
    }

    public Page<RestaurantDTO.Search> searchConditionPage(String restaurantName, Pageable pageable, String orderField) {
        return restaurantRepository.searchPage(restaurantName, pageable, orderField);
    }

    public Page<RestaurantDTO.Mine> searchMyRestaurantsConditionPage(Long ownerId, String restaurantName, Pageable pageable) {
        return restaurantRepository.searchMyRestaurantPage(ownerId, restaurantName, pageable);
    }


    public Boolean isMyRestaurant(Long restaurantId, Member member) {
        Restaurant findRestaurant = restaurantRepository.findById(restaurantId).orElse(null);

        if (findRestaurant == null) {
            return false;
        }
        Member findRestaurantMember = findRestaurant.getMember();
        return findRestaurantMember.equals(member);
    }

    public void deleteRestaurant(Restaurant restaurant) {
        restaurantRepository.delete(restaurant);
    }

    public Long updateRestaurant(Long restaurantId, String newName, String newDescription, List<MenuDTO.Update> newMenus) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).get();
        for (MenuDTO.Update newMenu : newMenus) {
            menuService.updateMenu(newMenu.getId(), newMenu.getName(), newMenu.getPrice());
        }
        return restaurant.updateRestaurant(newName, newDescription);
    }

    public List<RestaurantDTO.BasicInfo> getRestaurantByCursor(Long cursorId, int pageSize) {
        if (cursorId == null) {
            List<Restaurant> restaurants = restaurantRepository.findAll(PageRequest.of(0, pageSize, Sort.by(Sort.Direction.DESC, "id"))).getContent();
            return restaurants.stream()
                    .map(restaurant -> new RestaurantDTO.BasicInfo(restaurant.getId(), restaurant.getName(), restaurant.getDescription(), restaurant.getTotalRating()))
                    .collect(Collectors.toList());
        } else {
            List<Restaurant> restaurants = restaurantRepository.findByIdLessThanOrderByIdDesc(cursorId, PageRequest.of(0, pageSize));
            return restaurants.stream()
                    .map(restaurant -> new RestaurantDTO.BasicInfo(restaurant.getId(), restaurant.getName(), restaurant.getDescription(), restaurant.getTotalRating()))
                    .collect(Collectors.toList());
        }


    }
}