package io.alchemia.movieidentityservice.repository;

import io.alchemia.movieidentityservice.entity.UserCredentialEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCredentialRepository extends JpaRepository<UserCredentialEntity, Integer> {
}
