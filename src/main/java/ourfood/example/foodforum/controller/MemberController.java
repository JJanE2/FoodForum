package ourfood.example.foodforum.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ourfood.example.foodforum.entity.Member;
import ourfood.example.foodforum.exception.CustomAccessDeniedException;
import ourfood.example.foodforum.service.MemberService;

@Controller
@RequiredArgsConstructor
@Transactional
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/login")
    public String loginForm(HttpSession session, Model model) {
        Object loginError = session.getAttribute("loginError");
        if (loginError != null) {
            model.addAttribute("loginError", loginError);
            session.removeAttribute("loginError");
        }

        return "member/loginForm";
    }

    @GetMapping("/members/new")
    public String memberForm() {
        return "member/memberForm";
    }

    @GetMapping("/member/settings")
    public String myPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {

        Member member = memberService.findByName(userDetails.getUsername());

        model.addAttribute("nickname", member.getNickname());
        String role = member.getRole().name();
        model.addAttribute("role", role);
        model.addAttribute("id", member.getId());
        return "member/memberDetails";
    }

    @GetMapping("/member/settings/update")
    public String memberUpdateForm(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        Member findMember = memberService.findByName(userDetails.getUsername());
        String memberName = userDetails.getUsername();
        model.addAttribute("memberId", findMember.getId());
        model.addAttribute("memberName", memberName);
        model.addAttribute("nickname", findMember.getNickname());
        return "member/memberUpdateForm";
    }
}
