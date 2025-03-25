package ourfood.example.foodforum.init;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ourfood.example.foodforum.entity.*;
import ourfood.example.foodforum.repository.MemberRepository;
import ourfood.example.foodforum.repository.MenuRepository;
import ourfood.example.foodforum.repository.RestaurantRepository;
import ourfood.example.foodforum.repository.ReviewRepository;

@Component
public class DataInitializer {

    private final MemberRepository memberRepository;
    private final RestaurantRepository restaurantRepository;
    private final ReviewRepository reviewRepository;
    private final MenuRepository menuRepository;

    private final PasswordEncoder passwordEncoder;

    private final String memberPassword;
    private final String adminPassword;

    public DataInitializer(MemberRepository memberRepository, RestaurantRepository restaurantRepository, ReviewRepository reviewRepository, MenuRepository menuRepository, PasswordEncoder passwordEncoder,
                           @Value("${member.password}") String memberPassword, @Value("${admin.password}") String adminPassword) {
        this.memberRepository = memberRepository;
        this.restaurantRepository = restaurantRepository;
        this.reviewRepository = reviewRepository;
        this.menuRepository = menuRepository;
        this.passwordEncoder = passwordEncoder;
        this.memberPassword = memberPassword;
        this.adminPassword = adminPassword;
    }

    @Transactional
    @EventListener(ApplicationReadyEvent.class)
    public void init() {

        // db에 있는 rawPassword 암호화
        if (memberRepository.existsById(1L)) {
            Member member = memberRepository.findById(1L).get();
            if (isRawPassword(member.getPassword())) {
                String encodedPassword = passwordEncoder.encode(memberPassword);
                member.updateMember(member.getNickname(), encodedPassword);
            }
        }
        if (memberRepository.existsById(2L)) {
            Member admin = memberRepository.findById(2L).get();
            if (isRawPassword(admin.getPassword())) {
                String encodedAdminPassword = passwordEncoder.encode(adminPassword);
                admin.updateMember(admin.getNickname(), encodedAdminPassword);
            }
        }

        Restaurant restaurant = restaurantRepository.findById(1L).get();
        Restaurant restaurant2 = restaurantRepository.findById(3L).get();
        Restaurant restaurant3 = restaurantRepository.findById(5L).get();

        restaurant.setTotalRating(restaurant.calculateTotalRating());
        restaurant2.setTotalRating(restaurant2.calculateTotalRating());
        restaurant3.setTotalRating(restaurant3.calculateTotalRating());
    }

    private boolean isRawPassword(String password) {
        return password != null && !password.startsWith("{bcrypt}");
    }
}
