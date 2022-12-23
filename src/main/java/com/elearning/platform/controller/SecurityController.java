package com.elearning.platform.controller;

import com.elearning.platform.auth.User;
import com.elearning.platform.auth.UserRepository;
import com.elearning.platform.model.Enrollment;
import com.elearning.platform.repositories.EnrollmentRepository;
import com.elearning.platform.services.core.impl.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@PreAuthorize("hasRole('ROLE_USER')")
@AllArgsConstructor
public class SecurityController {

    private final UserRepository userRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final UserService userService;

    @GetMapping("/profile")
    public String getUserProfile(Authentication authentication, Model model) {
        try {
            String currentUsername = authentication.getName();
            User user = userRepository.findByUsername(currentUsername);
            List<Enrollment> enrollments = enrollmentRepository.findAllByUserName(user);
            int numCourses = enrollments.size();
            model.addAttribute("user", user);
            model.addAttribute("enrollments", enrollments);
            model.addAttribute("numCourses", numCourses);
            return "user/profile";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", e);
            return "error";
        }
    }

    @GetMapping("/user/edit/{userID}")
    public String getForEdit(@PathVariable Long userID, Authentication authentication, Model model) {

        try {
            String currentusername = authentication.getName();
            User current = userRepository.findById(userID).get();
            if (currentusername.equals(current.getUsername())) {
            model.addAttribute(current);
            return "user/user-edit";
            } else {
                throw new Exception("Error de autenticacion");
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", e);
            return "error";
        }
    }

    @PostMapping("/user/edit/{userID}")
    public String updateUser(@PathVariable Long userID, Authentication authentication, User user, Model model) {

        try {
            User current = userRepository.findById(userID).get();
            String currentusername = authentication.getName();
            if (currentusername.equals(current.getUsername())) {
                current.setName(user.getName());
                current.setSurname(user.getSurname());
                current.setEmail(user.getEmail());
                current.setImgUrl(user.getImgUrl());
                userService.update(current);

                return "redirect:/profile";
            } else {
                throw new Exception("Error de autenticacion");
            }

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", e);
            return "error";
        }
    }

    @PostMapping("/user/patch/{id_user}")
    public String patchUser(@PathVariable Long id_user, Authentication authentication, User user, Model model) {

        try {
            User current = userRepository.findById(id_user).get();
            String currentusername = authentication.getName();
            if (currentusername.equals(current.getUsername())) {
                current.setDetail(user.getDetail());
                userService.patch(current);

                return "redirect:/profile";
            } else {
                throw new Exception("Error de autenticacion");
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", e);
            return "error";
        }
    }
}
