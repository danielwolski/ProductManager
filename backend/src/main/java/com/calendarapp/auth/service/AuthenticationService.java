package com.calendarapp.auth.service;

import com.calendarapp.auth.model.AuthenticationRequest;
import com.calendarapp.auth.model.AuthenticationResponse;
import com.calendarapp.auth.model.Token;
import com.calendarapp.auth.model.TokenType;
import com.calendarapp.auth.validator.RegistrationValidator;
import com.calendarapp.auth.model.UserRole;
import com.calendarapp.auth.repository.TokenRepository;
import com.calendarapp.auth.repository.UserRepository;
import com.calendarapp.auth.model.RegisterRequest;
import com.calendarapp.auth.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UserRepository userRepository;
  private final TokenRepository tokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  private final RegistrationValidator registrationValidator;

  public AuthenticationResponse register(RegisterRequest registerRequest) {
    registrationValidator.validate(registerRequest);

    User user = new User();
    user.setUsername(registerRequest.getUsername());
    user.setLogin(registerRequest.getLogin());
    user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
    user.setUserRole(UserRole.USER);

    var savedUser = userRepository.save(user);
    var jwtToken = jwtService.generateToken(user);
    saveUserToken(savedUser, jwtToken);
    return AuthenticationResponse.builder()
            .accessToken(jwtToken)
            .build();
  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(
                      request.getLogin(),
                      request.getPassword()
              )
    );

    var user = userRepository.findByLogin(request.getLogin())
        .orElseThrow();
    var jwtToken = jwtService.generateToken(user);
    revokeAllUserTokens(user);
    saveUserToken(user, jwtToken);
    return AuthenticationResponse.builder()
        .accessToken(jwtToken)
            .username(user.getTrueUsername())
            .role(user.getUserRole())
        .build();
  }

  private void saveUserToken(User user, String jwtToken) {
    var token = Token.builder()
        .user(user)
        .token(jwtToken)
        .tokenType(TokenType.BEARER)
        .expired(false)
        .revoked(false)
        .build();
    tokenRepository.save(token);
  }

  private void revokeAllUserTokens(User user) {
    var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
    if (validUserTokens.isEmpty())
      return;

    validUserTokens.forEach(token -> {
      token.setExpired(true);
      token.setRevoked(true);
    });
    tokenRepository.saveAll(validUserTokens);
  }
}
