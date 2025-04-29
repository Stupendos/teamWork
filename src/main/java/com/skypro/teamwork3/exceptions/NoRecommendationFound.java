package com.skypro.teamwork3.exceptions;

public class NoRecommendationFound extends RuntimeException {

    public NoRecommendationFound(String username) {
        super("No recommendation found for " + username);
    }

    public NoRecommendationFound(String username, Throwable cause) {
        super("No recommendation found for " + username, cause);
    }
}
