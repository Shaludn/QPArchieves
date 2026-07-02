package com.qparchives.controller;

import com.qparchives.model.User;
import com.qparchives.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // ================= LOGIN PAGE =================

    @GetMapping("/login")
    public String loginPage(HttpSession session) {

        if (session.getAttribute("loggedInUser") != null) {
            return "redirect:/upload";
        }

        return "login";
    }

    // ================= REGISTER PAGE =================

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    // ================= REGISTER =================

    @PostMapping("/register")
    public String registerUser(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String confirmPassword,
            RedirectAttributes redirectAttributes
    ) {

        if (!password.equals(confirmPassword)) {

            redirectAttributes.addFlashAttribute(
                    "error",
                    "Passwords do not match!"
            );

            return "redirect:/register";
        }

        if (userService.usernameExists(username)) {

            redirectAttributes.addFlashAttribute(
                    "error",
                    "Username already exists!"
            );

            return "redirect:/register";
        }

        User user = new User();

        user.setUsername(username);
        user.setPassword(password);

        userService.registerUser(user);

        redirectAttributes.addFlashAttribute(
                "success",
                "Registration Successful! Please Login."
        );

        return "redirect:/login";
    }

    // ================= LOGIN =================

    @PostMapping("/login")
    public String loginUser(
            @RequestParam String username,
            @RequestParam String password,
            HttpSession session,
            RedirectAttributes redirectAttributes
    ) {

        User user = userService.loginUser(username, password);

        if (user == null) {

            redirectAttributes.addFlashAttribute(
                    "error",
                    "Invalid Username or Password!"
            );

            return "redirect:/login";
        }

        session.setAttribute("loggedInUser", user);

        return "redirect:/upload";
    }

    // ================= LOGOUT =================

    @GetMapping("/logout")
    public String logout(HttpSession session) {

        session.invalidate();

        return "redirect:/";
    }

}