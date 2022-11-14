package au.com.wrkr.addressbook.repository;

import au.com.wrkr.addressbook.model.AddressBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AddressBookRepository extends JpaRepository<AddressBook, String> {

    Optional<AddressBook> findAddressBooksByName(String addressBookName);
}

