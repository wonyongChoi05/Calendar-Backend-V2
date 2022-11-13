package com.calendar.api.service;

import com.calendar.api.dto.LoginReq;
import com.calendar.api.dto.SignUpReq;
import com.calendar.core.domain.entity.User;
import com.calendar.core.dto.UserCreateReq;
import com.calendar.core.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final static String LOGIN_SESSION_KEY = "USER_ID";
    private final UserService userService;

    @Transactional
    public void signUp(SignUpReq signupReq, HttpSession session) {
        final User user = userService.create(new UserCreateReq(
                signupReq.getName(),
                signupReq.getEmail(),
                signupReq.getPassword(),
                signupReq.getBirthday()
        ));

        // KEY:VALUE 방식으로 Session에 유저 정보를 담음
        session.setAttribute(LOGIN_SESSION_KEY, user.getId());
    }

    @Transactional
    public void login(LoginReq loginReq, HttpSession session) {
        // session 에 값이 있는지 판단
        final Long userId = (Long) session.getAttribute(LOGIN_SESSION_KEY);
        // 만약 세션에 값이 있다면 로그인이 된 상태이므로 그냥 리턴함
        if (userId != null) return;

        // 세션에 값이 없을경우 로그인 진행
        final Optional<User> user = userService.findPwMatchUser(loginReq.getEmail(), loginReq.getPassword());

        // user 가 존재하면 세션에 값 저장 else Exception
        if (user.isPresent()) {
            session.setAttribute(LOGIN_SESSION_KEY, user.get().getId());
        } else {
            throw new RuntimeException("password or email not match");
        }

    }

    public void logout(HttpSession session) {
        session.removeAttribute(LOGIN_SESSION_KEY);
    }
}
