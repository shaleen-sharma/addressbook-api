package au.com.wrkr.addressbook.controller;

import au.com.wrkr.addressbook.exception.InvalidPhoneNumberException;
import au.com.wrkr.addressbook.model.AddressBookEntries;
import au.com.wrkr.addressbook.phone.PhoneNormalisationService;
import au.com.wrkr.addressbook.service.AddressBookService;
import com.google.i18n.phonenumbers.NumberParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@RestController
@Validated
public class AddressBookController {

    @Autowired
    AddressBookService addressBookService;
    @Autowired
    PhoneNormalisationService phoneNormalisationService;

    Logger logger = LoggerFactory.getLogger(AddressBookController.class);

    @Autowired
    public AddressBookController(final AddressBookService addressBookService,
                                 final PhoneNormalisationService phoneNormalisationService) {
        this.addressBookService = addressBookService;
        this.phoneNormalisationService = phoneNormalisationService;
    }

    private static final String pattern = "^[a-zA-Z0-9]*$";

    @GetMapping(value = "/user/{username}/addressbook")
    @ResponseStatus(HttpStatus.OK)
    public List<AddressBookEntries> getEntries(
            @PathVariable(name = "username") @NotNull @Pattern(regexp =
                    pattern, message = "Invalid username") final String username,
            @RequestParam(name = "addressBookName", required = false) @Pattern(regexp =
                    pattern, message = "Invalid addressBookName") String addressBookName) {
        logger.info("getEntries called: {}: ", username);
       return addressBookService.getAddressBookEntries(username.toUpperCase(), addressBookName.toUpperCase());
    }

    @GetMapping(value = "/user/{username}/addressbook/{addressBookName1}/{addressBookName2}")
    @ResponseStatus(HttpStatus.OK)
    public List<AddressBookEntries> getAddressBookIntersection(
            @PathVariable(name = "username") @Pattern(regexp =
                    pattern, message = "Invalid username") final String username,
            @PathVariable(name = "addressBookName1") @Pattern(regexp =
                    pattern, message = "Invalid addressBookName1") String addressBookName1,
            @PathVariable(name = "addressBookName2") @Pattern(regexp =
                    pattern, message = "Invalid addressBookName2") String addressBookName2) {
        logger.info("getAddressBookIntersection called, userName = {}, addressBookName1 = {}, " +
                "addressBookName2 = {} ", username, addressBookName1, addressBookName2);
        return addressBookService.getUniqueNamesOfLists(username.toUpperCase(),
                addressBookName1.toUpperCase(), addressBookName2.toUpperCase());
    }

    @PostMapping(value = "/user/{username}/addressbook/{addressBookName}/entries")
    @ResponseStatus(HttpStatus.CREATED)
    public void setEntries(
            @PathVariable(name = "username") @Pattern(regexp =
                    pattern, message = "Invalid username") final String username,
            @PathVariable(name = "addressBookName") @Pattern(regexp =
                    pattern, message = "Invalid addressBookName") String addressBookName,
            @RequestParam(name = "personName") @Pattern(regexp =
                    pattern, message = "Invalid personName") String personName,
            @RequestParam(name = "phone") String phone) {
        logger.info("setEntries called: username = {}, addressBookName = {}, personName = {}," +
                " phone = {} : ", username, addressBookName, personName, phone);

         addressBookService.setAddressBookEntries(username.toUpperCase(),
                 addressBookName.toUpperCase(),
                 personName.toUpperCase(),
                 validateAndNormalisePhone(phone));
    }

    /**
     * Validates and normalise a phone number to International format
     * @param phone
     * @return
     */
    private String validateAndNormalisePhone(String phone){
        try {
           return phoneNormalisationService.normalise(phone);
        } catch (NumberParseException e) {
            throw new InvalidPhoneNumberException("Invalid Phone number: " + phone);
        }
    }
}
