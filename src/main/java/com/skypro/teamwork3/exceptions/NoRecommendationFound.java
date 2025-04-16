package com.skypro.teamwork3.exceptions;

public class NoRecommendationFound extends Exception{
    public NoRecommendationFound() {
    }

    public NoRecommendationFound(String message) {
        super(message);
    }

    public NoRecommendationFound(String message, Throwable cause) {
        super(message, cause);
    }

    public NoRecommendationFound(Throwable cause) {
        super(cause);
    }
}
