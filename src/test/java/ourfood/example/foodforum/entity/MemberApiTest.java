package ourfood.example.foodforum.entity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberApiTest {

    @Autowired
    EntityManager em;

    @Test
    public void querydslTest() throws Exception {
        Member member = new Member();
        em.persist(member);

        JPAQueryFactory query = new JPAQueryFactory(em);
        QMember qMember = QMember.member;

        Member result = query.selectFrom(qMember)
                .where(qMember.name.eq("me"))
                .fetchOne();

        assertNotNull(result);
    }

    @Test
    public void conditionTest() throws Exception {
        JPAQueryFactory query = new JPAQueryFactory(em);
        QMember member = QMember.member;
        QRestaurant restaurant = QRestaurant.restaurant;

        String restaurantName = "PaPa";
        Double minimumRating = 0.0;

        List<Restaurant> result = query.selectFrom(restaurant)
                .where(restaurant.name.containsIgnoreCase(restaurantName),
                        restaurant.totalRating.goe(minimumRating))
                .fetch();

        for (Restaurant rest : result) {
            System.out.println(rest.getName());
            System.out.println(rest.getDescription());
            System.out.println(rest.getTotalRating());
        }
    }
}