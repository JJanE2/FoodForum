package ourfood.example.foodforum.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ourfood.example.foodforum.dto.review.ReviewDTO;
import ourfood.example.foodforum.entity.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

    Optional<Member> findByName(String name);
    List<ReviewDTO.Mine> getMyReviewsOrderById(Long memberId, Long cursorId, Pageable pageable);

}
