drop table if exists Address_Book;
CREATE TABLE Address_Book (
    id   VARCHAR(60)      NOT NULL,
    name VARCHAR(120) NOT NULL,
    PRIMARY KEY (id)
);

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
                        PRIMARY KEY (addressbookid)
);


insert into Address_Book_User (username,addressbookid) values ('BOB','BOB_FAM');
insert into Address_Book_User (username,addressbookid) values ('BOB','BOB_FRI');
insert into Address_Book_User (username,addressbookid) values ('ALICE','ALICE_FAM');

insert into Address_Book values ('BOB_FRI','FRIENDS');
insert into Address_Book values ('BOB_FAM','FAMILY');
insert into Address_Book values ('ALICE_FAM','ALICE FAMILY');

insert into Address_Book_Entries (addressbookid,name,phone) values ('BOB_FRI','BOBFRIEND1','+61401440210');
insert into Address_Book_Entries (addressbookid,name,phone) values ('BOB_FRI','BOBFRIEND2','+61401440210');
insert into Address_Book_Entries (addressbookid,name,phone) values ('BOB_FRI','BOBFRIEND3','+61401440210');

insert into Address_Book_Entries (addressbookid,name,phone) values ('BOB_FAM','BOBFRIEND1','+91401440210');
insert into Address_Book_Entries (addressbookid,name,phone) values ('BOB_FAM','BOBFRIEND2','+61400000000');
insert into Address_Book_Entries (addressbookid,name,phone) values ('BOB_FAM','BOBFAMILY1','+61400000000');
insert into Address_Book_Entries (addressbookid,name,phone) values ('BOB_FAM','BOBFAMILY2','+61400000000');
insert into Address_Book_Entries (addressbookid,name,phone) values ('ALICE_FAM','ALICEFAMILY','+61411111111');
