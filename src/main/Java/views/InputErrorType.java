package views;

public enum InputErrorType {
    NAME_LENGTH(0),
    DESCRIPTION_LENGTH(1),
    DATE_FORMAT(2),
    DATE_ALREADY_PAST(3);

    private final int errNumber;
    private InputErrorType(int errNumber) {
        this.errNumber = errNumber;
    }
    public final int getErrNumber() {
        return errNumber;
    }
}
