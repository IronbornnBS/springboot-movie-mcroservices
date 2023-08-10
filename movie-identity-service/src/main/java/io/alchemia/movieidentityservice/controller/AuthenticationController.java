package io.alchemia.movieidentityservice.controller;

import io.alchemia.movieidentityservice.entity.UserCredentialEntity;
import io.alchemia.movieidentityservice.service.AuthenticationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public String createUser(@RequestBody UserCredentialEntity userCredential) {
        return authenticationService.createUser(userCredential);
    }

    @GetMapping("/token")
    public String createToken(UserCredentialEntity userCredential) {
        return authenticationService.createToken(userCredential.getUsername());
    }

    @GetMapping("validate")
    public void validateToken(@RequestParam("token") String token) {
        authenticationService.validateToken(token);
    }
}
