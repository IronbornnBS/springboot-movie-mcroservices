package io.alchemia.movieidentityservice.service;

import io.alchemia.movieidentityservice.entity.UserCredentialEntity;
import io.alchemia.movieidentityservice.repository.UserCredentialRepository;
import io.alchemia.movieidentityservice.util.JwtUtility;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final JwtUtility jwtUtility;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final UserCredentialRepository credentialRepository;

    public AuthenticationService(final JwtUtility jwtUtility,
                                 final PasswordEncoder passwordEncoder,
                                 final AuthenticationManager authenticationManager,
                                 final UserCredentialRepository credentialRepository) {
        this.jwtUtility = jwtUtility;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.credentialRepository = credentialRepository;
    }

    public String createUser(UserCredentialEntity userCredential) {

        userCredential.setPassword(passwordEncoder.encode(userCredential.getPassword()));
        credentialRepository.save(userCredential);

        return "User added to the database";
    }

    public String createToken(UserCredentialEntity userCredential) {
        Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        userCredential.getUsername(),
                        userCredential.getPassword()));

        if ( authenticate.isAuthenticated()) {
            return jwtUtility.generateToken(userCredential.getUsername());
        } else {
            throw new RuntimeException("invalid access");
        }
    }

    public void validateToken(String token) {
        jwtUtility.validateToken(token);
    }
}
