--FLORIN ONCICA
--GIANCARLO RIZZI
SET PAGESIZE 50
SET LINESIZE  260


ALTER TABLE EntiteMobile DROP CONSTRAINT CAPTID_FK_EM;
ALTER TABLE Vivant DROP CONSTRAINT ENTID_FK_VIV;
ALTER TABLE Artificiel DROP CONSTRAINT ENTID_FK_ART;


ALTER TABLE Infos_Scientifique DROP CONSTRAINT CAPTID_DATE_FK_INFS;
ALTER TABLE Infos_Scientifique DROP CONSTRAINT CAPTID_DATE_PK_INFS;

ALTER TABLE Infos_Scientifique_Vivant DROP CONSTRAINT CAPTID_DATE_FK_INFSV;
ALTER TABLE Infos_Scientifique_Vivant DROP CONSTRAINT CAPTID_DATE_PK_INFSV;

ALTER TABLE Adopte DROP CONSTRAINT ENTID_FK_AD;
ALTER TABLE Adopte DROP CONSTRAINT PARTID_FK_AD;
ALTER TABLE Adopte DROP CONSTRAINT ENTID_PARTID_PK_AD;


ALTER TABLE DB_GPS DROP CONSTRAINT CAPTID_FK_DBG;
ALTER TABLE DB_GPS DROP CONSTRAINT CAPTID_DATE_PK_DBG;

DROP TABLE Capteur_GPS;
DROP TABLE EntiteMobile;
DROP TABLE Vivant;
DROP TABLE Artificiel;
DROP TABLE DB_GPS;
DROP TABLE Infos_Scientifique;
DROP TABLE Infos_Scientifique_Vivant;
DROP TABLE Particulier;
DROP TABLE Adopte;


--CREATION OF TABLES
CREATE TABLE Capteur_GPS(capteurID INT PRIMARY KEY, modele VARCHAR(40) , fabricant VARCHAR(40), precisionGPS VARCHAR(10), dateDebut TIMESTAMP , dateFin TIMESTAMP);

CREATE TABLE EntiteMobile(entiteID INT PRIMARY KEY, nom VARCHAR(40), capteurID INT);

CREATE TABLE Vivant(entiteID INT PRIMARY KEY, dateNaissance TIMESTAMP, dateDeces TIMESTAMP, espece VARCHAR(20));

CREATE TABLE Artificiel(entiteID INT PRIMARY KEY, marque VARCHAR(20), anneFabrication INT, puissance VARCHAR(20), combustible VARCHAR(20));

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


