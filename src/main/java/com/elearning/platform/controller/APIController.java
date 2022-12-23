package com.elearning.platform.controller;

import com.elearning.platform.model.Course;
import com.elearning.platform.model.Tutor;
import com.elearning.platform.services.core.impl.CourseService;
import com.elearning.platform.services.core.impl.TutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class APIController {

    private TutorService tutorService;
    private CourseService courseService;

    @Autowired
    public APIController(TutorService tutorService, CourseService courseService) {
        super();
        this.tutorService = tutorService;
        this.courseService = courseService;
    }

    @GetMapping("/tutors")
    public List<Tutor> getAllTutors() { return this.tutorService.getAll(); }

    @GetMapping("/courses")
    public List<Course> getAllCourses() {
        return this.courseService.getAll();
    }
}
