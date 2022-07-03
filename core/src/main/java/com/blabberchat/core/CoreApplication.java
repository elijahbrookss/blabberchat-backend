package com.blabberchat.core;

import com.blabberchat.core.dtos.NewUserDTO;
import com.blabberchat.core.dtos.RoleDTO;
import com.blabberchat.core.models.Role;
import com.blabberchat.core.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@Slf4j
public class CoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoreApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

//	@Bean
//	CommandLineRunner run(UserService userService) {
//		log.info("Seeding database");
//		return args -> {
//
////			log.info("Seeding roles");
////			Role userRole = userService.createRole("USER_ROLE");
////			Role adminRole = userService.createRole("ADMIN_ROLE");
//			RoleDTO userRole = new RoleDTO("USER_ROLE");
//			RoleDTO adminRole = new RoleDTO("ADMIN_ROLE");
//
//			log.info("Seeding users");
//			userService.createUser( NewUserDTO.builder()
//					.email("test1@email.com")
//					.password("password")
//					.profilePicture("pfp")
//					.username("test1")
//					.roles(List.of( userRole ))
//					.build()
//			);
//			userService.createUser( NewUserDTO.builder()
//					.email("test2@email.com")
//					.password("password")
//					.profilePicture("pfp")
//					.username("test2")
//					.roles(List.of( userRole ))
//					.build()
//			);
//			userService.createUser( NewUserDTO.builder()
//					.email("test3@email.com")
//					.password("password")
//					.profilePicture("pfp")
//					.username("test3")
//					.roles(List.of( userRole, adminRole ))
//					.build()
//			);
//			userService.createUser( NewUserDTO.builder()
//					.email("test4@email.com")
//					.password("password")
//					.profilePicture("pfp")
//					.username("test4")
//					.roles(List.of( userRole, adminRole ))
//					.build()
//			);
//
//			log.info("Users seeded");
//		};
//	}

}
