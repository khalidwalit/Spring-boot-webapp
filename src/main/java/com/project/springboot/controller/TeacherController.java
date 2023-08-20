package com.project.springboot.controller;

import com.project.springboot.model.Teacher;
import com.project.springboot.service.TeacherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TeacherController {

    private TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        super();
        this.teacherService = teacherService;
    }

    // handler method to handle list students and return mode and view
    @GetMapping("/teachers")
    public String listTeachers(Model model) {
        model.addAttribute("teachers", teacherService.getAllTeacher());
        return "teachers";
    }

    @GetMapping("/teachers/new")
    public String createTeacherForm(Model model) {
        System.out.println("wtf");
        Teacher teacher = new Teacher();
        model.addAttribute("teacher", teacher);
        return "create_teacher";
    }

    @PostMapping("/teachers")
    public String saveTeacher(@ModelAttribute("teacher") Teacher teacher) {
        teacherService.saveTeacher(teacher);
        return "redirect:/teachers";
    }

    @GetMapping("/teacher/{id}")
    public String deleteStudent(@PathVariable Long id) {
        teacherService.deleteTeacherById(id);
        return "redirect:/teachers";
    }
//
//    @GetMapping("/students/edit/{id}")
//    public String editStudentForm(@PathVariable Long id, Model model) {
//        model.addAttribute("student", studentService.getStudentById(id));
//        return "edit_student";
//    }
//
//    @PostMapping("/students/{id}")
//    public String updateStudent(@PathVariable Long id,
//                                @ModelAttribute("student") Student student,
//                                Model model) {
//
//        // get student from database by id
//        Student existingStudent = studentService.getStudentById(id);
//        existingStudent.setId(id);
//        existingStudent.setFirstName(student.getFirstName());
//        existingStudent.setLastName(student.getLastName());
//        existingStudent.setEmail(student.getEmail());
//
//        // save updated student object
//        studentService.updateStudent(existingStudent);
//        return "redirect:/students";
//    }
//
//    // handler method to handle delete student request
//
//    @GetMapping("/students/{id}")
//    public String deleteStudent(@PathVariable Long id) {
//        studentService.deleteStudentById(id);
//        return "redirect:/students";
//    }

}