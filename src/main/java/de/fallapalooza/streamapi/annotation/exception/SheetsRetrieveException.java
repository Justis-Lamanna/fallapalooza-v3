package de.fallapalooza.streamapi.annotation.exception;

public class SheetsRetrieveException extends RuntimeException {
    public SheetsRetrieveException() {
    }

    public SheetsRetrieveException(String message) {
        super(message);
    }

    public SheetsRetrieveException(String message, Throwable cause) {
        super(message, cause);
    }

    public SheetsRetrieveException(Throwable cause) {
        super(cause);
    }
}
