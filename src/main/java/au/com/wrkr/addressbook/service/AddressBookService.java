package au.com.wrkr.addressbook.service;

import au.com.wrkr.addressbook.exception.AddressBookNameAlreadyExists;
import au.com.wrkr.addressbook.exception.NoAddressBookFoundException;
import au.com.wrkr.addressbook.model.AddressBookEntries;
import au.com.wrkr.addressbook.model.AddressBookUser;
import au.com.wrkr.addressbook.repository.AddressBookEntriesRepository;
import au.com.wrkr.addressbook.repository.AddressBookUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
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
    public AddressBookServiceImpl(final AddressBookEntriesRepository addressBookEntriesRepository
    ) {
        this.addressBookEntriesRepository = addressBookEntriesRepository;
    }

    /**
     * Returns Address Book Entries for a given user and addressbook name
     * @param userName
     * @param addressBookName
     * @return
     */
    @Override
    public List<AddressBookEntries> getAddressBookEntries(String userName,
                                                          String addressBookName) {
        //Throw exception if the requested addressbook doesnt exist
        checkIfGivenAddressBookDoesntExist(userName, addressBookName);
        return addressBookEntriesRepository.getAddressBookEntriesByUser(userName, addressBookName);
    }

    /**
     * Creates the entry in existing or new addressbook
     * with the name and phone
     * @param userName
     * @param addressBookName
     * @param personName
     * @param phone
     */
    @Override
    @Transactional
    public void setAddressBookEntries(String userName, String addressBookName
            , String personName, String phone) {
        //Generate unique id for address book
        String addressBookId;

        //Get addressBook details if already exists
        Optional<AddressBookUser> addressBookUser =
                addressBookUserRepository.findByUserNameAndAddressBookName(
                userName, addressBookName);

        if (addressBookUser.isPresent()) {
            //If addressBook exists then use the existing addressbookid
            addressBookId = addressBookUser.get().getAddressbookid();
            //check if there is an existing entry for same addressbook and same name
            if (addressBookEntriesRepository.findAddressBookEntriesByNameAndAddressbookid(personName,
                    addressBookUser.get().getAddressbookid()).isPresent()) {
                throw new AddressBookNameAlreadyExists("Addressbook entry for" +
                        " " +
                        "same name already exists, " +
                        "choose a different name: " + personName);
            }
        } else {
            //New addressbook being added
            addressBookId = generateUniqueAddressBookId(addressBookName,
                    personName);
            addressBookUserRepository.insertAddressBookUser(userName,
                    addressBookName,
                    addressBookId);
        }
        //Insert the addressbookentry with name and phone for addressBookId
        addressBookEntriesRepository.insertAddressBookEntries(addressBookId,
                personName, phone);
    }

    /**
     * Remove the common names and produces the unique
     * names from two addressbooks
     * @param userName
     * @param addressBookName1
     * @param addressBookName2
     * @return
     */
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
        List<AddressBookEntries> list = Stream.concat(removeCommons(list1, list2).stream(),
                removeCommons(list2, list1).stream())
                .collect(Collectors.toList());
        //Sort by name
        list.sort(Comparator.comparing(AddressBookEntries::getName));
        return list;
    }

    private List<AddressBookEntries> removeCommons(List<AddressBookEntries> list1,
                                                   List<AddressBookEntries> list2) {
        List<String> names =
                list2.stream().map(l2 -> l2.getName()).collect(Collectors.toList());
        return list1.stream()
                .filter(l1 -> !names.contains(l1.getName()))
                .collect(Collectors.toList());
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

    private void checkIfGivenAddressBookDoesntExist(String userName,
                                                    String addressBookName){
        addressBookUserRepository
                .findByUserNameAndAddressBookName(userName, addressBookName).orElseThrow(() ->
                new NoAddressBookFoundException("No such addressbook for " +
                        "user; " + userName));
    }

}
