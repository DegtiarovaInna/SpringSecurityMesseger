package com.example.SpringSecurityBook.service;

import com.example.SpringSecurityBook.model.AppUser;
import com.example.SpringSecurityBook.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;




//Загружает пользователя по email для аутентификации.
@Service
@RequiredArgsConstructor
public class AppUserService {

    @Autowired
    private AppUserRepository appUserRepository;

    public UserDetailsService getDetailsService() {

        UserDetailsService detailsService = new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                AppUser user = appUserRepository.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
                return user;
            }
        };

        return detailsService;
    }
}
