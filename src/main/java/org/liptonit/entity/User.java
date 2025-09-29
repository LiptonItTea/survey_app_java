package org.liptonit.entity;

import java.time.LocalDate;

public class User extends DBEntity{
    private String nickname;
    private String email;
    private LocalDate registrationDate;
    private String hashedPassword;

    public User(long id, String nickname, String email, LocalDate registrationDate, String hashedPassword) {
        super(id);
        this.nickname = nickname;
        this.email = email;
        this.registrationDate = registrationDate;
        this.hashedPassword = hashedPassword;
    }

    public User(Long id, User entity) {
        super(id, entity);
        this.nickname = entity.getNickname();
        this.email = entity.getEmail();
        this.registrationDate = entity.getRegistrationDate();
        this.hashedPassword = entity.getHashedPassword();
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

//    public void setNickname(String nickname) {
//        this.nickname = nickname;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public void setRegistrationDate(LocalDate registrationDate) {
//        this.registrationDate = registrationDate;
//    }
//
//    public void setHashedPassword(String hashedPassword) {
//        this.hashedPassword = hashedPassword;
//    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ID: ").append(getId())
                .append("\nNickname: ").append(nickname)
                .append("\nRegistration date: ").append(registrationDate);
        return builder.toString();
    }
}
