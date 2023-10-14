package com.project.springboot.service;

import com.project.springboot.model.Student;
import com.project.springboot.model.User;

import java.util.List;

public interface UserService {
    void saveUser(Student student);

    User findUserByEmail(String email);

    List<User> findAllUsers();
}
