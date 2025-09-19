package com.booking.BookingBackendPortal.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class JwtResponse {
    // Getters and setters
    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private List<String> roles;

    public JwtResponse(String accessToken, Long id, String username, List<String> roles) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.roles = roles;
    }

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public JwtResponse(String token, String type, Long id, String username, List<String> roles) {
		super();
		this.token = token;
		this.type = type;
		this.id = id;
		this.username = username;
		this.roles = roles;
	}

	public JwtResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "JwtResponse [token=" + token + ", type=" + type + ", id=" + id + ", username=" + username + ", roles="
				+ roles + "]";
	}
    
    

}