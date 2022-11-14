package au.com.wrkr.addressbook.exception;

public class NoAddressBookFoundException extends RuntimeException {
    public NoAddressBookFoundException(String err) {
        super(err);
    }
}
