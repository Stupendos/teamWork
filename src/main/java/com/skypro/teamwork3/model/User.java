package com.skypro.teamwork3.model;

import jakarta.persistence.Entity;

import java.util.Objects;

public class User {

    private String ID;
    private String username;
    private String firstName;
    private String lastName;

    public User() {
    }

    public User(String ID, String username, String firstName, String lastName) {
        this.ID = ID;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getID() {
        return ID;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(ID, user.ID) && Objects.equals(username, user.username) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, username, firstName, lastName);
    }

    @Override
    public String toString() {
        return "User{" +
                "ID='" + ID + '\'' +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
