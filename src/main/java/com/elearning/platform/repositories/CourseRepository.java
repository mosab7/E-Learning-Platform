package com.elearning.platform.repositories;

import com.elearning.platform.model.Course;
import com.elearning.platform.model.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    Course findByCourseName(String name);
    List<Course> findAllByTutor(Tutor tutor);
}
