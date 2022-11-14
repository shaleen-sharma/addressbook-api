package au.com.wrkr.addressbook.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "Address_Book")
@Data
public class AddressBook {

    @Id
    @Column
    private String id;
    private String name;
    @JsonManagedReference
    @OneToMany(mappedBy = "addressBook", orphanRemoval = true)
    private List<AddressBookEntries> addressBookEntries;
}
