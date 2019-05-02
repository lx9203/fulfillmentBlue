use fulfillmentsystem;
CREATE TABLE IF NOT EXISTS user (
	id VARCHAR(10) NOT NULL,
	user_type INT(4),
	name VARCHAR(10) NOT NULL,
	password VARCHAR(10) NOT NULL,
	hashed VARCHAR(256),
	PRIMARY KEY (id)
)CHARSET=utf8;

#관리자 생성, 비밀번호 :java
insert into user values('admin',0,'관리자','****','$2a$10$IzyTY.1tk053V3dDJJPYY.Rt5PaoZzOWxjsW.Y78.P6HKaPNW2hXe');

CREATE TABLE IF NOT EXISTS product (
	pCode VARCHAR(10) NOT NULL,
	pName VARCHAR(10) NOT NULL,
	pPrice VARCHAR(10) NOT NULL,
	pQuantity VARCHAR(10),
	pImgSource VARCHAR(80) NOT NULL,
	PRIMARY KEY (pCode)
)CHARSET=utf8;

CREATE TABLE IF NOT EXISTS customer (
	cCode VARCHAR(10) NOT NULL,
	cName VARCHAR(10) NOT NULL,
	cTel VARCHAR(20) NOT NULL,
	cAddress VARCHAR(50) NOT NULL,
	cTotalPrice INT(8) NOT NULL,
	PRIMARY KEY (cCode)
)CHARSET=utf8;

CREATE TABLE IF NOT EXISTS invoice (
	iCode VARCHAR(10) NOT NULL,
	iName VARCHAR(10) NOT NULL,
	iTel VARCHAR(20),
	iAddress  VARCHAR(50) NOT NULL,
	iAreaCode INT(4) NOT NULL,
	iDate DATE NOT NULL,
	PRIMARY KEY (iCode)
)CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `order` (
	oCode VARCHAR(13) NOT NULL,
	oProductCode VARCHAR(10) NOT NULL,
	oQuantity INT(8) NOT NULL,
	oInvoiceCode VARCHAR(13) NOT NULL,
	oTotalPrice INT(8) NOT NULL,
	PRIMARY KEY (oCode),
	FOREIGN KEY (oProductCode) REFERENCES product(pCode),
	FOREIGN KEY (oInvoiceCode) REFERENCES invoice(iCode)
)CHARSET=utf8;