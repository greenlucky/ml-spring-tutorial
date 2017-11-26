package com.ml.spring.tuto.corej.persistence.model.dto;

import com.ml.spring.tuto.corej.enums.RolesEnum;
import com.ml.spring.tuto.corej.validation.PassMatching;

import java.util.HashSet;
import java.util.Set;

@PassMatching
public class UserDto {

    private String username;
    private String email;
    private String password;
    private String confirmPassword;
    private Set<RolesEnum> roles;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public Set<RolesEnum> getRoles() {
        return roles;
    }

    public void setRole(RolesEnum role) {
        if (roles == null) roles = new HashSet<>();
        this.roles.add(role);
    }
}
