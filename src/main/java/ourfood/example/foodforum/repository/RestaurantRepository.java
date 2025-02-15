package ourfood.example.foodforum.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ourfood.example.foodforum.entity.Restaurant;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long>, RestaurantRepositoryCustom {

    Restaurant findByName(String name);

    List<Restaurant> findByMemberIdOrderByIdDesc(Long memberId);

    List<Restaurant> findByIdLessThanOrderByIdDesc(Long cursorId, Pageable pageable);
}
