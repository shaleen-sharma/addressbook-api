package au.com.wrkr.addressbook.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity(name = "Address_Book_Entries")
@Table(name = "Address_Book_Entries")
@Data
public class AddressBookEntries {
    @Id
    private int id;
    private String addressbookid;
    private String name;
    private String phone;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="addressbookid",referencedColumnName = "addressbookid"
            ,insertable = false, updatable = false)
    private AddressBookUser addressBookUser;
}
