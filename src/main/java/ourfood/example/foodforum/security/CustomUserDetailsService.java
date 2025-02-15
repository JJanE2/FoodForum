package ourfood.example.foodforum.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ourfood.example.foodforum.entity.Member;
import ourfood.example.foodforum.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("Member not found with name : " + username));

        if (member.isSuspended()) {
            throw new DisabledException("Member was Suspended with name : " + username);
        }

        return new User(
                member.getName(),
                member.getPassword(),
                AuthorityUtils.createAuthorityList("ROLE_" + member.getRole().name())
        );
    }
}
