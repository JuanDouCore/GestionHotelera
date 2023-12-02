package ar.com.juanferrara.GestionHotelera.domain.exceptions;

public class TokenRefreshException extends RuntimeException{
    public TokenRefreshException(String token, String message) {
        super(String.format("Failed for [%s]: %s", token, message));
    }
}
