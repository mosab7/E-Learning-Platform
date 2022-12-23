package com.elearning.platform.controller;

import com.elearning.platform.auth.User;
import com.elearning.platform.auth.UserRepository;
import com.elearning.platform.dto.CourseDto;
import com.elearning.platform.model.Course;
import com.elearning.platform.model.Tutor;
import com.elearning.platform.repositories.CourseRepository;
import com.elearning.platform.repositories.EnrollmentRepository;
import com.elearning.platform.repositories.TutorRepository;
import com.elearning.platform.services.core.impl.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/courses")
public class CourseController {

    private CourseService courseService;
    private CourseRepository courseRepository;
    private EnrollmentRepository enrollmentRepository;
    private UserRepository userRepository;
    private TutorRepository tutorRepository;

    @Autowired
    public CourseController(CourseService courseService, CourseRepository courseRepository,
                            EnrollmentRepository enrollmentRepository, UserRepository userRepository, TutorRepository tutorRepository) {
        super();
        this.courseService = courseService;
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.userRepository = userRepository;
        this.tutorRepository = tutorRepository;
    }

    @GetMapping("/add/{tutorId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addCourse(@PathVariable Long tutorId, Model model) {
        try {
            Tutor current = tutorRepository.findById(tutorId).get();
            model.addAttribute("course", new CourseDto());
            model.addAttribute("tutor", current);
            return "courses/course-add";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", e);
            return "error";
        }
    }

    @PostMapping("/add/{tutorId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String saveCourse(@PathVariable Long tutorId, CourseDto course, Model model) {
        try {
            Tutor current = tutorRepository.findById(tutorId).get();
            course.setTutor(current);
            courseService.create(course);
            return "redirect:/courses";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", e);
            return "error";
        }

    }

    @GetMapping("/edit/{courseId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getCourseForUpdate(@PathVariable Long courseId, Model model) {
        try {
            Course courseActual = courseRepository.findById(courseId).get();
            model.addAttribute("course", courseActual);
            return "courses/course-edit";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", e);
            return "error";
        }
    }

    @PostMapping("/edit/{tutorId}/{courseId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String updateCourse(@PathVariable Long tutorId, @PathVariable Long courseId, Course course, Model model, RedirectAttributes attributes) {

        try {
            Tutor currentTutor = tutorRepository.findById(tutorId).get();
            course.setTutor(currentTutor);

            courseService.update(course, courseId);
            attributes.addAttribute("courseId", courseId);

            return "redirect:/courses/{courseId}";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", e);
            return "error";
        }
    }

    @GetMapping
    public String getCoursesList(Model model) {
        List<Course> courses = courseService.getAll();
        model.addAttribute("courses", courses);
        return "courses/courses";
    }

    @GetMapping("/delete/{courseId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteCourse(@PathVariable Long courseId, Model model) {
        try {
            Course courseActual = courseRepository.findById(courseId).get();
            courseService.delete(courseActual);

            return "redirect:/courses";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", e);
            return "error";
        }
    }

    @GetMapping("/{courseId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String getCourseDetail(@PathVariable Long courseId, Authentication authentication, Model model) {
        String username = authentication.getName();
        Boolean enrollment = false;
        try {
            Course course = courseRepository.findById(courseId).get();
            User user = userRepository.findByUsername(username);
            if (null != enrollmentRepository.findByCourseAndUserName(course, user)) {
                enrollment = true;
            }
            model.addAttribute("course", course);
            model.addAttribute("enrollment", enrollment);
            return "courses/course-detail";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", e);
            return "error";
        }
    }
}
