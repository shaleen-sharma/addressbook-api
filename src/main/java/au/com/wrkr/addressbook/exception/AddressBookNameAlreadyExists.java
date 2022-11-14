package au.com.wrkr.addressbook.exception;

public class AddressBookNameAlreadyExists extends RuntimeException {
    public AddressBookNameAlreadyExists(String err) {
        super(err);
    }
}

