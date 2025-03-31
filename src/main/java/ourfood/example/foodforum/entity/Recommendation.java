package ourfood.example.foodforum.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Recommendation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recommendation_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public Recommendation(Review review, Member member) {
        this.review = review;
        this.member = member;
    }

    // 연관관계 편의 메서드
    public void addRecommendation(Review review, Member member) {
        review.getRecommendations().add(this);
        member.getRecommendations().add(this);
    }

    public void removeRecommendation(Review review, Member member) {
        review.getRecommendations().remove(this);
        member.getRecommendations().remove(this);
    }

}
