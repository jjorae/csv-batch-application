package me.jjorae.csv_batch_application.exception;

public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidationException(String fieldName, String message) {
        super(String.format("'%s' 필드 검증 실패 : %s", fieldName, message));
    }
}
