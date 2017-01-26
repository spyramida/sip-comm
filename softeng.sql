/* Delete the tables if they already exist */
drop table if exists Users;
drop table if exists Billing;
drop table if exists Blocking;
drop table if exists Forward;


/* Create the schema for our tables */
create table Users(userId int NOT NULL AUTO_INCREMENT, username text NOT NULL, email text, password text, creditCard int, plan text, 
	PRIMARY KEY (userId)) ENGINE=INNODB;

/*
create table (LId int NOT NULL, Name text, Town text, StreetName text, StreetNumber int, PostalCode int, 
	PRIMARY KEY (LId)) ENGINE=INNODB;

create table Intermediary(MId int NOT NULL, Name text, Town text, StreetName text, StreetNumber int, PostalCode int, 
	PRIMARY KEY (MId)) ENGINE=INNODB;

create table LoanRequest(BId int NOT NULL, DateOfRequest date NOT NULL, Deadline date, Amount int, PaybackPeriod int, Description text, 
Percentage float,
  PRIMARY KEY(BId,DateOfRequest),
  FOREIGN KEY (BId) REFERENCES Borrower(BId) ON DELETE CASCADE) ENGINE=INNODB;
*/
