/* Delete the tables if they already exist */
drop table if exists Users;
drop table if exists Billing;
drop table if exists Blocking;
drop table if exists Forwarding;


/* Create the schema for our tables */
create table Users(userId int AUTO_INCREMENT,username text NOT NULL, email text, password text, creditCard int, plan text, 
	PRIMARY KEY (userId)) ENGINE=INNODB;



create table Forwarding(ForwardFrom text NOT NULL, ForwardTo text NOT NULL) ENGINE=INNODB;

create table Blocking(blockedFrom text NOT NULL, blocked text NOT NULL) ENGINE=INNODB;

create table Billing(id int AUTO_INCREMENT,caller text NOT NULL, callee text NOT NULL,start_time  BIGINT, duration  BIGINT,
	cost TEXT,
    PRIMARY KEY(id)) ENGINE=INNODB;


