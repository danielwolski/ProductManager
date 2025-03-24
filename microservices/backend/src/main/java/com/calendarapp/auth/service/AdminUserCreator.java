package com.calendarapp.auth.service;

import com.calendarapp.auth.model.User;
import com.calendarapp.auth.model.UserRole;
import com.calendarapp.auth.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminUserCreator {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.login:admin}")
    private String adminLogin;

    @Value("${admin.password:admin}")
    private String adminPassword;

    @Value("${admin.username:admin}")
    private String adminUsername;

    @PostConstruct
    public void initializeAdminUser() {
        if (!userRepository.existsByLogin(adminLogin)) {
            User adminUser = new User();
            adminUser.setUsername(adminUsername);
            adminUser.setLogin(adminLogin);
            adminUser.setPassword(passwordEncoder.encode(adminPassword));
            adminUser.setUserRole(UserRole.ADMIN);

            userRepository.save(adminUser);
            log.info("Admin user created successfully.");
        } else {
            log.info("Admin user already exists.");
        }
    }
}
