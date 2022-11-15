package au.com.wrkr.addressbook.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "Address_Book_User")
@Data
public class AddressBookUser implements Serializable {

    @Id
    @Column(name = "username")
    private String userName;
    private String addressbookid;
    @Column(name = "addressbookname")
    private String addressBookName;
    @JsonManagedReference
    @OneToMany(mappedBy = "addressBookUser", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AddressBookEntries> addressBookEntries;
}
