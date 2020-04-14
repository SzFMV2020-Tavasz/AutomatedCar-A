package hu.oe.nik.szfmv.common.exceptions;

public class NegativeNumberException extends Exception {

    private String message;

    public NegativeNumberException() {
        this.message = "The variable could not be negative number";
    }

    public NegativeNumberException(String message) {
        super(message);
        this.message = message;
    }

    public NegativeNumberException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }

    public NegativeNumberException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
