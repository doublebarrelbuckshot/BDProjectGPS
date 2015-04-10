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


Insert into Capteur_GPS values (100, 'XYZ200', 'Garmin', '3', to_date('07/03/2011 02/07/09', 'DD/MM/YYYY HH24/MI/SS'), to_date('07/03/2013 02/07/09', 'DD/MM/YYYY HH24/MI/SS'));
Insert into Capteur_GPS values (101, 'Predator', 'Garmin', '5', to_date('07/05/2011 02/07/09', 'DD/MM/YYYY HH24/MI/SS'), to_date('07/03/2013 02/07/09', 'DD/MM/YYYY HH24/MI/SS'));
Insert into Capteur_GPS values (102, 'Nuvi 55', 'Garmin', '3', to_date('07/03/2011 02/07/09', 'DD/MM/YYYY HH24/MI/SS'), to_date('07/03/2014 02/07/09', 'DD/MM/YYYY HH24/MI/SS'));
Insert into Capteur_GPS values (103, 'Gazelle', 'Garmin', '3', to_date('07/03/2011 02/07/09', 'DD/MM/YYYY HH24/MI/SS'), to_date('07/03/2014 02/07/09', 'DD/MM/YYYY HH24/MI/SS'));
Insert into Capteur_GPS values (104, 'SeaSnake', 'Garmin', '3', to_date('07/03/2012 02/07/09', 'DD/MM/YYYY HH24/MI/SS'), to_date('07/03/2015 02/07/09', 'DD/MM/YYYY HH24/MI/SS'));
Insert into Capteur_GPS values (105, 'Go600', 'TomTom', '3', to_date('07/03/2012 02/07/09', 'DD/MM/YYYY HH24/MI/SS'), to_date('07/03/2013 02/07/09', 'DD/MM/YYYY HH24/MI/SS'));
Insert into Capteur_GPS values (106, 'Cycle', 'Garmin', '9', to_date('07/03/2012 02/07/09', 'DD/MM/YYYY HH24/MI/SS'), to_date('07/03/2014 02/07/09', 'DD/MM/YYYY HH24/MI/SS'));
Insert into Capteur_GPS values (107, 'Tornado', 'Garmin', '3', to_date('07/03/2012 02/07/09', 'DD/MM/YYYY HH24/MI/SS'), to_date('07/03/2014 02/07/09', 'DD/MM/YYYY HH24/MI/SS'));
Insert into Capteur_GPS values (108, 'Hurricane', 'Garmin', '3', to_date('07/03/2013 02/07/09', 'DD/MM/YYYY HH24/MI/SS'), to_date('07/03/2014 02/07/09', 'DD/MM/YYYY HH24/MI/SS'));
Insert into Capteur_GPS values (109, 'VIA 1535TM', 'TomTom', '3', to_date('07/03/2013 02/07/09', 'DD/MM/YYYY HH24/MI/SS'), to_date('07/03/2014 02/07/09', 'DD/MM/YYYY HH24/MI/SS'));
Insert into Capteur_GPS values (110, 'VIA 1605TM', 'TomTom', '3', to_date('07/03/2013 02/07/09', 'DD/MM/YYYY HH24/MI/SS'), to_date('07/03/2014 02/07/09', 'DD/MM/YYYY HH24/MI/SS'));
Insert into Capteur_GPS values (111, 'Nuvi 3597LMTHD', 'Garmin', '14', to_date('07/03/2013 02/07/09', 'DD/MM/YYYY HH24/MI/SS'), to_date('07/03/2014 02/07/09', 'DD/MM/YYYY HH24/MI/SS'));
Insert into Capteur_GPS values (112, 'Wallet U14', 'TrackR', '11', to_date('07/03/2013 02/07/09', 'DD/MM/YYYY HH24/MI/SS'), to_date('07/03/2015 02/07/09', 'DD/MM/YYYY HH24/MI/SS'));
Insert into Capteur_GPS values (113, 'Lola', 'Laipac', '4', to_date('07/03/2014 02/07/09', 'DD/MM/YYYY HH24/MI/SS'), to_date('07/03/2015 02/07/09', 'DD/MM/YYYY HH24/MI/SS'));
Insert into Capteur_GPS values (114, 'Sticker', 'TrackR', '6', to_date('07/03/2014 02/07/09', 'DD/MM/YYYY HH24/MI/SS'), to_date('07/03/2014 02/07/09', 'DD/MM/YYYY HH24/MI/SS'));
Insert into Capteur_GPS values (115, 'Entourae PS', 'Escort', '4', to_date('07/03/2014 02/07/09', 'DD/MM/YYYY HH24/MI/SS'), to_date('07/03/2014 02/07/09', 'DD/MM/YYYY HH24/MI/SS'));
Insert into Capteur_GPS values (116, 'Bracelet HC-ST', 'Laipac', '11', to_date('07/03/2014 02/07/09', 'DD/MM/YYYY HH24/MI/SS'), to_date('07/03/2014 02/07/09', 'DD/MM/YYYY HH24/MI/SS'));
Insert into Capteur_GPS values  (117, 'Entourage CIS', 'Escort', '4', to_date('07/03/2014 02/07/09', 'DD/MM/YYYY HH24/MI/SS') ,to_date('07/03/2014 02/07/09', 'DD/MM/YYYY HH24/MI/SS'));
Insert into Capteur_GPS values (118, 'StarFinder', 'Laipac', '1', to_date('07/03/2014 02/07/09', 'DD/MM/YYYY HH24/MI/SS'), to_date('07/03/2014 02/07/09', 'DD/MM/YYYY HH24/MI/SS'));


