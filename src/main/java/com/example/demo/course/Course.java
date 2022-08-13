package com.example.demo.course;

import javax.persistence.*;

@Entity
@Table(name = "courses")
public class Course {
    @Id
    @SequenceGenerator(name = "course_sequence", sequenceName = "course_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_sequence")
    Long id;
    String name;
    String professor;
    Integer LP;
    String status; //passed oder active
    Double grade;

    public Course() {
    }

    public Course(String name, String professor, Integer LP, String status, Double grade) {
        this.name = name;
        this.professor = professor;
        this.LP = LP;
        this.status = status;
        this.grade = grade;
    }

    public Course(String name, String professor, Integer LP, String status) {
        this.name = name;
        this.professor = professor;
        this.LP = LP;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public Integer getLP() {
        return LP;
    }

    public void setLP(Integer LP) {
        this.LP = LP;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", professor='" + professor + '\'' +
                ", LP=" + LP +
                ", status='" + status + '\'' +
                ", grade=" + grade +
                '}';
    }
}
