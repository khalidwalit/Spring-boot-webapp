package com.project.springboot.service;

import java.util.List;

import com.project.springboot.model.Student;

public interface StudentService {
    List<Student> getAllStudents();

    Student saveStudent(Student student);

    Student getStudentById(Long id);

    Student updateStudent(Student student);

    void deleteStudentById(Long id);

    Student findStudentByEmail(String email);
}