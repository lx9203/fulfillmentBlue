use fulfillmentsystem;
CREATE TABLE IF NOT EXISTS product (
	pCode VARCHAR(10) NOT NULL,
	pName VARCHAR(10) NOT NULL,
	pPrice VARCHAR(10) NOT NULL,
	pQuantity VARCHAR(10),
	pImgSource VARCHAR(80) NOT NULL,
	PRIMARY KEY (pCode)
)CHARSET=utf8;