package com.project.springboot.repository;

import com.project.springboot.model.Student;
import com.project.springboot.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Long>{

}