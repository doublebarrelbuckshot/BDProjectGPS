-- LISTE de REQUETES 



-- 1. Selectionner tous les particuliers qui detient des entites mobiles qui ont deja decedes
select Particulier.nom, entiteMobile.nom, Vivant.dateDeces, capteur_GPS.modele
	from Particulier, entiteMobile, Vivant, capteur_GPS, Adopte
	where
		Particulier.particulierID = Adopte.particulierID and
		entiteMobile.entiteID = Adopte.entiteID and
		Vivant.entiteID = entiteMobile.entiteID and
		capteur_GPS.capteurID = entiteMobile.capteurID and
		Vivant.dateDeces is not null;

-- 2. Selectionner tous les particuliers qui detient des entites mobiles vivants
select distinct Particulier.nom from Particulier, entiteMobile, Vivant, Adopte
	where
		Particulier.particulierID = Adopte.particulierID and
		entiteMobile.entiteID = Adopte.entiteID and
		Vivant.entiteID = entiteMobile.entiteID;
		
-- 3. Selectionner tous les particuliers qui detient des entites mobiles artificiels
select distinct Particulier.nom from Particulier, entiteMobile, Artificiel, Adopte
	where
		Particulier.particulierID = Adopte.particulierID and
		entiteMobile.entiteID = Adopte.entiteID and
		Artificiel.entiteID = entiteMobile.entiteID;
		
-- 4. Selectionner tous les particuliers qui  ne détiennent pas d'entite mobile artificiel
select distinct Particulier.nom from Particulier, Vivant, Adopte, Artificiel, entiteMobile
	where
		Particulier.particulierID = Adopte.particulierID and
		entiteMobile.entiteID = Adopte.entiteID and
		entiteMobile.entiteID = vivant.entiteID and
		Particulier.particulierID not in (select Adopte.particulierID from Adopte, Artificiel where Adopte.entiteID = Artificiel.entiteID);
		
-- 5. Selectionner tous les particuliers qui  ne détiennent pas d'entite mobile vivante
select distinct Particulier.nom from Particulier, Vivant, Adopte, Artificiel, entiteMobile
	where
		Particulier.particulierID = Adopte.particulierID and
		entiteMobile.entiteID = Adopte.entiteID and
		entiteMobile.entiteID = Artificiel.entiteID and
		Particulier.particulierID not in (select Adopte.particulierID from Adopte, Vivant where Adopte.entiteID = Vivant.entiteID);
		
 -- 6. Selectionner l'entiteID et nom  ainsi que  la position (lat, long) des entites mobiles non artificielles qui sont mortent en 2015 et 
 --qui possédaient un pourcentage de gras inférieur à la moyenne.  

SELECT entiteID, nom, latitude, longitude FROM DB_GPS, EntiteMobile
  WHERE (DB_GPS.capteurID, sampleDate) IN
    (SELECT capteurID, sampleDate FROM Infos_Scientifique_Vivant WHERE pourcentageGras < (SELECT AVG(pourcentageGras) FROM Infos_Scientifique_Vivant))
  AND DB_GPS.capteurID = EntiteMobile.capteurID
  AND DB_GPS.capteurID IN
    (SELECT capteurID FROM EntiteMobile
  WHERE entiteID IN
    (SELECT entiteID FROM Vivant WHERE dateDeces LIKE '15-%'));



 -- 7. Selectionner tous les particuliers (noms et types)(noms et types) qui ont adopté un ou des vivants par intérêt scientifique en mentionnant l'entiteID et espece  des entites mobiles vivantes.  
SELECT distinct Particulier.nom, type, Adopte.entiteID, EntiteMobile.nom, Vivant.espece FROM Particulier, Adopte, EntiteMobile, Vivant
  WHERE Adopte.particulierID = Particulier.particulierID 
  AND interet = 'Scientifique'
  AND Adopte.entiteID = Vivant.entiteID 
  AND EntiteMobile.entiteID = Adopte.entiteID
  ORDER BY type ASC, Particulier.nom ASC;

 -- 8. Selectionner tous les particuliers (noms et types) qui ont adopté des artificiels par intérêt scientifique en mentionnant l'entiteID et espece  des entites mobiles Artificielles.  
SELECT distinct Particulier.nom, type, Adopte.entiteID, EntiteMobile.nom, Artificiel.typeMachine FROM Particulier, Adopte, EntiteMobile, Artificiel
  WHERE Adopte.particulierID = Particulier.particulierID 
  AND interet = 'Scientifique'
  AND Adopte.entiteID = Artificiel.entiteID 
  AND EntiteMobile.entiteID = Adopte.entiteID
  ORDER BY type ASC, Particulier.nom ASC;

 -- 9. Selectionner tous les particuliers (noms et types) qui payent  le prix minimum pour adoption.  
SELECT distinct nom, type FROM Particulier, Adopte
  WHERE Adopte.particulierID = Particulier.particulierID AND prix = (SELECT MIN(prix) FROM Adopte) 
  ORDER BY type ASC, nom ASC;

 -- 10. Selectionner tous les particuliers (noms et types) qui ont adopté plus de deux entités mobiles.
SELECT nom, type, COUNT(*)  FROM Particulier, Adopte
  WHERE Adopte.particulierID = Particulier.particulierID
  GROUP BY nom, type
  HAVING COUNT(*) > 2;

