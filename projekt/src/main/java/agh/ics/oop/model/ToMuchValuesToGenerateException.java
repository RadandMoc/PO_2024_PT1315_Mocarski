package agh.ics.oop.model;

public class ToMuchValuesToGenerateException extends RuntimeException { // jak się pisze "too much"?
    private final int errorValue;

    public ToMuchValuesToGenerateException(String message, int errorValue) {
        super(message);
        this.errorValue = errorValue;
    }

    public int getErrorValue() {
        return errorValue;
    }
}
