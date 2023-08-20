package com.project.springboot.service;

import java.util.List;

import com.project.springboot.model.Teacher;


public interface TeacherService {
    List<Teacher> getAllTeacher();

    Teacher saveTeacher(Teacher teacher);
//
//    Teacher getTeacherById(Long id);
//
//    Teacher updateTeacher(Teacher teacher);
//
    void deleteTeacherById(Long id);
}