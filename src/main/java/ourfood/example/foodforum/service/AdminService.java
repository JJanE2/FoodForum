package ourfood.example.foodforum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ourfood.example.foodforum.entity.Member;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {

    private final MemberService memberService;

    public void suspendMember(Long memberId) {
        Member member = memberService.findById(memberId);
        member.setSuspend();
    }

    public void activateMember(Long memberId) {
        Member member = memberService.findById(memberId);
        member.setActive();
    }
}
