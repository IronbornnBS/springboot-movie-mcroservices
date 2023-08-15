package io.alchemia.movieidentityservice.util;


import io.alchemia.movieidentityservice.entity.UserCredentialEntity;
import io.alchemia.movieidentityservice.repository.UserCredentialRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserCredentialUtility implements UserDetailsService {

    private final UserCredentialRepository credentialRepository;

    public UserCredentialUtility(UserCredentialRepository credentialRepository) {
        this.credentialRepository = credentialRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserCredentialEntity> credentials = credentialRepository.findByUsername(username);

        return credentials.map(UserDetailUtility::new)
                .orElseThrow(() ->
                        new UsernameNotFoundException("user not found with name :" + username)
                );
    }
}
