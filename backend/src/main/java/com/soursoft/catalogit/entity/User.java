package com.soursoft.catalogit.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.soursoft.catalogit.dto.UserDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Set;
import java.util.TreeSet;

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
    @JsonManagedReference
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "uid"),
            inverseJoinColumns = @JoinColumn(name = "rid")
    )
    private Set<Role> roles;

//    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JoinTable(
//            name = "user_watchlist",
//            joinColumns = @JoinColumn(name = "uid"),
//            inverseJoinColumns = @JoinColumn(name = "movie_id")
//    )
//    private Set<Movie> watchlistSet = new TreeSet<>();

    @OneToMany(mappedBy = "watchlistId")
    @JsonManagedReference
    private Set<WatchlistElement> userWatchlist = new TreeSet<>();


    public UserDTO convertToDTO() {
        return new UserDTO(getUserId(), getUsername(), getEmail(), getRoles());
    }

    public User() {}

    public User(String username, String email, String password, boolean active, Set<Role> userRoles) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.active = active;
        this.roles = userRoles;
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

    public Set<WatchlistElement> getUserWatchlist() {
        return userWatchlist;
    }

    public void setUserWatchlist(Set<WatchlistElement> userWatchlist) {
        this.userWatchlist = userWatchlist;
    }

    public void addElementToTheWatchlist(WatchlistElement element) {

    }

    public void removeElementFromTheWatchlist(WatchlistElement element) {

    }

    //
//    public Set<Movie> getWatchlistSet() {
//        return watchlistSet;
//    }
//
//    public void setWatchlistSet(Set<Movie> watchlistSet) {
//        this.watchlistSet = watchlistSet;
//    }
//
//    public void addMovieToWatchlist(Movie movie) {
//        this.watchlistSet.add(movie);
//    }
//
//    public void removeMovieFromWatchlist(Movie movie) {
//        this.watchlistSet.remove(movie);
//    }
}