Insert into DB_GPS values (100, to_date('22/10/86/11/12/13',  'DD/MM/YYYY   HH24/MI/SS'), 12.345678, -179.765432);


Insert into Particulier values (1, 'Universite de Montreal', 'Ecole', 'udemuser', 'udempass', '2900', 'Edouard Montpetit Blvd', 'Montreal', 'Quebec', 'Canada', 'H3T 1J4', '514-343-6111');
Insert into Particulier values (2, 'McGill University', 'Ecole', 'mcgilluser', 'mcgillpass', '845', 'Sherbrooke West', 'Montreal', 'Quebec', 'Canada', 'H3A 0G4', '514-398-4455');
Insert into Particulier values (3, 'Concordia University', 'Ecole', 'concordiauser', 'concordiapass', '7141', 'Sherbrooke West', 'Montreal', 'Quebec', 'Canada', 'H4B 1R6', '514-848-2424');
Insert into Particulier values (4, 'UQAM', 'Ecole', 'uqamuser', 'uqampass', '405', 'Sainte Catherine East', 'Montreal', 'Quebec', 'Canada', 'H2L 2C4', '514-987-3000');
Insert into Particulier values (5, 'Universite Sherbrooke', 'Ecole', 'sherbrookeuser', 'sherbrookepass', '2500', 'de lUniversite', 'Sherbrooke', 'Quebec', 'Canada', 'J1K 2R1', '819-821-7000');
Insert into Particulier values (6, 'Department of Defence Canada', 'Gouvernement', 'dnduser', 'dndpass', '101', 'Colonel By Drive', 'Ottawa', 'Ontario', 'Canada', 'K1A 0K7', '613-992-4582');
Insert into Particulier values (7, 'Google Inc', 'Enterprise', 'googleuser', 'googlepass', '1600', 'Amphitheatre Parkway', 'Mountain View', 'California', 'USA', '94043', '650-253-0000');
Insert into Particulier values (8, 'Research In Motion Inc.', 'Enterprise', 'rimuser', 'rimpass', '2200', 'University East', 'Waterloo', 'Ontario', 'Canada', 'N2K 0A7', '519-888-7465');


Insert into EntiteMobile VALUES(1000,  '313WFL', 100);
Insert into EntiteMobile VALUES(1001,  '724REX', 101);
Insert into EntiteMobile VALUES(1002,  'Harry', 102);
Insert into EntiteMobile VALUES(1003,  'Wally', 103);
Insert into EntiteMobile VALUES(1004,  'Willy', 104);
Insert into EntiteMobile VALUES(1005,  'Charles', 105);
Insert into EntiteMobile VALUES(1006,  'BQM832', 106);
Insert into EntiteMobile VALUES(1007,  'Marie', 107);
Insert into EntiteMobile VALUES(1008,  'J2D883', 108);
Insert into EntiteMobile VALUES(1009,  'Oscar', 109);
Insert into EntiteMobile VALUES(1010,  'Groucho', 110);
Insert into EntiteMobile VALUES(1011,  'Tigger', 111);
Insert into EntiteMobile VALUES(1012,  '1107W', 112);
Insert into EntiteMobile VALUES(1013,  '4302NS', 113);
Insert into EntiteMobile VALUES(1014,  '5086CAN', 114);
Insert into EntiteMobile VALUES(1015,  '5087CAN', 115);
Insert into EntiteMobile VALUES(1016,  '5088CAN', 116);
Insert into EntiteMobile VALUES(1017,  'STUCAZO', 117);
Insert into EntiteMobile VALUES(1018,  'SLICE OF LIFE', 118);



