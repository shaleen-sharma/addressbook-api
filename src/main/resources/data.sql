-- drop table if exists Address_Book;
-- CREATE TABLE Address_Book (
--     id   VARCHAR(60)      NOT NULL,
--     name VARCHAR(120) NOT NULL,
--     PRIMARY KEY (id)
-- );

drop table if exists Address_Book_Entries;
CREATE TABLE Address_Book_Entries (
    id INTEGER NOT NULL AUTO_INCREMENT,
    addressbookid   VARCHAR(60)      NOT NULL,
    name VARCHAR(120) NOT NULL,
    phone VARCHAR(50) NOT NULL
);

drop table if exists Address_Book_User;
CREATE TABLE Address_Book_User (
                        username   VARCHAR(120)     NOT NULL,
                        addressbookid VARCHAR(60) NOT NULL,
                        addressbookname VARCHAR(60) NOT NULL,
                        PRIMARY KEY (addressbookid)
);


insert into Address_Book_User (username,addressbookid, addressbookname) values ('BOB','BOB_FAM_12hghg2', 'FAMILY');
insert into Address_Book_User (username,addressbookid, addressbookname) values ('BOB','BOB_FRI_675jhga', 'FRIENDS');
insert into Address_Book_User (username,addressbookid, addressbookname) values ('ALICE','ALICE_FAM_786jhnbs', 'FAMILY');

-- insert into Address_Book values ('BOB_FRI','FRIENDS');
-- insert into Address_Book values ('BOB_FAM','FAMILY');
-- insert into Address_Book values ('ALICE_FAM','ALICE FAMILY');

insert into Address_Book_Entries (addressbookid,name,phone) values ('BOB_FRI_675jhga','BOB1','+61401440210');
insert into Address_Book_Entries (addressbookid,name,phone) values ('BOB_FRI_675jhga','BOB2','+61401440210');
insert into Address_Book_Entries (addressbookid,name,phone) values ('BOB_FRI_675jhga','BOB3','+61401440210');

insert into Address_Book_Entries (addressbookid,name,phone) values ('BOB_FAM_12hghg2','BOB1','+91401440210');
insert into Address_Book_Entries (addressbookid,name,phone) values ('BOB_FAM_12hghg2','BOB2','+61400000000');
insert into Address_Book_Entries (addressbookid,name,phone) values ('BOB_FAM_12hghg2','BOB4','+61400000000');
insert into Address_Book_Entries (addressbookid,name,phone) values ('BOB_FAM_12hghg2','BOB5','+61400000000');
insert into Address_Book_Entries (addressbookid,name,phone) values ('ALICE_FAM','ALICEFAMILY','+61411111111');
