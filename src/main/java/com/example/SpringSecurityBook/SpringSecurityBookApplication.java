package com.example.SpringSecurityBook;

import com.example.SpringSecurityBook.enums.AppRole;
import com.example.SpringSecurityBook.model.AppUser;
import com.example.SpringSecurityBook.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//Класс ContactsAppApplication является точкой входа в Spring Boot приложение и реализует интерфейс CommandLineRunner, что позволяет выполнить код при запуске приложения.
//
//Основные функции класса:
//
//Инициализация приложения:
//Метод main запускает Spring Boot приложение.
//Создание начальных пользователей:
//Метод run вызывается автоматически при старте и создает двух пользователей: администратора и обычного пользователя.

@SpringBootApplication
public class SpringSecurityBookApplication implements CommandLineRunner {
	@Autowired
	private AppUserRepository appUserRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityBookApplication.class, args);
	}
	@Override
	public void run(String... args) {
//		createUserIfNotExists("admin@admin.admin", AppRole.ADMIN);
//		createUserIfNotExists("user@user.user", AppRole.USER);
	}

	private void createUserIfNotExists(String email, AppRole role) {
		if (appUserRepository.findByEmail(email).isEmpty()) {
			AppUser account = new AppUser();
			account.setEmail(email);
			account.setPassword(new BCryptPasswordEncoder().encode("pass"));
			account.setRole(role);
			appUserRepository.save(account);
		}
	}
}
