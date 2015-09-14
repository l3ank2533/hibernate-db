
    alter table EMPLOYEE 
        drop 
        foreign key FK_tkrpjy48htun8k01qpsyy5hco;

    drop table if exists DEPARTMENT;

    drop table if exists EMPLOYEE;

    create table DEPARTMENT (
        ID integer not null auto_increment,
        NAME varchar(255),
        PHONE_NUMBER varchar(255),
        primary key (ID)
    );

    create table EMPLOYEE (
        ID integer not null auto_increment,
        NAME varchar(255),
        CREATE_TIME date,
        EMAIL varchar(255),
        DEPARTMENT_ID integer,
        primary key (ID)
    );

    alter table EMPLOYEE 
        add constraint FK_tkrpjy48htun8k01qpsyy5hco 
        foreign key (DEPARTMENT_ID) 
        references DEPARTMENT (ID);