Insert into Capteur_GPS values (100, 'XYZ200', 'Garmin', '3', to_date('07/03/2011 02/07/09', 'DD/MM/YYYY HH24/MI/SS'), to_date('07/03/2013 02/07/09', 'DD/MM/YYYY HH24/MI/SS'));
Insert into Capteur_GPS values (101, 'Predator', 'Garmin', '5', to_date('07/03/2011 02/07/09', 'DD/MM/YYYY HH24/MI/SS'), to_date('07/03/2013 02/07/09', 'DD/MM/YYYY HH24/MI/SS'));
Insert into Capteur_GPS values (102, 'Nuvi 55', 'Garmin', '3', to_date('07/03/2011 02/07/09', 'DD/MM/YYYY HH24/MI/SS'), to_date('07/03/2014 02/07/09', 'DD/MM/YYYY HH24/MI/SS'));
Insert into Capteur_GPS values (103, 'Gazelle', 'Garmin', '3', to_date('07/03/2011 02/07/09', 'DD/MM/YYYY HH24/MI/SS'), to_date('07/03/2014 02/07/09', 'DD/MM/YYYY HH24/MI/SS'));
Insert into Capteur_GPS values (104, 'SeaSnake', 'Garmin', '3', to_date('07/03/2012 02/07/09', 'DD/MM/YYYY HH24/MI/SS'), to_date('07/03/2015 02/07/09', 'DD/MM/YYYY HH24/MI/SS'));
Insert into Capteur_GPS values (105, 'Go600', 'TomTom', '3', to_date('07/03/2012 02/07/09', 'DD/MM/YYYY HH24/MI/SS'), to_date('07/03/2013 02/07/09', 'DD/MM/YYYY HH24/MI/SS'));
Insert into Capteur_GPS values (106, 'Cycle', 'Garmin', '9', to_date('07/03/2012 02/07/09', 'DD/MM/YYYY HH24/MI/SS'), to_date('07/03/2014 02/07/09', 'DD/MM/YYYY HH24/MI/SS'));
Insert into Capteur_GPS values (107, 'Tornado', 'Garmin', '3', to_date('07/03/2012 02/07/09', 'DD/MM/YYYY HH24/MI/SS'), to_date('07/03/2014 02/07/09', 'DD/MM/YYYY HH24/MI/SS'));
Insert into Capteur_GPS values (108, 'Hurricane', 'Garmin', '3', to_date('07/03/2013 02/07/09', 'DD/MM/YYYY HH24/MI/SS'), to_date('07/03/2014 02/07/09', 'DD/MM/YYYY HH24/MI/SS'));
Insert into Capteur_GPS (capteurID, modele, fabricant, precisionGPS, dateDebut) values (109, 'VIA 1535TM', 'TomTom', '3', to_date('07/03/2013 02/07/09', 'DD/MM/YYYY HH24/MI/SS'));
Insert into Capteur_GPS values (110, 'VIA 1605TM', 'TomTom', '3', to_date('07/03/2013 02/07/09', 'DD/MM/YYYY HH24/MI/SS'), to_date('07/03/2014 02/07/09', 'DD/MM/YYYY HH24/MI/SS'));
Insert into Capteur_GPS values (111, 'Nuvi 3597LMTHD', 'Garmin', '14', to_date('07/03/2013 02/07/09', 'DD/MM/YYYY HH24/MI/SS'), to_date('07/03/2014 02/07/09', 'DD/MM/YYYY HH24/MI/SS'));
Insert into Capteur_GPS values (112, 'Wallet U14', 'TrackR', '11', to_date('07/03/2013 02/07/09', 'DD/MM/YYYY HH24/MI/SS'), to_date('07/03/2015 02/07/09', 'DD/MM/YYYY HH24/MI/SS'));
Insert into Capteur_GPS values (113, 'Lola', 'Laipac', '4', to_date('07/03/2014 02/07/09', 'DD/MM/YYYY HH24/MI/SS'), to_date('07/03/2015 02/07/09', 'DD/MM/YYYY HH24/MI/SS'));
Insert into Capteur_GPS (capteurID, modele, fabricant, precisionGPS, dateDebut) values (114, 'Sticker', 'TrackR', '6', to_date('07/03/2014 02/07/09', 'DD/MM/YYYY HH24/MI/SS'));
Insert into Capteur_GPS (capteurID, modele, fabricant, precisionGPS, dateDebut) values (115, 'Entourae PS', 'Escort', '4', to_date('07/03/2014 02/07/09', 'DD/MM/YYYY HH24/MI/SS'));
Insert into Capteur_GPS (capteurID, modele, fabricant, precisionGPS, dateDebut) values (116, 'Bracelet HC-ST', 'Laipac', '11', to_date('07/03/2014 02/07/09', 'DD/MM/YYYY HH24/MI/SS'));
Insert into Capteur_GPS (capteurID, modele, fabricant, precisionGPS, dateDebut) values  (117, 'Entourage CIS', 'Escort', '4', to_date('07/03/2014 02/07/09', 'DD/MM/YYYY HH24/MI/SS'));
Insert into Capteur_GPS (capteurID, modele, fabricant, precisionGPS, dateDebut) values (118, 'StarFinder', 'Laipac', '1', to_date('07/03/2014 02/07/09', 'DD/MM/YYYY HH24/MI/SS'));


Insert into DB_GPS values (100, to_date('22/10/86/11/12/13',  'DD/MM/YYYY   HH24/MI/SS'), 12.345678, -179.765432);


Insert into Particulier values (1, 'Universite de Montreal', 'Ecole', 'udemuser', 'udempass', '2900', 'Edouard Montpetit Blvd', 'Montreal', 'Quebec', 'Canada', 'H3T 1J4', '514-343-6111');
Insert into Particulier values (2, 'McGill University', 'Ecole', 'mcgilluser', 'mcgillpass', '845', 'Sherbrooke West', 'Montreal', 'Quebec', 'Canada', 'H3A 0G4', '514-398-4455');
Insert into Particulier values (3, 'Concordia University', 'Ecole', 'concordiauser', 'concordiapass', '7141', 'Sherbrooke West', 'Montreal', 'Quebec', 'Canada', 'H4B 1R6', '514-848-2424');
Insert into Particulier values (4, 'UQAM', 'Ecole', 'uqamuser', 'uqampass', '405', 'Sainte Catherine East', 'Montreal', 'Quebec', 'Canada', 'H2L 2C4', '514-987-3000');
Insert into Particulier values (5, 'Universite Sherbrooke', 'Ecole', 'sherbrookeuser', 'sherbrookepass', '2500', 'de lUniversite', 'Sherbrooke', 'Quebec', 'Canada', 'J1K 2R1', '819-821-7000');
Insert into Particulier values (6, 'Department of Defence Canada', 'Gouvernement', 'dnduser', 'dndpass', '101', 'Colonel By Drive', 'Ottawa', 'Ontario', 'Canada', 'K1A 0K7', '613-992-4582');
Insert into Particulier values (7, 'Google Inc', 'Enterprise', 'googleuser', 'googlepass', '1600', 'Amphitheatre Parkway', 'Mountain View', 'California', 'USA', '94043', '650-253-0000');
Insert into Particulier values (8, 'Research In Motion Inc.', 'Enterprise', 'rimuser', 'rimpass', '2200', 'University East', 'Waterloo', 'Ontario', 'Canada', 'N2K 0A7', '519-888-7465');
