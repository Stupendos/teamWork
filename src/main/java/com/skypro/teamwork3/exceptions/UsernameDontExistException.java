package com.skypro.teamwork3.exceptions;

public class UsernameDontExistException extends Exception {
    public UsernameDontExistException() {
    }

    public UsernameDontExistException(String message) {
        super(message);
    }

    public UsernameDontExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public UsernameDontExistException(Throwable cause) {
        super(cause);
    }
}
