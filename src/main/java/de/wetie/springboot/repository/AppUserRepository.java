package de.wetie.springboot.repository;

import de.wetie.springboot.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Integer> {

    Optional<AppUser> findByUsername(String username);
}
