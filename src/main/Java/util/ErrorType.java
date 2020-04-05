package util;

public enum ErrorType {
    NAME_UNIQUENESS_OR_LENGTH(0),
    DESCRIPTION_LENGTH(1),
    DATE_FORMAT(2),
    DATE_ALREADY_PAST(3),
    IO_EXCEPTION(4),
    CLASS_NOT_FOUND_EXCEPTION(5),
    WRONG_LOGIN_INPUT(6),
    USERNAME_ALREADY_TAKEN(7),
    USERNAME_LENGTH(8),
    PASSWORD_LENGTH(9),
    PASSWORD_CONFIRMATION(10),
    SOME_SYSTEM_ERROR(11);

    private final int errNumber;
    private ErrorType(int errNumber) {
        this.errNumber = errNumber;
    }
    public final int getErrNumber() {
        return errNumber;
    }
}

