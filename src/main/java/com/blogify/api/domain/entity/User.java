package com.blogify.api.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails { // <-- IMPLEMENT THE INTERFACE

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // --- UserDetails Methods ---

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // For now, we are not using roles. Return an empty list.
        return List.of();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        // Our UserDetails "username" will be the email, as it's unique.
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Assume accounts never expire.
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Assume accounts are never locked.
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Assume credentials never expire.
    }

    @Override
    public boolean isEnabled() {
        return true; // Assume accounts are always enabled.
    }
}