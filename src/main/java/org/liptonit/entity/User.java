package org.liptonit.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Objects;

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

    public User(ResultSet rs) throws SQLException {
        super(rs);

        this.nickname = rs.getString("nickname");
        this.email = rs.getString("email");
        this.registrationDate = rs.getDate("registration_date").toLocalDate();
        this.hashedPassword = rs.getString("hashed_password");
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

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return Objects.equals(nickname, user.nickname) && Objects.equals(email, user.email) && Objects.equals(registrationDate, user.registrationDate) && Objects.equals(hashedPassword, user.hashedPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), nickname, email, registrationDate, hashedPassword);
    }

    @Override
    public String toString() {
        return "ID: " + getId() +
                "\nNickname: " + nickname +
                "\nRegistration date: " + registrationDate;
    }
}
