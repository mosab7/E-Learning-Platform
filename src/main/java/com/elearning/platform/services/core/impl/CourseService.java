package com.elearning.platform.services.core.impl;

import com.elearning.platform.dto.CourseDto;
import com.elearning.platform.model.Course;
import com.elearning.platform.model.Tutor;
import com.elearning.platform.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService{

    private CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public void create(CourseDto courseDto) throws Exception{
        if (null != courseRepository.findByCourseName(courseDto.getCourseName())) {
            throw new Exception("There's already a course with the name " + courseDto.getCourseName());
        }
        String courseName = courseDto.getCourseName();
        String courseDescription = courseDto.getCourseDescription();
        String courseDetail = courseDto.getDetail();
        String courseDifficulty = courseDto.getDifficulty();
        String courseUrl = courseDto.getUrl();
        String imgUrl = courseDto.getImgUrl();
        Tutor tutor = courseDto.getTutor();
        Course course = new Course(courseName, courseDescription, courseDetail, courseDifficulty, courseUrl, imgUrl, tutor);

        courseRepository.save(course);
    }

    public void update(Course course, Long courseId) {
        Course currentCourse = courseRepository.findById(courseId).get();

            currentCourse.setCourseName(course.getCourseName());
            currentCourse.setCourseDescription(course.getCourseDescription());
            currentCourse.setCourseDetail(course.getCourseDetail());
            currentCourse.setCourseDifficulty(course.getCourseDifficulty());
            currentCourse.setCourseUrl(course.getCourseUrl());
            currentCourse.setImgUrl(course.getImgUrl());
            currentCourse.setTutor(course.getTutor());

            courseRepository.save(currentCourse);

    }

    public void delete(Course course) { courseRepository.delete(course); }


    public List<Course> getAll() {
        return courseRepository.findAll();
    }

}
