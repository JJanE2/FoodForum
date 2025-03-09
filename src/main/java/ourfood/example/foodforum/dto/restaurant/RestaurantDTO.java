package ourfood.example.foodforum.dto.restaurant;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import ourfood.example.foodforum.dto.restaurant.menu.MenuDTO;
import ourfood.example.foodforum.entity.Member;
import ourfood.example.foodforum.entity.Menu;

import java.util.List;

public class RestaurantDTO {

    @Data
    public static class Create {
        private String restaurantName;
        private String description;
        private List<Menu> menus;
        private String address;
        private Double latitude;
        private Double longitude;
    }

    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class BasicInfo {
        private Long id;
        private String name;
        private String description;
        private Double totalRating;

        public BasicInfo(Long id, String name, String description, Double totalRating) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.totalRating = totalRating;
        }
    }

    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class DetailInfo {
        private Long id;
        private String name;
        private String description;
        private Double starRating;
        private List<MenuDTO.Create> menus;
        private String address;
        private Double latitude;
        private Double longitude;

        public DetailInfo(Long id, String name, String description, Double starRating, List<MenuDTO.Create> menus,
                          String address, Double latitude, Double longitude) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.starRating = starRating;
            this.menus = menus;
            this.address =address;
            this.latitude = latitude;
            this.longitude = longitude;
        }
    }

    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Update {
        private Long id;
        private String name;
        private String description;
        private List<Menu> menus;

        public Update(Long id, String name, String description, List<Menu> menus) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.menus = menus;
        }
    }

    @Data
    public static class UpdateRequest {
        private String name;
        private String description;
        private List<MenuDTO.Update> menus;
    }

    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Mine {
        private Long id;
        private String name;
        private Double totalRating;

        @QueryProjection
        public Mine(Long id, String name, Double totalRating) {
            this.id = id;
            this.name = name;
            this.totalRating = totalRating;
        }
    }

    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Search {
        private Long id;
        private String name;
        private String description;
        private Double totalRating;

        @QueryProjection
        public Search(Long id, String name, String description, Double totalRating) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.totalRating = totalRating;
        }
    }
}
