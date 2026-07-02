package com.demo.banking.app.team1.model;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Setter
@ToString
public class User {

    private final PasswordEncoder passwordEncoder;

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

    public User(PasswordEncoder passwordEncoder, long id, String username, String rawPassword, Role role){
        this.passwordEncoder = passwordEncoder;
        this.id = id;
        this.username = username;
        hashPassword(rawPassword);
        this.role = role;
    }

}
