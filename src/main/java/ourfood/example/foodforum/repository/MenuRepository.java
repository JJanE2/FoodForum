package ourfood.example.foodforum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ourfood.example.foodforum.entity.Menu;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}
