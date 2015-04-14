
--CREATION OF TABLES
CREATE TABLE Capteur_GPS(capteurID INT PRIMARY KEY, modele VARCHAR(40) , fabricant VARCHAR(40), precisionGPS VARCHAR(10), dateDebut TIMESTAMP , dateFin TIMESTAMP);

CREATE TABLE EntiteMobile(entiteID INT PRIMARY KEY, nom VARCHAR(40), capteurID INT);

CREATE TABLE Vivant(entiteID INT PRIMARY KEY, dateNaissance TIMESTAMP, dateDeces TIMESTAMP, espece VARCHAR(20));

CREATE TABLE Artificiel(entiteID INT PRIMARY KEY,  marque VARCHAR(20), modele VARCHAR(20), anneeFabrication INT, puissance VARCHAR(20), combustible VARCHAR(20), typeMachine VARCHAR(20));

CREATE TABLE DB_GPS(capteurID INT, sampleDate TIMESTAMP, latitude NUMBER(11, 9), longitude NUMBER(12,9), CHECK(latitude >= -90), CHECK( latitude <= 90),  CHECK(longitude >= -180), CHECK(longitude<=180));


CREATE TABLE Infos_Scientifique(capteurID INT, sampleDate TIMESTAMP, temperatureEnv INT, vent INT, directionVent VARCHAR(5), pressionAtm INT, houle INT, altitude INT, vitesse INT);

CREATE TABLE Infos_Scientifique_Vivant(capteurID INT, sampleDate TIMESTAMP, pouls INT, pressionArterielle INT, pourcentageGras NUMBER(4,2), temperatureCorps INT);


CREATE TABLE Particulier(particulierID INT PRIMARY KEY, nom VARCHAR(40), type VARCHAR(20), username VARCHAR(20), password VARCHAR(20), streetNumber varchar(10), streetName VARCHAR(50), city VARCHAR(30), provState VARCHAR(30), country VARCHAR(30), postalCodeZip VARCHAR(7), tel VARCHAR(20));


CREATE TABLE Adopte(entiteID INT, particulierID INT, prix INT, debutLocation TIMESTAMP, finLocation TIMESTAMP, interet VARCHAR(40));


ALTER TABLE EntiteMobile Add CONSTRAINT CAPTID_FK_EM FOREIGN KEY(capteurID) REFERENCES Capteur_GPS(capteurID);

ALTER TABLE Vivant ADD CONSTRAINT ENTID_FK_VIV FOREIGN KEY(entiteID) REFERENCES EntiteMobile(entiteID);

ALTER TABLE Artificiel ADD CONSTRAINT ENTID_FK_ART FOREIGN KEY(entiteID) REFERENCES EntiteMobile(entiteID);

ALTER TABLE DB_GPS ADD CONSTRAINT CAPTID_FK_DBG FOREIGN KEY(capteurID) REFERENCES Capteur_GPS(capteurID);

ALTER TABLE DB_GPS ADD CONSTRAINT CAPTID_DATE_PK_DBG PRIMARY KEY(capteurID, sampleDate);

ALTER TABLE Infos_Scientifique ADD CONSTRAINT CAPTID_DATE_FK_INFS FOREIGN KEY(capteurID, sampleDate) REFERENCES DB_GPS(capteurID, sampleDate);

ALTER TABLE Infos_Scientifique ADD CONSTRAINT CAPTID_DATE_PK_INFS PRIMARY KEY (capteurID, sampleDate);

ALTER TABLE Infos_Scientifique_Vivant ADD CONSTRAINT CAPTID_DATE_FK_INFSV FOREIGN KEY(capteurID, sampleDate) REFERENCES DB_GPS(capteurID, sampleDate);

ALTER TABLE Infos_Scientifique_Vivant ADD CONSTRAINT CAPTID_DATE_PK_INFSV PRIMARY KEY (capteurID, sampleDate);

ALTER TABLE Adopte ADD CONSTRAINT ENTID_FK_AD FOREIGN KEY(entiteID) REFERENCES EntiteMobile(entiteID);
ALTER TABLE Adopte ADD CONSTRAINT PARTID_FK_AD FOREIGN KEY(particulierID) REFERENCES Particulier(particulierID);

ALTER TABLE Adopte ADD CONSTRAINT ENTID_PARTID_PK_AD PRIMARY KEY(entiteID, particulierID);