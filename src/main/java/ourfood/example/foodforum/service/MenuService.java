package ourfood.example.foodforum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ourfood.example.foodforum.entity.Menu;
import ourfood.example.foodforum.repository.MenuRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class MenuService {
    private final MenuRepository menuRepository;

    public void updateMenu(Long menuId, String updateName, String updatePrice) {
        Menu menu = menuRepository.findById(menuId).get();
        menu.updateMenu(updateName, updatePrice);
    }
}
