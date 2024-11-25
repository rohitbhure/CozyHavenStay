package com.hexaware.CozyHavenStay.service;



import com.hexaware.CozyHavenStay.dto.JWTAuthResponse;
import com.hexaware.CozyHavenStay.dto.LoginDto;
import com.hexaware.CozyHavenStay.dto.RegisterDto;



public interface AuthService {

	JWTAuthResponse login(LoginDto dto);

	String register(RegisterDto dto);

}