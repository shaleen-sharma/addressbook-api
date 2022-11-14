package au.com.wrkr.addressbook.phone;

import au.com.wrkr.addressbook.exception.InvalidPhoneNumberException;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PhoneNormalisationService {

    private static final Logger LOG = LoggerFactory.getLogger(PhoneNormalisationService.class);
    private final PhoneNumberUtil phoneNumberUtil;

    public PhoneNormalisationService(PhoneNumberUtil phoneNumberUtil) {
        this.phoneNumberUtil = phoneNumberUtil;
    }

    // mobile numbers could be in different formats eg. +614..., 04..., 0614...
    // the output is always in E164 format (or null) eg. +61433222111
    public String normalise(String mobile) throws NumberParseException {
        var phoneNumber = phoneNumberUtil.parse(mobile, "AU");
        if(!phoneNumberUtil.isValidNumber(phoneNumber)){
            throw new InvalidPhoneNumberException("Invalid phone number: " + phoneNumber.toString());
        }
        return phoneNumberUtil.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.E164);
    }

}
