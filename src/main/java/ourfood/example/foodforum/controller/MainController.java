package ourfood.example.foodforum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ourfood.example.foodforum.dto.restaurant.RestaurantDTO;

@Controller
@Transactional
public class MainController {

    @GetMapping("/")
    public String mainPage() {
        return "mainPage";
    }
}
