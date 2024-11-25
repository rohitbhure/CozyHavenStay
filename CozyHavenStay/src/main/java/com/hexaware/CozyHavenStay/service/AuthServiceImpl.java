package com.hexaware.CozyHavenStay.service;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hexaware.CozyHavenStay.dto.JWTAuthResponse;
import com.hexaware.CozyHavenStay.dto.LoginDto;
import com.hexaware.CozyHavenStay.dto.RegisterDto;
import com.hexaware.CozyHavenStay.dto.UserRequestDTO;
import com.hexaware.CozyHavenStay.exception.BadRequestException;
import com.hexaware.CozyHavenStay.model.Role;
import com.hexaware.CozyHavenStay.model.User;
import com.hexaware.CozyHavenStay.repository.RoleRepository;
import com.hexaware.CozyHavenStay.repository.UserRepository;
import com.hexaware.CozyHavenStay.security.JwtTokenProvider;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j

public class AuthServiceImpl implements AuthService {

	private AuthenticationManager authenticationManager;

	private UserRepository userRepo;

	private RoleRepository roleRepo;

	private PasswordEncoder passwordEncoder;

	private JwtTokenProvider jwtTokenProvider;

	@Autowired

	public AuthServiceImpl(AuthenticationManager authenticationManager,

			UserRepository userRepo, RoleRepository roleRepo, PasswordEncoder passwordEncoder,

			JwtTokenProvider jwtTokenProvider) {

		this.authenticationManager = authenticationManager;

		this.userRepo = userRepo;

		this.roleRepo = roleRepo;

		this.passwordEncoder = passwordEncoder;

		this.jwtTokenProvider = jwtTokenProvider;

	}

	@Override
	public JWTAuthResponse login(LoginDto dto) {

		System.out.println(("object received" + dto));

		Authentication authentication = authenticationManager.authenticate(

				new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = jwtTokenProvider.generateToken(authentication);

		System.out.println("Token generated : " + token);

		User user = userRepo.findByEmail(dto.getEmail()).get();

		System.out.println("user object found in repo " + user);

		UserRequestDTO userDto = new UserRequestDTO();

		userDto.setName(user.getName());

		userDto.setEmail(user.getEmail());

		

		String role = "ROLE_USER";

		Set<Role> roleUser = user.getRoles();

		for (Role roleTemp : roleUser)

		{

			if (roleTemp.getName().equalsIgnoreCase("ROLE_ADMIN"))

				role = "ROLE_ADMIN";

		}

		userDto.setRole(role);

		return new JWTAuthResponse(token, userDto);

	}

	@Override

	public String register(RegisterDto dto) {


		if (userRepo.existsByEmail(dto.getEmail()))

			throw new BadRequestException(HttpStatus.BAD_REQUEST, "Email already exist");

		User user = new User();

		user.setName(dto.getName());

		user.setEmail(dto.getEmail());

		

		user.setPassword(passwordEncoder.encode(dto.getPassword()));

		Set<Role> roles = new HashSet<>();

		Role role = roleRepo.findByName("ROLE_USER").get();

		roles.add(role);

		user.setRoles(roles);

		userRepo.save(user);

		return "Register Successfull!..";

	}

}
