package com.project.springboot.service.impl;
import com.project.springboot.model.Role;
import com.project.springboot.model.Student;
import com.project.springboot.model.Teacher;
import com.project.springboot.model.User;
import com.project.springboot.repository.RoleRepository;
import com.project.springboot.repository.UserRepository;
import com.project.springboot.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;



import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        super();
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(Student student) {
        User user = new User();
        user.setName(student.getFirstName() + " " + student.getLastName());
        user.setEmail(student.getEmail());
        user.setPassword(passwordEncoder.encode("test"));

        Role role = roleRepository.findByName("ROLE_ADMIN");
        if(role == null){
            role = checkRoleExist();
        }
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);
    }

    @Override
    public User findUserByEmail(String email) {
        System.out.println("natang");
        return userRepository.findByEmail(email);
    }


    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    private Role checkRoleExist(){
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        return roleRepository.save(role);
    }


}
