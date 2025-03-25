package ourfood.example.foodforum.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "member_name")
    private String name;

    private String nickname;

    @Column(name = "member_password")
    private String password;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();


    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Restaurant> restaurants = new ArrayList<>();


    @Enumerated(EnumType.STRING)
    private ROLE role;

    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    public Member(String name, String nickname, String password, ROLE role) {
        this.name = name;
        this.nickname = nickname;
        this.password = password;
        this.role = role;
        this.status = MemberStatus.ACTIVE;
    }

    public boolean isSuspended() {
        return this.status == MemberStatus.SUSPENDED;
    }

    public void setSuspend() {
        this.status = MemberStatus.SUSPENDED;
    }

    public void setActive() {
        this.status = MemberStatus.ACTIVE;
    }

    public void updateMember(String updateNickname, String updatePwd) {
        this.nickname = updateNickname;
        this.password = updatePwd;
    }

    public void addRestaurant(Restaurant restaurant) {
        this.restaurants.add(restaurant);
    }
}