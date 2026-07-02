package com.demo.banking.app.team1.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.concurrent.atomic.AtomicLong;

@Getter
@Setter
@ToString
public class User {

    private final PasswordEncoder passwordEncoder;

    private static final AtomicLong ID_GENERATOR = new AtomicLong(1);
    @Setter(AccessLevel.NONE)
    private long id;

    @NotNull
    // Only allow letters and numbers in the username with limited length
    @Pattern(regexp = "^[a-zA-Z0-9]{3,20}$")
    private String username;

    @NotNull
    @Setter(value = AccessLevel.NONE)
    private String passwordHash;

    @NotNull
    private Role role;

    private void hashPassword(String rawPassword){
        this.passwordHash = passwordEncoder.encode(rawPassword);
    }

    public User(PasswordEncoder passwordEncoder, String username, String rawPassword, Role role){
        this.id = ID_GENERATOR.getAndIncrement();
        this.passwordEncoder = passwordEncoder;
        this.username = username;
        hashPassword(rawPassword);
        this.role = role;
    }

}
