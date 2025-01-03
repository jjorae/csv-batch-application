package me.jjorae.csv_batch_application.exception;

public class ParsingException extends RuntimeException {
    public ParsingException(String message) {
        super(message);
    }

    public ParsingException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParsingException(String fieldName, String message) {
        super(String.format("'%s' 필드 파싱 실패 : %s", fieldName, message));
    }
}
