package com.example.SpringSecurityBook.repository;

import com.example.SpringSecurityBook.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
//Используется для доступа к данным пользователя в базе данных.
public interface AppUserRepository extends JpaRepository<AppUser, Integer> {
    Optional<AppUser> findByEmail(String email);
}
