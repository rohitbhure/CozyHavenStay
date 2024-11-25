package com.hexaware.CozyHavenStay.dto;

public class JWTAuthResponse {
	private String accessToken;
	private String tokenType = "Bearer";
	private UserRequestDTO userDto;// appending user details and JWT Token in response

	public JWTAuthResponse() {
	}

	public JWTAuthResponse(String accessToken, UserRequestDTO userDto) {
		super();
		this.accessToken = accessToken;
		this.userDto = userDto;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public UserRequestDTO getUserDto() {
		return userDto;
	}

	public void setUserDto(UserRequestDTO userDto) {
		this.userDto = userDto;
	}
}
