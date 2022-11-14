package au.com.wrkr.addressbook.service;

import au.com.wrkr.addressbook.exception.AddressBookNameAlreadyExists;
import au.com.wrkr.addressbook.exception.NoAddressBookFoundException;
import au.com.wrkr.addressbook.model.AddressBook;
import au.com.wrkr.addressbook.model.AddressBookEntries;
import au.com.wrkr.addressbook.repository.AddressBookEntriesRepository;
import au.com.wrkr.addressbook.repository.AddressBookRepository;
import au.com.wrkr.addressbook.repository.AddressBookUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface AddressBookService {

    List<AddressBookEntries> getAddressBookEntries(String userName,
                                                   String addressBookName);

    void setAddressBookEntries(String userName, String addressBookName,
                               String personName, String phone);

    List<AddressBookEntries> getUniqueNamesOfLists(String userName,
                                                   String addressBookName1,
                                                   String addressBookName2);

}

@Service
class AddressBookServiceImpl implements AddressBookService {

    @Autowired
    AddressBookEntriesRepository addressBookEntriesRepository;
    @Autowired
    AddressBookUserRepository addressBookUserRepository;
    @Autowired
    AddressBookRepository addressBookRepository;


    @Autowired
    public AddressBookServiceImpl(final AddressBookEntriesRepository addressBookEntriesRepository
    ) {
        this.addressBookEntriesRepository = addressBookEntriesRepository;

    }


    @Override
    public List<AddressBookEntries> getAddressBookEntries(String userName,
                                                          String addressBookName) {
        // Check if requested addressbook exists
        AddressBook addressBook =
                addressBookRepository.findAddressBooksByName(addressBookName).orElseThrow(()
                        -> new NoAddressBookFoundException("No address book " +
                        "found: " + addressBookName));

        // Check if the addressbook requested belongs to the user
        if (addressBookUserRepository.findByUserNameAndAddressbookid(userName
                , addressBook.getId()).isEmpty()) {
            throw new NoAddressBookFoundException("No address book found for " +
                    "the user: " + userName);
        }
        return addressBookEntriesRepository.getAddressBookEntriesByUser(userName, addressBookName);
    }

    @Override
    @Transactional
    public void setAddressBookEntries(String userName, String addressBookName
            , String personName, String phone) {
        String addressBookId = generateUniqueAddressBookId(addressBookName,
                personName);
        //check if an addressbook entry with the same name already exists in
        // the requested addressbook
        checkIfAddressbookEntryAlreadyExists(personName, addressBookName);
        //Create a new addressbook entry
        addressBookEntriesRepository.insertAddressBook(addressBookId,
                addressBookName);
        addressBookUserRepository.insertAddressBookUser(userName,
                addressBookId);
        addressBookEntriesRepository.insertAddressBookEntries(addressBookId,
                personName, phone);
    }

    public List<AddressBookEntries> getUniqueNamesOfLists(String userName,
                                                          String addressBookName1,
                                                          String addressBookName2) {
        //Get the entries for the 2 requested addressbooks
        List<AddressBookEntries> list1 = getAddressBookEntries(userName,
                addressBookName1);
        List<AddressBookEntries> list2 = getAddressBookEntries(userName,
                addressBookName2);
        return evaluateUniqueNames(list1, list2);
    }

    private List<AddressBookEntries> evaluateUniqueNames(List<AddressBookEntries> list1,
                                                         List<AddressBookEntries> list2) {
        //Call removeCommons() twice with reversed params to remove commons
        // from each list and then concat the two
        return Stream.concat(removeCommons(list1, list2).stream(),
                removeCommons(list2, list1).stream())
                .collect(Collectors.toList());
    }

    private List<AddressBookEntries> removeCommons(List<AddressBookEntries> list1, List<AddressBookEntries> list2) {
        List<String> names =
                list2.stream().map(l2 -> l2.getName()).collect(Collectors.toList());
        return list1.stream()
                .filter(l1 -> !names.contains(l1.getName()))
                .collect(Collectors.toList());
    }

    private void checkIfAddressbookEntryAlreadyExists(String personName,
                                                      String addressBookName) {
        Optional<AddressBook> addressBook =
                addressBookRepository.findAddressBooksByName(addressBookName);

        addressBook.ifPresent(a -> {
            if (addressBookEntriesRepository.findAddressBookEntriesByNameAndAddressBookId(personName,
                    a.getId()).isPresent()) {
                throw new AddressBookNameAlreadyExists("Addressbook entry for" +
                        " " +
                        "same " +
                        "name already exists, " +
                        "choose a different name: " + personName);
            }
        });
    }

    private String generateUniqueAddressBookId(String addressBookName,
                                               String personName) {
        return personName
                .toUpperCase()
                .substring(0, 3) + "_"
                + addressBookName
                .toUpperCase()
                .substring(0, 3) + "_"
                + UUID.randomUUID().toString();
    }


}
