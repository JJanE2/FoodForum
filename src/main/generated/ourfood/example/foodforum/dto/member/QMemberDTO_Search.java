package ourfood.example.foodforum.dto.member;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * ourfood.example.foodforum.dto.member.QMemberDTO_Search is a Querydsl Projection type for Search
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QMemberDTO_Search extends ConstructorExpression<MemberDTO.Search> {

    private static final long serialVersionUID = -223971805L;

    public QMemberDTO_Search(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<String> memberName, com.querydsl.core.types.Expression<String> nickname, com.querydsl.core.types.Expression<ourfood.example.foodforum.entity.ROLE> role, com.querydsl.core.types.Expression<ourfood.example.foodforum.entity.MemberStatus> status) {
        super(MemberDTO.Search.class, new Class<?>[]{long.class, String.class, String.class, ourfood.example.foodforum.entity.ROLE.class, ourfood.example.foodforum.entity.MemberStatus.class}, id, memberName, nickname, role, status);
    }

}

