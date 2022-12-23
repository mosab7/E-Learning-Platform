package com.elearning.platform.controller;

import com.elearning.platform.auth.User;
import com.elearning.platform.auth.UserRepository;
import com.elearning.platform.model.Course;
import com.elearning.platform.repositories.CourseRepository;
import com.elearning.platform.services.core.impl.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/enrollment")
@PreAuthorize("hasRole('ROLE_USER')")
public class EnrollmentController {

    private EnrollmentService enrollmentService;
    private UserRepository userRepository;
    private CourseRepository courseRepository;

    @Autowired
    public EnrollmentController(EnrollmentService enrollmentService, UserRepository userRepository, CourseRepository courseRepository) {
        this.enrollmentService = enrollmentService;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    @GetMapping("/save/{courseId}")
    public String saveEnrollment(@PathVariable Long courseId, Authentication authentication, Model model) {
        try {
            String username = authentication.getName();
            enrollmentService.createEnrollment(courseId, username);
            User user = userRepository.findByUsername(username);
            Course course = courseRepository.findById(courseId).get();
            model.addAttribute("course", course);
            model.addAttribute("user", user);
            return "enrollment-success";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", e);
            return "error";
        }
    }
}
