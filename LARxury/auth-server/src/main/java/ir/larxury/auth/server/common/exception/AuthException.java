package ir.larxury.auth.server.common.exception;

public class AuthException extends RuntimeException{

    public AuthException() {
    }

    public AuthException(String message) {
        super(message);
    }
}
