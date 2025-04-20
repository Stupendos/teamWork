package com.skypro.teamwork3.exceptions;

public class UsernameDontExistException extends RuntimeException {
    public UsernameDontExistException() {
    }

    public UsernameDontExistException(String username) {
        super("User search by username failed: " + username);
    }

    public UsernameDontExistException(String username, Throwable cause) {
        super("User search by username failed: " + username, cause);
    }
}
