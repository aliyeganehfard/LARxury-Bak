package ir.larxury.auth.server.common.exception.handler;

public class AuthException extends RuntimeException{

    public AuthException() {
    }

    public AuthException(String message) {
        super(message);
    }
}
