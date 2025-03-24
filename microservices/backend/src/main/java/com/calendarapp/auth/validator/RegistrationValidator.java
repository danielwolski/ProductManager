package com.calendarapp.auth.validator;

import com.calendarapp.exception.LoginAlreadyExistsException;
import com.calendarapp.auth.repository.UserRepository;
import com.calendarapp.auth.model.RegisterRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationValidator {

    private final UserRepository userRepository;

    public void validate(RegisterRequest registerRequest) {
         validateLoginUniqueness(registerRequest.getLogin());
    }

    private void validateLoginUniqueness(String login) {
        if (userRepository.existsByLogin(login)) {
            throw new LoginAlreadyExistsException("Login " + login + " is already in use");
        }
    }
}
