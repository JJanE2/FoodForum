package ourfood.example.foodforum.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import ourfood.example.foodforum.exception.SuspendedMemberException;

import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException {

        String errorMessage;

        if (exception instanceof BadCredentialsException) { errorMessage = "아이디 또는 비밀번호를 확인해주세요."; }
        else if (exception instanceof InternalAuthenticationServiceException) { errorMessage = "계정이 제한되었습니다. <br> 관리자에게 문의하세요."; }
        else { errorMessage = "알 수 없는 이유로 로그인에 실패하였습니다. <br> 관리자에게 문의하세요."; }

        request.getSession().setAttribute("loginError", errorMessage);
        response.sendRedirect("/login");
    }
}
