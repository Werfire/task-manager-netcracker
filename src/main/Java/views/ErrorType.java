package views;

public enum ErrorType {
    NAME_LENGTH(0),
    DESCRIPTION_LENGTH(1),
    DATE_FORMAT(2),
    DATE_ALREADY_PAST(3),
    IO_EXCEPTION(4),
    CLASS_NOT_FOUND_EXCEPTION(5);

    private final int errNumber;
    private ErrorType(int errNumber) {
        this.errNumber = errNumber;
    }
    public final int getErrNumber() {
        return errNumber;
    }
}
