package com.ml.spring.tuto.authal.persistence.model;

import com.ml.spring.tuto.authal.enums.RolesEnum;
import com.ml.spring.tuto.authal.persistence.model.dto.UserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @NotNull
    private String email;

    @NotNull
    private String username;

    @Value(value = "false")
    private boolean enabled;

    @Column(length = 60)
    private String password;

    @ElementCollection(targetClass = RolesEnum.class)
    @Enumerated(EnumType.STRING)
    private Set<RolesEnum> roles = new HashSet<>();

    public User() {
        super();
        this.enabled = true;
    }

    public User(UserDto userDto) {
        this.username = userDto.getUsername();
        this.email = userDto.getEmail();
        this.password = userDto.getPassword();
        this.enabled = true;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<RolesEnum> getRoles() {
        return roles;
    }

    public void setRoles(Set<RolesEnum> roles) {
        this.roles = roles;
    }

    public void addRole(RolesEnum role) {
        roles.add(role);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        roles.forEach(re -> authorities.add(new Authority(re.getName())));
        return authorities;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return id != null ? id.equals(user.id) : user.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", enabled=" + enabled +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                '}';
    }
}