--CREATE TABLE Artificiel(entiteID INT PRIMARY KEY, marque VARCHAR(20), anneeFabrication INT, puissance VARCHAR(20), combustible VARCHAR(20));

INSERT INTO Artificiel VALUES('1000', 'Toyota', 'Yaris', '2007', '1.5L', 'Essence', 'Automobile');
INSERT INTO Artificiel VALUES('1001', 'Honda', 'Civic', '2008', '1.7L', 'Essence', 'Automobile');
INSERT INTO Artificiel VALUES('1006', 'Mack', 'SE11', '2008', '4.7L', 'Diesel', 'Camion');
INSERT INTO Artificiel VALUES('1008', 'Toyota', 'Prius', '2007', '1.5L', 'Hybrid', 'Automobile');
INSERT INTO Artificiel VALUES('1012', 'Boeing', '777-200ER', '2001', '1020kN', 'Jet Fuel', 'Avion');
INSERT INTO Artificiel VALUES('1013', 'Airbus', 'A380', '2007', '800kN', 'Essence', 'Avion');
INSERT INTO Artificiel VALUES('1017', 'Princess Yachts', 'Flybridge 98', '2008', '4450mhp', 'Essence', 'Bateau');
INSERT INTO Artificiel VALUES('1018', 'Princess Yachts', 'S72', '2004', '3600mhp', 'Essence', 'Bateau');



INSERT INTO Vivant VALUES(1002, to_date('09/03/2000', 'DD/MM/YYYY'), to_date('09/03/2015', 'DD/MM/YYYY'), 'Falcon' );
INSERT INTO Vivant VALUES(1003, to_date('11/03/2002', 'DD/MM/YYYY'), to_date('20/04/2015', 'DD/MM/YYYY'), 'Cheetah' );
INSERT INTO Vivant VALUES(1004, to_date('22/07/2001', 'DD/MM/YYYY'), to_date('02/02/2014', 'DD/MM/YYYY'), 'Shark' );
INSERT INTO Vivant (entiteID, dateNaissance, espece) VALUES(1005, to_date('15/05/2001', 'DD/MM/YYYY'), 'Coyote' );
INSERT INTO Vivant (entiteID, dateNaissance, espece) VALUES(1007, to_date('27/02/1986', 'DD/MM/YYYY'),  'Human' );
INSERT INTO Vivant (entiteID, dateNaissance, espece)  VALUES(1009, to_date('25/11/2003', 'DD/MM/YYYY'), 'Turtle' );
INSERT INTO Vivant (entiteID, dateNaissance, espece)  VALUES(1010, to_date('30/04/2005', 'DD/MM/YYYY'), 'Hyena' );
INSERT INTO Vivant VALUES(1011, to_date('05/05/2004', 'DD/MM/YYYY'), to_date('04/04/2015', 'DD/MM/YYYY'), 'Lion' );





Insert into Adopte values (1000, 1, 300, to_date('09/03/2011 02/07/09', 'DD/MM/YYYY HH24/MI/SS') , to_date('07/03/2013 02/07/09', 'DD/MM/YYYY HH24/MI/SS'), 'Scientifique');

Insert into Adopte values (1001, 1, 300, to_date('09/03/2011 02/07/09', 'DD/MM/YYYY HH24/MI/SS') , to_date('07/03/2013 02/07/09', 'DD/MM/YYYY HH24/MI/SS'), 'Scientifique');

Insert into Adopte values (1002, 2, 300, to_date('09/03/2011 02/07/09', 'DD/MM/YYYY HH24/MI/SS') , to_date('07/03/2013 02/07/09', 'DD/MM/YYYY HH24/MI/SS'), 'Scientifique');

Insert into Adopte values (1003, 3, 300, to_date('09/03/2011 02/07/09', 'DD/MM/YYYY HH24/MI/SS') , to_date('07/03/2013 02/07/09', 'DD/MM/YYYY HH24/MI/SS'), 'Ludique');

