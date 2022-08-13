package com.example.demo.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping(path = "/courses")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public String getCourses(Model model) {
        List<Course> courses = courseService.getCourses();
        courses = sortCoursesByName(courses);
        courses = sortCoursesByStatus(courses);
        int totalLp = calcTotalLP(courses);
        int passedLp = calcPassedLP(courses);
        int activeLp = totalLp - passedLp;
        double averageGrade = calcAverageGrade(courses);
        double grade = 0.0;
        boolean activeChecked = true;
        model.addAttribute("courses", courses);
        model.addAttribute("newCourse", new Course());
        model.addAttribute("editCourse", new Course());
        model.addAttribute("editGrade", grade);
        model.addAttribute("averageGrade", averageGrade);
        model.addAttribute("totalLp", totalLp);
        model.addAttribute("passedLp", passedLp);
        model.addAttribute("activeLp", activeLp);
        model.addAttribute("activeChecked", activeChecked);
        return "courses";
    }

    @PostMapping(path = "/add")
    public String addCourse(@ModelAttribute("newCourse") Course course) {
        courseService.addCourse(course);
        return "redirect:/courses";
    }

    @GetMapping(path = "/delete/{courseId}")
    public String deleteCourse(@PathVariable("courseId") Long id) {
        courseService.deleteCourse(id);
        return "redirect:/courses";
    }

    @PostMapping(path = "/edit/{courseId}")
    public String editCourse(@PathVariable("courseId") Long id, @ModelAttribute("editCourse") Course course) {
        courseService.editCourse(id, course);
        return "redirect:/courses";
    }

    @PostMapping(path = "/edit/setgrade/{courseId}")
    public String setGrade(@PathVariable("courseId") Long id, @ModelAttribute("editCourse") Course course) {
        courseService.setCoursePassed(id, course);
        return "redirect:/courses";
    }

    //Functions

    public List<Course> sortCoursesByName(List<Course> courses) {
        Course temp;
        for (int i = 0; i < courses.size(); i++) {
            for (int j = i + 1; j < courses.size(); j++) {
                if (courses.get(i).getName().compareTo(courses.get(j).getName()) > 0) {
                    temp = courses.get(i);
                    courses.set(i, courses.get(j));
                    courses.set(j, temp);
                }
            }
        }
        return courses;
    }

    public List<Course> sortCoursesByStatus(List<Course> courses) {
        List<Course> passed = new LinkedList<Course>();
        List<Course> active = new LinkedList<Course>();
        for (Course course:courses) {
            if (course.getStatus().equals("passed")) {
                passed.add(course);
            }
            else {
                active.add(course);
            }
        }
        return Stream.concat(active.stream(), passed.stream()).collect(Collectors.toList());
    }

    public double calcAverageGrade(List<Course> courses) {
        double credits = 0;
        double dividend = 0;
        for (Course course:courses) {
            if (course.getStatus().equals("passed") && course.getGrade() != 0.0) {
                dividend = dividend + course.getLP() * course.getGrade();
                credits += course.getLP();
            }
        }
        if (credits != 0) {
            return (double) dividend / credits;
        }
        else {
            return 0;
        }
    }

    public int calcTotalLP(List<Course> courses) {
        int totalLp = 0;
        for (Course course : courses) {
            totalLp = totalLp + course.getLP();
        }
        return totalLp;
    }

    public int calcPassedLP(List<Course> courses) {
        int passedLp = 0;
        for (Course course : courses) {
            if (course.getStatus().equals("passed")) {
                passedLp = passedLp + course.getLP();
            }
        }
        return passedLp;
    }

}
