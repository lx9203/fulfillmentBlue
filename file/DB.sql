use fulfillmentsystem;
CREATE TABLE IF NOT EXISTS user (
	id VARCHAR(10) NOT NULL,
	user_type INT(1),
	name VARCHAR(10) NOT NULL,
	password VARCHAR(10) NOT NULL,
	hashed VARCHAR(256),
	PRIMARY KEY (id)
)CHARSET=utf8;

#관리자 생성, 비밀번호 :java
insert into user values('admin',0,'관리자','****','$2a$10$IzyTY.1tk053V3dDJJPYY.Rt5PaoZzOWxjsW.Y78.P6HKaPNW2hXe');

CREATE TABLE IF NOT EXISTS product (
	pCode VARCHAR(4) NOT NULL,
	pName VARCHAR(20) NOT NULL,
	pPrice INT(4) NOT NULL,
	pQuantity INT(4),
	pImgSource VARCHAR(80) NOT NULL,
	PRIMARY KEY (pCode)
)CHARSET=utf8;

CREATE TABLE IF NOT EXISTS invoice (
	iCode VARCHAR(13) NOT NULL,
	iName VARCHAR(10) NOT NULL,
	iTel VARCHAR(20),
	iAddress  VARCHAR(50) NOT NULL,
	iAreaCode VARCHAR(10) NOT NULL,
	iDate DATETIME NOT NULL,
	iState INT(1) NOT NULL,
	PRIMARY KEY (iCode)
)CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `order` (
	oNum INT(4) NOT NULL AUTO_INCREMENT,
	oProductCode VARCHAR(4) NOT NULL,
	oQuantity INT(4) NOT NULL,
	oInvoiceCode VARCHAR(13) NOT NULL,
	PRIMARY KEY (oNum),
	FOREIGN KEY (oProductCode) REFERENCES product(pCode),
	FOREIGN KEY (oInvoiceCode) REFERENCES invoice(iCode)
)AUTO_INCREMENT=1001 CHARSET=utf8;

CREATE TABLE IF NOT EXISTS supply (
	sCode VARCHAR(10) NOT NULL,
	sProductCode VARCHAR(4) NOT NULL,
	sDate DATETIME NOT NULL,
	sQuantity INT(4) NOT NULL,
	sState INT(1) NOT NULL,
	PRIMARY KEY (sCode),
	FOREIGN KEY (sProductCode) REFERENCES product(pCode)
)CHARSET=utf8;
