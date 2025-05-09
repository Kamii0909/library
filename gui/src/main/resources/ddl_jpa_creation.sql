create table Author (id bigint not null, age integer not null, name varchar(255), primary key (id));
create table Author_SEQ (next_val bigint);
insert into Author_SEQ values ( 1 );
create table Book (id bigint not null, name varchar(255), releasedYear integer not null, reimburseCost float not null, stock integer not null, primary key (id));
create table Book_Author (books_id bigint not null, authors_id bigint not null, primary key (books_id, authors_id));
create table Book_BookGenre (Book_id bigint not null, bookGenres_name varchar(255) not null, primary key (Book_id, bookGenres_name));
create table Book_SEQ (next_val bigint);
insert into Book_SEQ values ( 1 );
create table BookGenre (name varchar(255) not null, primary key (name));
create table Client (id bigint not null, address varchar(255), name varchar(255), clientTier varchar(255), endDate varchar(255), startDate varchar(255), primary key (id));
create table Client_SEQ (next_val bigint);
insert into Client_SEQ values ( 1 );
create table LibraryEmployee (username varchar(255) not null, employeeName varchar(255), encryptedPassword blob, salt blob, primary key (username));
create table LibraryEmployee_roles (LibraryEmployee_username varchar(255) not null, roles varchar(255));
create table Ticket (DTYPE varchar(31) not null, id bigint not null, startDate varchar(255), endDate varchar(255), book_id bigint, client_id bigint, primary key (id));
create table Ticket_SEQ (next_val bigint);
insert into Ticket_SEQ values ( 1 );
