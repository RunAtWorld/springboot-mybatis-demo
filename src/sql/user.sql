create database testdb default character set utf8 collate utf8_general_ci;

CREATE TABLE t_user(
  userId INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  userName VARCHAR(255) NOT NULL ,
  password VARCHAR(255) NOT NULL ,
  phone VARCHAR(255) NOT NULL
) ENGINE=INNODB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8;


insert into t_user (userName,password,phone)values ('li','23123','1322');