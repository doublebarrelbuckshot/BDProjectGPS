--INSTRUCTIONS pour retirer les tables
--On retire les tables avant de tenter de les remettres
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

--FIN DES INSTRUCTIONS pour retirer les tables
