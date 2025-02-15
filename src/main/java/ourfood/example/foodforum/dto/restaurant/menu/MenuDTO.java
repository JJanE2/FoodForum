package ourfood.example.foodforum.dto.restaurant.menu;

import lombok.AllArgsConstructor;
import lombok.Data;

public class MenuDTO {

    @Data
    @AllArgsConstructor
    public static class Create{
        private String name;
        private String price;
    }

    @Data
    public static class Update{
        private String name;
        private String price;
        private Long id;
    }
}
