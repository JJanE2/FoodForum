package ourfood.example.foodforum.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Restaurant {

    @Id @GeneratedValue
    @Column(name = "restaurant_id")
    private Long id;

    @Column(name = "restaurant_name")
    private String name;

    private String description;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<Menu> menus = new ArrayList<>();

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

    @Column(name = "total_rating")
    private Double totalRating = 0.0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


    public Restaurant(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Restaurant(Member member, String name, String description) {
        this.member = member;
        this.name = name;
        this.description = description;
    }

    public Restaurant(Member member, String name, String description, List<Menu> menus) {
        this.member = member;
        this.name = name;
        this.description = description;
        this.menus = menus;
    }

    public void addMenu(Menu menu) {
        menus.add(menu);
        menu.setRestaurant(this);
    }

    public double calculateTotalRating() {
        if (this.reviews == null || (this.reviews).isEmpty()) {
            this.totalRating = 0.0;
            return 0.0;
        }

        double totalSum = 0.0;
        int size = this.reviews.size();

        for (Review review : this.reviews) {
            totalSum += review.getStarRating();
        }

        double totalRating = totalSum / size;

        return Math.round(totalRating * 10.0) / 10.0;
    }

    public void setTotalRating(Double totalRating) {
        this.totalRating = totalRating;
    }

    public Long updateRestaurant(String newName, String newDescription) {
        this.name = newName;
        this.description = newDescription;
        return this.id;
    }
}
