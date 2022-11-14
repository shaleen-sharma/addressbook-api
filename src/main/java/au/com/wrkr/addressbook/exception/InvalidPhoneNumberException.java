package au.com.wrkr.addressbook.exception;

public class InvalidPhoneNumberException extends RuntimeException {
    public InvalidPhoneNumberException(String err) {
        super(err);
    }
}

