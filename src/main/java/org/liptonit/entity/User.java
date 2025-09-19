package org.liptonit.entity;

import java.time.LocalDate;

public class User {
    private long id;
    private String nickname;
    private String email;
    private LocalDate registrationDate;
    private String hashedPassword;

    public User(long id, String nickname, String email, LocalDate registrationDate, String hashedPassword) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.registrationDate = registrationDate;
        this.hashedPassword = hashedPassword;
    }

    public long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }
}
