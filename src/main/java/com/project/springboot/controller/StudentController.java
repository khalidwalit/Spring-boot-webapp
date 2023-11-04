package com.project.springboot.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.project.springboot.model.Student;
import com.project.springboot.service.StudentService;

@Controller
public class StudentController {

    private StudentService studentService;
    private PasswordEncoder passwordEncoder;

    public StudentController(StudentService studentService, PasswordEncoder passwordEncoder) {
        super();
        this.studentService = studentService;
        this.passwordEncoder = passwordEncoder;
    }

    // handler method to handle list students and return mode and view
    @GetMapping("/students")
    public String listStudents(Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        return "students";
    }

    @GetMapping("/students/new")
    public String createStudentForm(Model model) {

        // create student object to hold student form data
        Student student = new Student();
        model.addAttribute("student", student);
        return "create_student";

    }

    @PostMapping("/students")
    public String saveStudent(@ModelAttribute("student") Student student) {
//        System.out.println(student.getEmail());
//        student.setPassword("password");
//        student.setRoleName("STUDENT");

        Student existingUser = studentService.findStudentByEmail(student.getEmail());
        if ( existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()){
            return "redirect:/students/new";
        }

        student.setPassword(passwordEncoder.encode(student.getPassword()));
        System.out.println(student.getPassword());
        studentService.saveStudent(student);
        return "redirect:/";
    }

    @GetMapping("/students/edit/{id}")
    public String editStudentForm(@PathVariable Long id, Model model) {
        model.addAttribute("student", studentService.getStudentById(id));
        return "edit_student";
    }

    @PostMapping("/students/{id}")
    public String updateStudent(@PathVariable Long id,
                                @ModelAttribute("student") Student student,
                                Model model) {

        // get student from database by id
        Student existingStudent = studentService.getStudentById(id);
        existingStudent.setId(id);
        existingStudent.setFirstName(student.getFirstName());
        existingStudent.setLastName(student.getLastName());
        existingStudent.setEmail(student.getEmail());

        // save updated student object
        studentService.updateStudent(existingStudent);
        return "redirect:/";
    }

    // handler method to handle delete student request

    @GetMapping("/students/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentService.deleteStudentById(id);
        return "redirect:/";
    }

}