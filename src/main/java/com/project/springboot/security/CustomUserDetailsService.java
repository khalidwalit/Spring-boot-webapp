package com.project.springboot.security;


import com.project.springboot.model.Student;
import com.project.springboot.repository.StudentRepository;
import com.project.springboot.service.StudentService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private StudentRepository studentRepository;

    public CustomUserDetailsService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Student student = studentRepository.findByEmail(username);

        System.out.println(student);

        if (student != null) {
            Collection<? extends GrantedAuthority> authorities = mapRolesToAuthorities(student.getRoleName());

            System.out.println(student.getPassword());
            return new User(
                    student.getEmail(),
                    student.getPassword(),
                    authorities
            );
        } else {
            throw new UsernameNotFoundException("Invalid Username and Password");
        }
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(String roleName) {
        if (roleName != null && !roleName.isEmpty()) {
            // Create a collection with a single SimpleGrantedAuthority using the roleName
            return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + roleName));
        } else {
            return Collections.emptySet(); // No roles, return an empty set
        }
    }
}
