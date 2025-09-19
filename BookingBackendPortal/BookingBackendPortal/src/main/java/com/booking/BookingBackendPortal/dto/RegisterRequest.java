package com.booking.BookingBackendPortal.dto;
import jakarta.validation.constraints.NotBlank;
import java.util.Set;

public class RegisterRequest {
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private Set<String> roles; // e.g. ["ROLE_USER"]

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}

