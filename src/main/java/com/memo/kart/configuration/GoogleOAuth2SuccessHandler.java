package com.memo.kart.configuration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.memo.kart.model.Role;
import com.memo.kart.model.User;
import com.memo.kart.repository.RoleRepository;
import com.memo.kart.repository.UserRepository;

@Component
public class GoogleOAuth2SuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        String email = token.getPrincipal().getAttribute("email").toString();
        //set  email in cookie
        Cookie cookie = new Cookie("EMAIL",email);
        cookie.setPath("/");
        httpServletResponse.addCookie(cookie);
        if(userRepository.findUserByEmail(email).isPresent()){
            
        }else{
            User user = new User();
            user.setFirstName(token.getPrincipal().getAttribute("given_name").toString());
            user.setLastName(token.getPrincipal().getAttribute("family_name").toString());
            user.setEmail(email);
            List<Role> roles = new ArrayList<>();
            roles.add(roleRepository.findById(2).get());
            user.setRoles(roles);
            userRepository.save(user);
        }
        redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, "/");
    }

}
