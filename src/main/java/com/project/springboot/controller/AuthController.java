package com.project.springboot.controller;

import com.project.springboot.model.Student;
import com.project.springboot.model.User;
import com.project.springboot.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.project.springboot.service.UserService;


@Controller
public class AuthController {

    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/auth")
    public String home(){
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        Student student = new Student();
        model.addAttribute("student", student);
        return "register";
    }

    @PostMapping("/register/save")
    public String saveStudent(@Validated @ModelAttribute("student") Student student,
                              BindingResult result,
                              Model model) {
        System.out.println(student.getEmail());
        User existingUser = userService.findUserByEmail(student.getEmail());
        System.out.println(existingUser);
        if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()){
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }

        if(result.hasErrors()){
            model.addAttribute("student", student);
            return "/register";
        }

        userService.saveUser(student);
        return "redirect:/register?success";
    }
}
