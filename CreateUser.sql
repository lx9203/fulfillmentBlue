use fulfillmentsystem;
CREATE TABLE IF NOT EXISTS user (
	id VARCHAR(10) NOT NULL,
	user_type INT(4),
	name VARCHAR(10) NOT NULL,
	password VARCHAR(10) NOT NULL,
	hashed VARCHAR(256),
	PRIMARY KEY (id)
)CHARSET=utf8;

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
	cTel VARCHAR(10) NOT NULL,
	cAddress VARCHAR(50) NOT NULL,
	cAreaCode VARCHAR(10) NOT NULL,
	cDate DATE NOT NULL,
	PRIMARY KEY(cCode)
	)CHARSET=utf8;
	
CREATE TABLE IF NOT EXISTS invoiceproduct (
	no int(8) NOT NULL auto_increment,
	iCustomerCode VARCHAR(10) NOT NULL, 
	iProductCode VARCHAR(10) NOT NULL,
	iQuantity int(4) NOT NULL,
	
	PRIMARY KEY (no),
	FOREIGN KEY (iCustomerCode)
   REFERENCES customer(cCode) ON UPDATE CASCADE,
   FOREIGN KEY (iProductCode)
   REFERENCES product(pCode) ON UPDATE CASCADE
   
)auto_increment=10001,CHARSET=utf8;