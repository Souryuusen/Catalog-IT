package com.soursoft.catalogit.entity;

import com.soursoft.catalogit.dto.UserDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uid")
    private long userId;

    @NotBlank
    @Size(min = 4, max = 255)
    @Column(name = "username", unique = true, nullable = false)
    private String username;
    @NotBlank
    @Size(min = 3, max = 255)
    @Pattern(regexp = "[\\w-\\.]+@+[\\w-\\.]+\\.+[\\w-]{2,4}")
    @Column(name = "email", unique = true, nullable = false)
    private String email;
    @NotBlank
    @Size(min = 4, max = 255)
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "active")
    private boolean active;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "uid"),
            inverseJoinColumns = @JoinColumn(name = "rid")
    )
    private Set<Role> roles;

    public User() {}

    public User(String username, String email, String password, boolean active, Set<Role> userRoles) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.active = active;
        this.roles = userRoles;
    }

    public User(UserDTO userDTO) {
        this.username = userDTO.getUsername();
        this.password = userDTO.getPassword();
        this.email = userDTO.getEmail();
        this.active = false;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> userRoles) {
        this.roles = userRoles;
    }

    protected void addRole(Role role) {
        if(!this.roles.contains(role)) {
            this.roles.add((role));
        }
    }

    protected void removeRole(Role role) {
        if(this.roles.contains(role)) {
            this.roles.remove(role);
        }
    }
}

