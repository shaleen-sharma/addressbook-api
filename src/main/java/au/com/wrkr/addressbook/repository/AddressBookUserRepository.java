package au.com.wrkr.addressbook.repository;

import au.com.wrkr.addressbook.model.AddressBookUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AddressBookUserRepository extends JpaRepository<AddressBookUser, String> {

   String queryinsertAddressBookUser = "insert into Address_Book_User (username,addressbookid,addressbookname) " +
           "values (:username, :addressbookid, :addressbookname)";

   Optional<AddressBookUser> findByUserNameAndAddressBookName(String userName, String addressBookName);

   @Modifying
   @Query(value = queryinsertAddressBookUser, nativeQuery = true)
   void insertAddressBookUser(@Param("username") String userName,
                          @Param("addressbookname") String addressbookname,
                          @Param("addressbookid") String addressbookid);

}

