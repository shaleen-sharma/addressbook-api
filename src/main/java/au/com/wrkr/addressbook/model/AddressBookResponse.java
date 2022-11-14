package au.com.wrkr.addressbook.model;

import lombok.Data;


@Data
public class AddressBookResponse {
    private String addressBookName;
    private String name;
    private String phone;
}
