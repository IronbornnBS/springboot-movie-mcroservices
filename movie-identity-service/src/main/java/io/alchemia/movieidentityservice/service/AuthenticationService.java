package io.alchemia.movieidentityservice.service;

import io.alchemia.movieidentityservice.entity.UserCredentialEntity;
import io.alchemia.movieidentityservice.repository.UserCredentialRepository;
import io.alchemia.movieidentityservice.util.JwtUtility;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final JwtUtility jwtUtility;

    private final PasswordEncoder passwordEncoder;

    private final UserCredentialRepository credentialRepository;

    public AuthenticationService(final JwtUtility jwtUtility,
                                 final PasswordEncoder passwordEncoder,
                                 final UserCredentialRepository credentialRepository) {
        this.jwtUtility = jwtUtility;
        this.passwordEncoder = passwordEncoder;
        this.credentialRepository = credentialRepository;
    }

    public String createUser(UserCredentialEntity userCredential) {

        userCredential.setPassword(passwordEncoder.encode(userCredential.getPassword()));
        credentialRepository.save(userCredential);

        return "User added to the database";
    }

    public String createToken(String username) {
        return jwtUtility.generateToken(username);
    }

    public void validateToken(String token) {
        jwtUtility.validateToken(token);
    }
}
