package com.project.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.springboot.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long>{

    Student findByEmail(String email);

}