Insert into Adopte values (1004, 4, 300, to_date('09/03/2011 02/07/09', 'DD/MM/YYYY HH24/MI/SS') , to_date('07/03/2013 02/07/09', 'DD/MM/YYYY HH24/MI/SS'), 'Scientifique');

Insert into Adopte values (1005, 5, 300, to_date('09/03/2011 02/07/09', 'DD/MM/YYYY HH24/MI/SS') , to_date('07/03/2013 02/07/09', 'DD/MM/YYYY HH24/MI/SS'), 'Ludique');

Insert into Adopte values (1006, 6, 300, to_date('09/03/2011 02/07/09', 'DD/MM/YYYY HH24/MI/SS') , to_date('07/03/2013 02/07/09', 'DD/MM/YYYY HH24/MI/SS'), 'Scientifique');

Insert into Adopte values (1007, 7, 300, to_date('09/03/2011 02/07/09', 'DD/MM/YYYY HH24/MI/SS') , to_date('07/03/2013 02/07/09', 'DD/MM/YYYY HH24/MI/SS'), 'Scientifique');

Insert into Adopte values (1008, 8, 300, to_date('09/03/2011 02/07/09', 'DD/MM/YYYY HH24/MI/SS') , to_date('07/03/2013 02/07/09', 'DD/MM/YYYY HH24/MI/SS'), 'Ludique');

Insert into Adopte values (1009, 1, 300, to_date('09/03/2011 02/07/09', 'DD/MM/YYYY HH24/MI/SS') , to_date('07/03/2013 02/07/09', 'DD/MM/YYYY HH24/MI/SS'), 'Ludique');

Insert into Adopte values (1010, 2, 300, to_date('09/03/2011 02/07/09', 'DD/MM/YYYY HH24/MI/SS') , to_date('07/03/2013 02/07/09', 'DD/MM/YYYY HH24/MI/SS'), 'Ludique');

Insert into Adopte values (1011, 2, 300, to_date('09/03/2011 02/07/09', 'DD/MM/YYYY HH24/MI/SS') , to_date('07/03/2013 02/07/09', 'DD/MM/YYYY HH24/MI/SS'), 'Scientifique');

Insert into Adopte values (1012, 4, 300, to_date('09/03/2011 02/07/09', 'DD/MM/YYYY HH24/MI/SS') , to_date('07/03/2013 02/07/09', 'DD/MM/YYYY HH24/MI/SS'), 'Ludique');

Insert into Adopte values (1013, 3, 300, to_date('09/03/2011 02/07/09', 'DD/MM/YYYY HH24/MI/SS') , to_date('07/03/2013 02/07/09', 'DD/MM/YYYY HH24/MI/SS'), 'Scientifique');

Insert into Adopte values (1014, 5, 300, to_date('09/03/2011 02/07/09', 'DD/MM/YYYY HH24/MI/SS') , to_date('07/03/2013 02/07/09', 'DD/MM/YYYY HH24/MI/SS'), 'Ludique');

Insert into Adopte values (1015, 6, 300, to_date('09/03/2011 02/07/09', 'DD/MM/YYYY HH24/MI/SS') , to_date('07/03/2013 02/07/09', 'DD/MM/YYYY HH24/MI/SS'), 'Scientifique');

Insert into Adopte values (1016, 7, 300, to_date('09/03/2011 02/07/09', 'DD/MM/YYYY HH24/MI/SS') , to_date('07/03/2013 02/07/09', 'DD/MM/YYYY HH24/MI/SS'), 'Ludique');

Insert into Adopte values (1017, 8, 300, to_date('09/03/2011 02/07/09', 'DD/MM/YYYY HH24/MI/SS') , to_date('07/03/2013 02/07/09', 'DD/MM/YYYY HH24/MI/SS'), 'Scientifique');

Insert into Adopte values (1018, 6, 300, to_date('09/03/2011 02/07/09', 'DD/MM/YYYY HH24/MI/SS') , to_date('07/03/2013 02/07/09', 'DD/MM/YYYY HH24/MI/SS'), 'Ludique');



--SELECT EntiteMobile.entiteID, EntiteMobile.nom, EntiteMobile.capteurID FROM 
--				Particulier, EntiteMobile, Adopte 
--				WHERE Particulier.particulierID = Adopte.particulierID AND EntiteMobile.entiteID = Adopte.entiteID 
--				AND Adopte.particulierID = 1;


