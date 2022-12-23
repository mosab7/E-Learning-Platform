package com.elearning.platform.services.core.impl;

import com.elearning.platform.auth.User;
import com.elearning.platform.auth.UserRepository;
import com.elearning.platform.model.Course;
import com.elearning.platform.model.Enrollment;
import com.elearning.platform.repositories.CourseRepository;
import com.elearning.platform.repositories.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class EnrollmentService {

    private EnrollmentRepository enrollmentRepository;
    private CourseRepository courseRepository;
    private UserRepository userRepository;

    @Autowired
    public EnrollmentService(EnrollmentRepository enrollmentRepository, CourseRepository courseRepository, UserRepository userRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    public void createEnrollment(Long courseId, String username) throws Exception {
        Course course = courseRepository.findById(courseId).get();
        User user = userRepository.findByUsername(username);

        if (null != enrollmentRepository.findByCourseAndUserName(course, user)) {
            throw new Exception("You already enrolled in this course");
        }
        LocalDate date = LocalDate.now();
        Enrollment enrollment = new Enrollment(date, user, course);
        enrollmentRepository.save(enrollment);
    }
}
