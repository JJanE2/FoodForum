package ourfood.example.foodforum.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ourfood.example.foodforum.dto.member.MemberDTO;
import ourfood.example.foodforum.dto.review.ReviewDTO;
import ourfood.example.foodforum.entity.Member;
import ourfood.example.foodforum.service.MemberService;

import java.util.List;

@Tag(name = "Member Controller", description = "Member 가입 및 정보수정")
@RestController
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@RequestMapping("/api")
public class MemberApi {
    private final MemberService memberService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Operation(summary = "Member 가입")
    @PostMapping("/member/new")
    public ResponseEntity<String> createMember(@RequestBody MemberDTO.Create memberDTO) {
        if (memberService.isDuplicatedName(memberDTO.getMemberName())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 사용중인 아이디 입니다.");
        }

        Long memberId = memberService.join(memberDTO);
        if (memberId != null) {
            return ResponseEntity.ok("회원가입이 완료되었습니다. 로그인을 해주세요.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("올바른 회원 정보를 입력해주세요.");
        }
    }

    @PostMapping("/member/update")
    public ResponseEntity<String> memberUpdate(@RequestBody MemberDTO.Update memberUpdateDTO) {
        try {
            String encodedPassword = passwordEncoder.encode(memberUpdateDTO.getPassword());
            memberService.updateMember(memberUpdateDTO.getMemberId(), memberUpdateDTO.getNickname(), encodedPassword);

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(memberUpdateDTO.getMemberName(), memberUpdateDTO.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return ResponseEntity.ok("기본정보 수정이 완료되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("기본정보 수정 중 오류가 발생했습니다.");
        }
    }

    @PostMapping("/member/delete")
    public ResponseEntity<String> memberDelete(@AuthenticationPrincipal UserDetails userDetails,
                                               @RequestBody MemberDTO.Delete memberDeleteDTO,
                                               HttpServletRequest request) {
        Member findMember = memberService.findByName(userDetails.getUsername());
        Boolean isPasswordCorrect = memberService.passwordCheck(findMember, memberDeleteDTO.getPassword());

        try {
            if (isPasswordCorrect) {
                memberService.deleteMember(findMember);

                SecurityContextHolder.clearContext();
                HttpSession session = request.getSession();
                if (session != null) { session.invalidate(); }
                return ResponseEntity.ok("정상적으로 회원 탈퇴 되었습니다.");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("비밀번호가 일치하지 않습니다.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원 탈퇴 중 오류가 발생했습니다.");
        }
    }

    @GetMapping("/member/reviews")
    public List<ReviewDTO.Mine> getMemberReviews(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(name = "cursorId", required = false) Long cursorId,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
        Member member = memberService.findByName(userDetails.getUsername());
        Long memberId = member.getId();
        Pageable pageable = PageRequest.of(0, pageSize);

        List<ReviewDTO.Mine> reviews = memberService.getMyReviewsByCursor(memberId, cursorId, pageable);
        return reviews;
    }
}
