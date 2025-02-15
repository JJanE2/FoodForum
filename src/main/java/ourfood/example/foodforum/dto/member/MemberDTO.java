package ourfood.example.foodforum.dto.member;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import ourfood.example.foodforum.entity.MemberStatus;
import ourfood.example.foodforum.entity.ROLE;

public class MemberDTO {

    @Data
    public static class Create {
        private String memberName;
        private String nickname;
        private String password;
        private String role;
    }

    @Data
    public static class Update {
        private Long memberId;
        private String memberName;
        private String nickname;
        private String password;
    }

    @Data
    public static class Delete {
        private String password;
    }

    @Data
    public static class Search {
        private Long id;
        private String memberName;
        private String nickname;
        private String role;
        private String status;

        @QueryProjection
        public Search(Long id, String memberName, String nickname, ROLE role, MemberStatus status) {
            this.id = id;
            this.memberName = memberName;
            this.nickname = nickname;
            this.role = String.valueOf(role);
            this.status = String.valueOf(status);
        }
    }
}
