package ourfood.example.foodforum.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ourfood.example.foodforum.dto.member.MemberDTO;
import ourfood.example.foodforum.dto.review.ReviewDTO;
import ourfood.example.foodforum.entity.Member;
import ourfood.example.foodforum.entity.ROLE;
import ourfood.example.foodforum.entity.Review;
import ourfood.example.foodforum.exception.CustomAccessDeniedException;
import ourfood.example.foodforum.exception.ResourceNotFoundException;
import ourfood.example.foodforum.repository.MemberRepository;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member findById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id : " + id));
    }

    public Member findByName(String name) {
        return memberRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with name : " + name));
    }

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public Boolean passwordCheck(Member member, String rawPassword) {
        String memberPassword = member.getPassword();

        return passwordEncoder.matches(rawPassword, memberPassword);
    }

    public List<Review> getReviews(String name) {
        Member findMember = findByName(name);
        return findMember.getReviews();
    }

    public List<ReviewDTO.Mine> getMyReviewsByCursor(Long memberId, Long cursorId, Pageable pageable) {
        return memberRepository.getMyReviewsOrderById(memberId, cursorId, pageable);
    }

    public Long join(MemberDTO.Create memberDTO) {
        String encodedPassword = passwordEncoder.encode(memberDTO.getPassword());
        ROLE role = ROLE.valueOf(memberDTO.getRole());

        Member member = new Member(memberDTO.getMemberName(), memberDTO.getNickname(), encodedPassword, role);
        Member savedMember = memberRepository.save(member);
        return savedMember.getId();
    }

    public boolean isDuplicatedName(String memberName) {
        Member findMember = memberRepository.findByName(memberName).orElse(null);
        if (findMember == null) {
            return false;
        } else return true;
    }

    public void updateMember(Long memberId, String newNickname, String newPassword) {
        Member findMember = memberRepository.findById(memberId).get();
        findMember.updateMember(newNickname, newPassword);
    }

    public void deleteMember(Member member) {
        memberRepository.delete(member);
    }

    public void validMemberRole(Member member) {
        ROLE role = member.getRole();
        if (!ROLE.OWNER.equals(role)) {
            throw new CustomAccessDeniedException("Not allowed to access restaurant create because MemberId: " + member.getId() + " is a CUSTOMER");
        }
    }

    public Page<MemberDTO.Search> searchPage(String memberName, Pageable pageable) {
        return memberRepository.getSearchPage(memberName, pageable);
    }
}