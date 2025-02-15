package ourfood.example.foodforum.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import ourfood.example.foodforum.dto.member.MemberDTO;
import ourfood.example.foodforum.dto.member.QMemberDTO_Search;
import ourfood.example.foodforum.dto.review.QReviewDTO_Mine;
import ourfood.example.foodforum.dto.review.ReviewDTO;
import ourfood.example.foodforum.entity.Member;
import ourfood.example.foodforum.entity.QMember;
import ourfood.example.foodforum.entity.ROLE;
import ourfood.example.foodforum.util.QuerydslUtil;

import java.util.List;

import static ourfood.example.foodforum.entity.QMember.*;
import static ourfood.example.foodforum.entity.QRestaurant.restaurant;
import static ourfood.example.foodforum.entity.QReview.review;
import static ourfood.example.foodforum.util.QuerydslUtil.*;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ReviewDTO.Mine> getMyReviewsOrderById(Long memberId, Long cursorId, Pageable pageable) {

        return queryFactory
                .select(new QReviewDTO_Mine(
                        review.id, review.restaurant.name, review.restaurant.id, review.content, review.starRating, review.date
                ))
                .from(review)
                .where(review.member.id.eq(memberId)
                        .and(cursorId != null ? review.id.lt(cursorId) : null))
                .orderBy(review.id.desc())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public Page<MemberDTO.Search> getSearchPage(String memberName, Pageable pageable) {
        JPAQuery<MemberDTO.Search> query = queryFactory
                .select(new QMemberDTO_Search(
                        member.id, member.name, member.nickname, member.role, member.status
                ))
                .from(member)
                .where(memberNameContain(memberName));

        dynamicOrder(pageable, query, member);

        List<MemberDTO.Search> results = query.offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Member> countQuery = queryFactory
                .selectFrom(member)
                .where(memberNameContain(memberName)
                );

        return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchCount);
    }

    private Predicate memberNameContain(String memberName) {
        return memberName != null ? member.name.containsIgnoreCase(memberName) : null;
    }
}
