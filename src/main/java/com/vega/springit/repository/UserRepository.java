package com.vega.springit.repository;

import com.vega.springit.domain.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByEmail(String email);

    Optional<AppUser> findByEmailAndActivationCode(String email, String activationCode);

}
