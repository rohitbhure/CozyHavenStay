package com.hexaware.CozyHavenStay;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.hexaware.CozyHavenStay.model.Role;
import com.hexaware.CozyHavenStay.model.User;
import com.hexaware.CozyHavenStay.repository.RoleRepository;
import com.hexaware.CozyHavenStay.repository.UserRepository;



@SpringBootApplication
@EnableAspectJAutoProxy

public class CozyHavenStayApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(CozyHavenStayApplication.class, args);
	}
	@Autowired
	private RoleRepository rolerepo;
	
	@Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public void run(String... args) throws Exception {
        System.out.println("Seeding database with roles and users...");

        // Add Roles
        Role roleUser = new Role(null, "ROLE_USER");
        Role roleAdmin = new Role(null, "ROLE_ADMIN");

        if (!roleRepository.existsByName("ROLE_USER")) {
            roleRepository.save(roleUser);
            System.out.println("ROLE_USER added.");
        }
        if (!roleRepository.existsByName("ROLE_ADMIN")) {
            roleRepository.save(roleAdmin);
            System.out.println("ROLE_ADMIN added.");
        }

        // Add a test admin user
        if (!userRepository.existsByEmail("admin@example.com")) {
            User admin = new User();
            admin.setName("Admin");
            
            admin.setEmail("admin@example.com");
            admin.setPassword("password");
            admin.setRoles(Set.of(roleRepository.findByName("ROLE_ADMIN").orElseThrow()));
            userRepository.save(admin);
            System.out.println("Admin user added.");
        }

        // Add a test regular user
        if (!userRepository.existsByEmail("user@example.com")) {
            User user = new User();
            user.setName("User");
           
            user.setEmail("user@example.com");
            user.setPassword("password1");
            user.setRoles(Set.of(roleRepository.findByName("ROLE_USER").orElseThrow()));
            userRepository.save(user);
            System.out.println("Regular user added.");
        }

        System.out.println("Database seeding completed.");
    }

}
