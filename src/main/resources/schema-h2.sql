create table accounts (
    "ID"      integer not null,
    "NUMBER"  integer,
    "NAME"    varchar(255),
    "BALANCE" double,
    PRIMARY KEY ("ID")
);

CREATE SEQUENCE "ACCOUNT_SEQ"
MINVALUE 1
MAXVALUE 2147483647
INCREMENT BY 1
START WITH 202700
NOCACHE
NOCYCLE;