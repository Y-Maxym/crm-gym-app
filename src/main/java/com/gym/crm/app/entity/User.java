package com.gym.crm.app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Entity
@Table(
        name = "user",
        schema = "public",
        indexes = @Index(name = "user_pkey", unique = true, columnList = "id")
)
@Getter
@Builder(toBuilder = true)
@ToString
@EqualsAndHashCode
@NoArgsConstructor(force = true)
@AllArgsConstructor
public final class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false, unique = true, insertable = false)
    private final Long id;

    @Column(name = "first_name", nullable = false, length = 50)
    private final String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private final String lastName;

    @Column(name = "username", nullable = false, length = 100)
    private final String username;

    @Column(name = "password", nullable = false, length = 1024)
    private final String password;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
}
