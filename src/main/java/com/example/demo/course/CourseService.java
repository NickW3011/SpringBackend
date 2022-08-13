package com.example.demo.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> getCourses() {
        return courseRepository.findAll();
    }

    public void addCourse(Course course) {
        courseRepository.save(course);
    }

    public void deleteCourse(Long courseId) {
        if (!courseRepository.existsById(courseId)) {
            throw new IllegalStateException("course with id " + courseId + " does not exist");
        }
        courseRepository.deleteById(courseId);
    }

    @Transactional
    public void setCoursePassed(Long courseId, Course newCourse) {
        Course course = courseRepository.findById(courseId).orElseThrow(
                () -> new IllegalStateException("course with id " + courseId + " does not exist")
        );
        if (!Objects.equals(course.getStatus(), "passed")) {
            course.setStatus("passed");
            course.setGrade(newCourse.getGrade());
        }
    }

    @Transactional
    public void editCourse(Long courseId, Course newCourse) {
        Course oldCourse = courseRepository.findById(courseId).orElseThrow(
                () -> new IllegalStateException("course with id " + courseId + " does not exist")
        );
        oldCourse.setGrade(newCourse.getGrade());
        oldCourse.setStatus(newCourse.getStatus());
        oldCourse.setName(newCourse.getName());
        oldCourse.setLP(newCourse.getLP());
        oldCourse.setProfessor(newCourse.getProfessor());
    }
}
