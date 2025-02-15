package ourfood.example.foodforum.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ourfood.example.foodforum.dto.member.MemberDTO;
import ourfood.example.foodforum.dto.review.ReviewDTO;
import ourfood.example.foodforum.entity.ROLE;

import java.util.List;

public interface MemberRepositoryCustom {
    List<ReviewDTO.Mine> getMyReviewsOrderById(Long memberId, Long cursorId, Pageable pageable);

    Page<MemberDTO.Search> getSearchPage(String memberName, Pageable pageable);
}
