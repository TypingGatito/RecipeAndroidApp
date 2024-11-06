package com.recipe.exceptions;

public class ConnectionPoolException extends RuntimeException {

    public ConnectionPoolException() {
        super("Произошла ошибка при работе с базой данных");
    }

    public ConnectionPoolException(String message) {
        super(message);
    }

    public ConnectionPoolException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConnectionPoolException(Throwable cause) {
        super(cause);
    }


}
