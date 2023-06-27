USE RDB2023_MartinKonak
GO
-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2023-05-16 16:37:42.767

-- models
-- Table: Automaticke_mereni
CREATE TABLE Automaticke_mereni (
    misto nvarchar(50)  NOT NULL,
    od int  NOT NULL,
    do int  NOT NULL,
    regnum nvarchar(50)  NOT NULL,
    CONSTRAINT Automaticke_mereni_pk PRIMARY KEY  (misto,od,do)
);

-- Table: Manualni_mereni
CREATE TABLE Manualni_mereni (
    misto nvarchar(50)  NOT NULL,
    od int  NOT NULL,
    do int  NOT NULL,
    osoba_email nvarchar(50)  NOT NULL,
    nazev_oddeleni nvarchar(50)  NOT NULL,
    CONSTRAINT Manualni_mereni_pk PRIMARY KEY  (misto,od,do)
);

-- Table: Mereni
CREATE TABLE Mereni (
    misto nvarchar(50)  NOT NULL,
    od int  NOT NULL,
    do int  NOT NULL,
    CONSTRAINT Mereni_pk PRIMARY KEY  (misto,od,do)
);

-- Table: Oddeleni
CREATE TABLE Oddeleni (
    nazev nvarchar(50)  NOT NULL,
    ulice nvarchar(50)  NOT NULL,
    cislo_popisne nvarchar(50)  NOT NULL,
    mesto nvarchar(50)  NOT NULL,
    CONSTRAINT Oddeleni_pk PRIMARY KEY  (nazev)
);

-- Table: Oddeleni_Telefon
CREATE TABLE Oddeleni_Telefon (
    nazev nvarchar(50)  NOT NULL,
    cislo nvarchar(9)  NOT NULL,
    CONSTRAINT Oddeleni_Telefon_pk PRIMARY KEY  (nazev,cislo)
);

-- Table: Osoba
CREATE TABLE Osoba (
    jmeno nvarchar(50)  NOT NULL,
    prijmeni nvarchar(50)  NOT NULL,
    email nvarchar(50)  NOT NULL,
    CONSTRAINT Osoba_pk PRIMARY KEY  (email)
);

-- Table: Telefon
CREATE TABLE Telefon (
    cislo nvarchar(9)  NOT NULL,
    CONSTRAINT Telefon_pk PRIMARY KEY  (cislo)
);

-- Table: Zarizeni
CREATE TABLE Zarizeni (
    title nvarchar(50)  NOT NULL,
    delta int  NOT NULL,
    manufacturer nvarchar(50)  NOT NULL,
    voltage nvarchar(50)  NOT NULL,
    regnum nvarchar(50)  NOT NULL,
    CONSTRAINT Zarizeni_pk PRIMARY KEY  (regnum)
);

-- foreign keys
-- Reference: Automaticke_mereni_Mereni (table: Automaticke_mereni)
ALTER TABLE Automaticke_mereni ADD CONSTRAINT Automaticke_mereni_Mereni
    FOREIGN KEY (misto,od,do)
    REFERENCES Mereni (misto,od,do)
    ON DELETE  CASCADE 
    ON UPDATE  CASCADE;

-- Reference: Automaticke_mereni_Zarizeni (table: Automaticke_mereni)
ALTER TABLE Automaticke_mereni ADD CONSTRAINT Automaticke_mereni_Zarizeni
    FOREIGN KEY (regnum)
    REFERENCES Zarizeni (regnum)
    ON DELETE  CASCADE 
    ON UPDATE  CASCADE;

-- Reference: Manualni_mereni_Department (table: Manualni_mereni)
ALTER TABLE Manualni_mereni ADD CONSTRAINT Manualni_mereni_Department
    FOREIGN KEY (nazev_oddeleni)
    REFERENCES Oddeleni (nazev)
    ON DELETE  CASCADE 
    ON UPDATE  CASCADE;

-- Reference: Manualni_mereni_Mereni (table: Manualni_mereni)
ALTER TABLE Manualni_mereni ADD CONSTRAINT Manualni_mereni_Mereni
    FOREIGN KEY (misto,od,do)
    REFERENCES Mereni (misto,od,do)
    ON DELETE  CASCADE 
    ON UPDATE  CASCADE;

-- Reference: Manualni_mereni_Osoba (table: Manualni_mereni)
ALTER TABLE Manualni_mereni ADD CONSTRAINT Manualni_mereni_Osoba
    FOREIGN KEY (osoba_email)
    REFERENCES Osoba (email)
    ON DELETE  CASCADE 
    ON UPDATE  CASCADE;

-- Reference: Oddeleni_Telefon_Oddeleni (table: Oddeleni_Telefon)
ALTER TABLE Oddeleni_Telefon ADD CONSTRAINT Oddeleni_Telefon_Oddeleni
    FOREIGN KEY (nazev)
    REFERENCES Oddeleni (nazev)
    ON DELETE  CASCADE 
    ON UPDATE  CASCADE;

-- Reference: Oddeleni_Telefon_Telefon (table: Oddeleni_Telefon)
ALTER TABLE Oddeleni_Telefon ADD CONSTRAINT Oddeleni_Telefon_Telefon
    FOREIGN KEY (cislo)
    REFERENCES Telefon (cislo)
    ON DELETE  CASCADE 
    ON UPDATE  CASCADE;

-- End of file.

