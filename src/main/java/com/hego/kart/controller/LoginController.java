package com.hego.kart.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hego.kart.global.GlobalData;
import com.hego.kart.model.Role;
import com.hego.kart.model.User;
import com.hego.kart.repository.RoleRepository;
import com.hego.kart.repository.UserRepository;

@Controller
public class LoginController {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @GetMapping("/login")
    public String login(Model model) {
        GlobalData.cart.clear();
        return "login";
    }

    @GetMapping("/forgotpassword")
    public String getForgotPassword() {
        return "forgotpassword";
    }

    @PostMapping("/forgotpassword")
    public String postForgotPassword(@RequestParam("email") String email, 
    RedirectAttributes attributes) {
        Optional<User> users = userRepository.findUserByEmail(email);
        if(users.isEmpty()) {
            attributes.addAttribute("error", "Invalid Email");
            return "redirect:/forgotpassword";
        }
        else {
            String newPassword = RandomStringUtils.randomAlphanumeric(8);
            users.get().setPassword(bCryptPasswordEncoder.encode(newPassword));
            userRepository.save(users.get());
            logger.info("A new password has been sent to the email: {} and the new password is: {}", email, newPassword);
            attributes.addAttribute("success", "Password has been sent to your email. go back to Login to Procceed with new password");
            return "redirect:/forgotpassword";
        }
    }

    @GetMapping("/register")
    public String getRegister() {
        return "register";
    }

    @PostMapping("/register")
    public String postRegister(@ModelAttribute("user") User user, 
    HttpServletRequest request, RedirectAttributes attributes) throws ServletException {
        String email = user.getEmail();
        Optional<User> existingUser = userRepository.findUserByEmail(email);
        if (existingUser.isPresent()) {
            attributes.addAttribute("firstname", user.getFirstName());
            attributes.addAttribute("lastname", user.getLastName());
            attributes.addAttribute("error", "user already exists with that email address");
            return "redirect:/register";
        }
        String password = user.getPassword();
        user.setPassword(bCryptPasswordEncoder.encode(password));
        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.findById(2).get());
        user.setRoles(roles);
        userRepository.save(user);
        request.login(user.getEmail(), password);
        return "redirect:/";
    }
}
