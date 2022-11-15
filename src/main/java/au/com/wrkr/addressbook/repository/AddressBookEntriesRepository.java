package au.com.wrkr.addressbook.repository;

import au.com.wrkr.addressbook.model.AddressBookEntries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface AddressBookEntriesRepository extends JpaRepository<AddressBookEntries, String> {

    String queryAddressBookEntriesByUser = "SELECT abe FROM " +
            "Address_Book_Entries abe " +
            "inner join abe.addressBookUser abu on abe.addressbookid = abu.addressbookid " +
            "where abu.userName = :username and (abu.addressBookName = :addressbookname or " +
            ":addressbookname is null) order by abe.name";

    String queryinsertAddressBookEntries = "insert into Address_Book_Entries (addressbookid,name,phone) " +
            "values (:addressbookid, :name, :phone)";


    @Query(queryAddressBookEntriesByUser)
    List<AddressBookEntries> getAddressBookEntriesByUser(@Param("username") String username,
                                                         @Param("addressbookname") String addressbookname);

    @Modifying
    @Query(value = queryinsertAddressBookEntries, nativeQuery = true)
    void insertAddressBookEntries(@Param("addressbookid") String addressbookid,
                     @Param("name") String name,
                     @Param("phone") String phone);

    Optional<AddressBookEntries> findAddressBookEntriesByNameAndAddressbookid(String name, String addressBookId);
}

