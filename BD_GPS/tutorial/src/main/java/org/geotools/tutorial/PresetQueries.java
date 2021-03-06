package org.geotools.tutorial;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class PresetQueries {

	
	public static Hashtable<String, InfosScientifiqueVivant> getInfosScientifiqueVivantOfPoint(Connection conn, int capteurID) throws SQLException{
		
		String query = "SELECT Infos_Scientifique_Vivant.capteurID, Infos_Scientifique_Vivant.sampleDate, latitude, longitude, pouls, pressionArterielle, pourcentageGras, temperatureCorps, DB_GPS.latitude, DB_GPS.longitude "+
				"FROM Infos_Scientifique_Vivant, DB_GPS " +
				"where " +
				"Infos_Scientifique_Vivant.capteurID = DB_GPS.capteurID AND Infos_Scientifique_Vivant.sampleDate = DB_GPS.sampleDate AND " + 
				"DB_GPS.capteurID = " + capteurID ;//+ " AND " +
				//"sampleDate = " +
				//"to_date(\'" + gpsp.getSampleDateString() + "\', \'DD/MM/YYYY HH24/MI/SS\')";
		
		
		System.out.println(query);

		Statement stmt = (Statement) conn.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		Hashtable<String, InfosScientifiqueVivant> vivants = new Hashtable<String, InfosScientifiqueVivant>();
		while(rs.next()){
			int captID = rs.getInt("capteurID");
			Date sampleDate = rs.getTimestamp("sampleDate");
			double latitude = rs.getDouble("latitude");
			double longitude = rs.getDouble("longitude");
			int pouls = rs.getInt("pouls");
			int pressionArterielle = rs.getInt("pressionArterielle");
			Double pourcentageGras = rs.getDouble("pourcentageGras");
			int temperatureCorps = rs.getInt("temperatureCorps");

			InfosScientifiqueVivant isv = new InfosScientifiqueVivant(captID, sampleDate, latitude, longitude,
					pouls, pressionArterielle, pourcentageGras,temperatureCorps );
			vivants.put(GPS_Point.getSampleDateFromDate(sampleDate), isv);
		}
		return vivants;
	}
	
	
	
	public static Hashtable<String, InfosScientifique> getInfosScientifiqueOfPoint(Connection conn, int capteurID) throws SQLException{
		
		String query = "SELECT Infos_Scientifique.capteurID, Infos_Scientifique.sampleDate, latitude, longitude, temperatureEnv, vent, directionVent, pressionAtm, "+ 
				"houle, altitude, vitesse FROM Infos_Scientifique, DB_GPS " +
				"where " +
				"Infos_Scientifique.capteurID = DB_GPS.capteurID AND Infos_Scientifique.sampleDate = DB_GPS.sampleDate AND " + 
				"DB_GPS.capteurID = " + capteurID ;
		
		
		System.out.println(query);

		Statement stmt = (Statement) conn.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		Hashtable<String, InfosScientifique> vivants = new Hashtable<String, InfosScientifique>();

		
		while(rs.next()){
			int captID = rs.getInt("capteurID");
			Date sampleDate = rs.getTimestamp("sampleDate");
			double latitude = rs.getDouble("latitude");
			double longitude = rs.getDouble("longitude");
			int temperatureEnv = rs.getInt("temperatureEnv");
			int vent = rs.getInt("vent");
			String directionVent = rs.getString("directionVent");
			int pressionAtm = rs.getInt("pressionAtm");
			int houle = rs.getInt("houle");
			int altitude = rs.getInt("altitude");
			int vitesse = rs.getInt("vitesse");

			InfosScientifique is = new InfosScientifique(captID, sampleDate, latitude, longitude, temperatureEnv, 
					vent, directionVent, pressionAtm, houle, altitude, vitesse);
			vivants.put(GPS_Point.getSampleDateFromDate(sampleDate), is);
		}
		return vivants;
	}
	public static void deleteEM(Connection conn, EntiteMobile em) throws SQLException {

		String query = "DELETE FROM ENTITEMOBILE WHERE ENTITEID = " + em.getEntiteID();
		System.out.println(query);

		//System.out.println(query);
		Statement stmt = (Statement) conn.createStatement();
		stmt.executeUpdate(query);
	}




	public static void addArtificiel(Connection conn, Artificiel art) throws SQLException {

		String query = "INSERT INTO Artificiel VALUES "
				+ "("+ art.getEntiteID() + ", " 
				+ "\'" + art.getMarque() + "\'" + ", "
				+ "\'" +art.getModele() + "\'" + ", "
				+ art.getAnneeFabrication() + ", "
				+ "\'" +art.getPuissance() + "\'" + ", "
				+ "\'" + art.getCombustible() + "\'" + ", "
				+ "\'" +art.getTypeMachine() + "\')" ;


		System.out.println(query);

		//System.out.println(query);
		Statement stmt = (Statement) conn.createStatement();
		stmt.executeUpdate(query);
	}

	public static void addVivant(Connection conn, Vivant viv) throws SQLException {
		String query = "INSERT INTO VIVANT VALUES ";// + 
		if(viv.getDateDeces() == null){
			query = "INSERT INTO VIVANT(entiteID, dateNaissance, espece) VALUES " + 
					"("+ viv.getEntiteID() + ", " 
					+ "to_date(\'" + viv.getDateNaissanceString() + "\', \'DD/MM/YYYY\')," +
					"\'" + viv.getEspece() + "\'" + ")";
		}
		else query += "("+ viv.getEntiteID() + ", " 
				+ "to_date(\'" + viv.getDateNaissanceString() + "\', \'DD/MM/YYYY\')," +
				"to_date(\'" + viv.getDateDecesString() + "\', \'DD/MM/YYYY\'), " +  
				"\'" + viv.getEspece() + "\'" + ")";

		System.out.println(query);
		Statement stmt = (Statement) conn.createStatement();
		stmt.executeUpdate(query);

	}


	public static void addEM(Connection conn, EntiteMobile em) throws SQLException{
		String query = "INSERT INTO ENTITEMOBILE VALUES " + 
				"("+ em.getEntiteID() + ", " + "\'"+  em.getNom() + "\'" + ", " + em.getCapteurID() + ")";
		System.out.println(query);
		Statement stmt = (Statement) conn.createStatement();
		stmt.executeUpdate(query);

	}
	
	//CREATE TABLE Particulier(	particulierID INT PRIMARY KEY,
	//							nom VARCHAR(40), 
	//							type VARCHAR(20), 
	//							username VARCHAR(20), 
	//							password VARCHAR(20), 
	//							streetNumber varchar(10), 
	//							streetName VARCHAR(50), 
	//							city VARCHAR(30), 
	//							provState VARCHAR(30), 
	//							country VARCHAR(30), 
	//							postalCodeZip VARCHAR(7), 
	//							tel VARCHAR(20));
	
	//Add Particulier window query
	public static void addPA(Connection conn, Particulier pa) throws SQLException{
		String query = "INSERT INTO PARTICULIER VALUES " + 
				"("+ pa.getParticulierID() + ", " + 
				"\'"+ pa.getNom() + "\'" + ", " + 
				"\'"+ pa.getType() + "\'" + ", " + 
				"\'"+ pa.getUsername() + "\'" + ", " + 
				"\'"+ pa.getPassword() + "\'" + ", "+
				"\'"+ pa.getStreetNumber() + "\'" + ", "+
				"\'"+ pa.getStreetName() + "\'" + ", "+
				"\'"+ pa.getCity() + "\'" + ", "+
				"\'"+ pa.getProvState() + "\'" + ", "+
				"\'"+ pa.getCountry() + "\'" + ", "+
				"\'"+ pa.getPostalCodeZip() + "\'" + ", "+
				"\'"+ pa.getTel() + "\'" + ", "+
				")";
		System.out.println(query);
		Statement stmt = (Statement) conn.createStatement();
		stmt.executeUpdate(query);
	

	}

	public static void newCapteurGPS(Connection conn, Capteur_GPS cgps) throws SQLException {
		String query = "INSERT INTO CAPTEUR_GPS VALUES " + 
				"("+ cgps.getCaptID() + ", " + 
				"\'"+  cgps.getModel()+ "\', " +   
				"\'"+  cgps.getFabricant()+ "\'," +
				"\'"+  cgps.getPrecisionGPS()+ "\'," + 
				"to_date(\'" + cgps.getDateDebutString() + "\', \'DD/MM/YYYY\'), " +
				"to_date(\'" + cgps.getDateFinString() + "\', \'DD/MM/YYYY\')) ";
		System.out.println(query);
		Statement stmt = (Statement) conn.createStatement();
		stmt.executeUpdate(query);


	}



	public static int getMaxCGPSID(Connection conn) throws SQLException{
		String query = "SELECT MAX(capteurID) from Capteur_GPS";
		Statement stmt = (Statement) conn.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		int result = 0;
		while(rs.next()){
			result = rs.getInt("MAX(CAPTEURID)");

		}
		System.out.println("RESULT IS: " + result);
		return result;
	}
	public static int getMaxEMID(Connection conn) throws SQLException{
		String query = "SELECT MAX(entiteID) from EntiteMobile";
		Statement stmt = (Statement) conn.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		int result = 0;
		while(rs.next()){
			result = rs.getInt("MAX(ENTITEID)");

		}
		System.out.println("RESULT IS: " + result);
		return result;
	}

	public static void updateArtificiel(Connection conn, int entiteID,
			String marque, String modele, int anneeFabrication, String puissance,
			String combustible, String typeMachine) throws SQLException {

		String query = "UPDATE Artificiel SET Artificiel.marque = " + "\'" + marque + "\', " +
				"Artificiel.modele = " + "\'" + modele + "\', " + "Artificiel.anneeFabrication = " 
				+ anneeFabrication + ", Artificiel.puissance = " + "\'" + puissance + "\', " +
				"Artificiel.combustible = " + "\'" + combustible + "\', " +
				"Artificiel.typeMachine = " + "\'" + typeMachine + "\' " + 
				"WHERE "+
				"Artificiel.entiteID = "+ entiteID; //"FROM Capteur_GPS";

		//System.out.println(query);
		Statement stmt = (Statement) conn.createStatement();
		stmt.executeUpdate(query);

	}

	public static void updateCapteurGPS(Connection conn, int capteurID,
			String modele, String fabricant, String precisionGPS, String dateDebut, String dateFin) throws SQLException {
		String decesOption = "";

		String query = "UPDATE Capteur_GPS SET " + 
				"modele = " + "\'" + modele + "\', " +
				"fabricant = " + "\'" + fabricant + "\', " +
				"precisionGPS = " + "\'" + precisionGPS + "\', " +
				"dateDebut = to_date(\'" + dateDebut + "\', \'DD/MM/YYYY\'), " +  
				"dateFin = to_date(\'" + dateFin + "\', \'DD/MM/YYYY\') "+
				"WHERE "+
				"capteurID = "+ capteurID; //"FROM Capteur_GPS";

		//System.out.println(query);
		Statement stmt = (Statement) conn.createStatement();
		stmt.executeUpdate(query);


	}
	
	public static void updateParticulier(Connection conn, int particulierID,
			String nom, String type, String username, String password, String streetNumber, String streetName, String city, String provState, String country, String postalCodeZip, String tel) throws SQLException {

		String query = "UPDATE Particulier SET " + 
				"nom = " + "\'" + nom + "\', " +
				"type = " + "\'" + type + "\', " +
				"username = " + "\'" + username + "\', " +
				"password = " + "\'" + password + "\', " +
				"streetNumber = " + "\'" + streetNumber + "\', " +
				"streetName = " + "\'" + streetName + "\', " +
				"city = " + "\'" + city + "\', " +
				"provState = " + "\'" + provState + "\', " +
				"country = " + "\'" + country + "\', " +
				"postalCodeZip = " + "\'" + postalCodeZip + "\', " +
				"tel = " + "\'" + tel + "\' " +
				"WHERE "+
				"particulierID = "+ particulierID;

		System.out.println(query);
		Statement stmt = (Statement) conn.createStatement();
		stmt.executeUpdate(query);

	}
	
	public static void createParticulier(Connection conn, int particulierID,
			String nom, String type, String username, String password, String streetNumber, String streetName, String city, String provState, String country, String postalCodeZip, String tel) throws SQLException {

		String query = "Insert into Particulier values (" +
				particulierID + ", " +
				"\'" + nom + "\', " +
				"\'" + type + "\', " +
				"\'" + username + "\', " +
				"\'" + password + "\', " +
				"\'" + streetNumber + "\', " +
				"\'" + streetName + "\', " +
				"\'" + city + "\', " +
				"\'" + provState + "\', " +
				"\'" + country + "\', " +
				"\'" + postalCodeZip + "\', " +
				"\'" + tel + "\')";

		System.out.println(query);
		Statement stmt = (Statement) conn.createStatement();
		stmt.executeUpdate(query);

	}
	
	public static void deleteParticulier(Connection conn, String particulierID) throws SQLException {

		String query = "DELETE FROM Particulier WHERE particulierID = "+particulierID;

		System.out.println(query);
		Statement stmt = (Statement) conn.createStatement();
		stmt.executeUpdate(query);

	}

	public static void updateVivant(Connection conn, int entiteID, Date dateNaissance, Date dateDeces, String espece) throws SQLException{
		String decesOption = "";
		if(dateDeces != null){
			decesOption = "Vivant.dateDeces = to_date(\'" + Vivant.dateInStringFormat(dateDeces) + "\', \'DD/MM/YYYY\'), ";
		}
		String query = "UPDATE Vivant SET Vivant.dateNaissance = to_date(\'" + Vivant.dateInStringFormat(dateNaissance) + "\', \'DD/MM/YYYY\'), " +  
				decesOption +
				"Vivant.espece = " + "\'" + espece + "\'" + " WHERE "+
				"Vivant.entiteID = "+ entiteID; //"FROM Capteur_GPS";

		//System.out.println(query);
		Statement stmt = (Statement) conn.createStatement();
		stmt.executeUpdate(query);


	}


	public static void updateEntiteMobile(Connection conn, int entiteID, String nom, int capteurID ) throws SQLException{
		//System.out.println(capteurID + " " + entiteID + " " + nom);
		String query = "UPDATE EntiteMobile SET EntiteMobile.nom = \'" + nom + "\', EntiteMobile.capteurID = " + capteurID + " WHERE "+
				"EntiteMobile.entiteID = "+ entiteID; //"FROM Capteur_GPS";
		System.out.println("!!!!!!!!!!!!!!!!!!!!!" + query);
		Statement stmt = (Statement) conn.createStatement();
		stmt.executeUpdate(query);
	}


	/*
	 * GET ALL Capteur_GPS devices
	 */
	public static void getAllCapteur_GPS(Connection conn) throws SQLException{
		Capteur_GPS.listCapteurGPS = new ArrayList<Capteur_GPS>();
		String query = "SELECT * FROM Capteur_GPS";
		Statement stmt = (Statement) conn.createStatement();
		ResultSet rs = stmt.executeQuery(query);

		while(rs.next()){
			int captID = rs.getInt("CapteurID");
			String model = rs.getString("modele");
			String fabricant = rs.getString("fabricant");
			String precisionGPS = rs.getString("precisionGPS");
			Date dateDebut = rs.getDate("dateDebut");
			Date dateFin = rs.getDate("dateFin");

			Capteur_GPS gps = new Capteur_GPS(captID, model, fabricant, precisionGPS, dateDebut, dateFin);
			Capteur_GPS.listCapteurGPS.add(gps);
		}
		for (Capteur_GPS x : Capteur_GPS.listCapteurGPS){
			System.out.println(x);
		}
	}



	public static ArrayList<String> getAllCapteurIDs(Connection conn) throws SQLException{
		ArrayList<String> result = new ArrayList<String>();
		String query = "SELECT capteurID FROM Capteur_GPS";
		Statement stmt = (Statement) conn.createStatement();
		ResultSet rs = stmt.executeQuery(query);

		while(rs.next()){
			String captID = rs.getString("CapteurID");
			result.add(captID);
		}
		return result;
	}

	public static Capteur_GPS getCapteurGPSDetails(Connection conn, int capteurID) throws SQLException {

		String query = "SELECT capteurID, model, fabricant, " +
				"precisionGPS, dateDebut, dateFin " +
				"FROM Capteur_GPS " +
				"WHERE capteurID  = " + capteurID;
		Statement stmt = (Statement) conn.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		Capteur_GPS gps = null;
		while(rs.next()){
			int captID = rs.getInt("CapteurID");
			String model = rs.getString("modele");
			String fabricant = rs.getString("fabricant");
			String precisionGPS = rs.getString("precisionGPS");
			Date dateDebut = rs.getDate("dateDebut");
			Date dateFin = rs.getDate("dateFin");
			gps = new Capteur_GPS(captID, model, fabricant, precisionGPS, dateDebut, dateFin);
 
		}

		return gps;


	}


	public static Particulier getParticulierDetails(Connection conn, int paID) throws SQLException{

		String query = "SELECT Particulier.particulierID, Particulier.nom, Particulier.type, Particulier.username, Particulier.password, " +
				"Particulier.streetNumber, Particulier.streetName, Particulier.city, Particulier.provState, Particulier.country, Particulier.postalCodeZip, Particulier.tel " +
				"FROM Particulier " +
				"WHERE Particulier.particulierID = " + paID;
				System.out.println(query);
		Statement stmt = (Statement) conn.createStatement();
		ResultSet r2 = stmt.executeQuery(query);
	
		Particulier part = null;

		while(r2.next()){
			
				String nom = r2.getString("nom");
				String type = r2.getString("type");
				String username = r2.getString("username");
				String password = r2.getString("password");
				String streetNumber = r2.getString("streetNumber");
				String streetName = r2.getString("streetName");
				String city = r2.getString("city");
				String provState = r2.getString("provState");
				String country = r2.getString("country");
				String postalCodeZip = r2.getString("postalCodeZip");
				String tel = r2.getString("tel");

				part = new Particulier(paID, nom, type, username, password, 
						 streetNumber,  streetName,  city,  provState,  country, 
						 postalCodeZip,  tel);
				
		}
		return part;
	}
	
	public static int getNextParticulierID(Connection conn) throws SQLException{
		
		
		int nextID = -1;
		String query = "SELECT particulierID FROM Particulier";
				System.out.println(query);
		Statement stmt = (Statement) conn.createStatement();
		ResultSet r2 = stmt.executeQuery(query);

		while(r2.next()){
			
			int id = r2.getInt("particulierID");
			nextID = id;
		}
		return (nextID + 1);
	}
	
	
	public static EntiteMobile getEntiteMobileDetails(Connection conn, EntiteMobile em) throws SQLException{
		int entiteID = em.getEntiteID();
		String query = "SELECT EntiteMobile.entiteID, EntiteMobile.nom, EntiteMobile.capteurID, " +
				"Vivant.dateNaissance, Vivant.dateDeces, Vivant.espece " +
				"FROM EntiteMobile, Vivant " +
				"WHERE EntiteMobile.entiteID = Vivant.entiteID " +
				"AND EntiteMobile.entiteID = " + entiteID;
		Statement stmt = (Statement) conn.createStatement();
		ResultSet r1 = stmt.executeQuery(query);
		if (!r1.isBeforeFirst() ) {  //NOT VIVANT  
			query = "SELECT EntiteMobile.entiteID, EntiteMobile.nom, EntiteMobile.capteurID, " +
					"Artificiel.marque, Artificiel.modele, Artificiel.anneeFabrication, Artificiel.puissance, " +
					"Artificiel.combustible, Artificiel.typeMachine " +
					"FROM EntiteMobile, Artificiel " +
					"WHERE EntiteMobile.entiteID = Artificiel.entiteID " +
					"AND EntiteMobile.entiteID = " + entiteID;

			stmt = (Statement) conn.createStatement();
			ResultSet r2 = stmt.executeQuery(query);
			if (!r2.isBeforeFirst() ) { //NOT ARTIFICIEL
				return em;
			}
			else{ //It's Artificiel
				while(r2.next()){
					String marque = r2.getString("marque");
					String modele = r2.getString("modele");
					int anneeFabrication = r2.getInt("anneeFabrication");
					String puissance = r2.getString("puissance");
					String combustible = r2.getString("combustible");
					String typeMachine = r2.getString("typeMachine");
					Artificiel art = new Artificiel(em.getEntiteID(), em.getNom(), em.getCapteurID(), marque, modele, anneeFabrication, puissance, combustible, typeMachine);
					return ((EntiteMobile)art);
				}
			}
		}
		//Its Vivant
		while(r1.next()){
			Date dateNaissance = r1.getTimestamp("dateNaissance");
			String espece = r1.getString("espece");

			Date dateDeces = r1.getTimestamp("dateDeces");
			if(dateDeces!=null){
				Vivant viv = new Vivant(em.getEntiteID(), em.getNom(), em.getCapteurID(), dateNaissance, dateDeces, espece);
				return (EntiteMobile)viv;
			}
			else{
				Vivant viv = new Vivant(em.getEntiteID(), em.getNom(), em.getCapteurID(), dateNaissance, espece);
				return (EntiteMobile)viv;
			}

		}


		return null;

	}

	public static ArrayList<Capteur_GPS> getCapteursForParticulier(Connection conn, int particulierID) throws SQLException{
		ArrayList<Capteur_GPS> result = new ArrayList<Capteur_GPS>();
		String query = "SELECT Capteur_GPS.capteurID, Capteur_GPS.modele, Capteur_GPS.fabricant, " + 
				"Capteur_GPS.precisionGPS, Capteur_GPS.dateDebut, Capteur_GPS.dateFin " +
				"FROM " + "Particulier, EntiteMobile, Capteur_GPS, Adopte " +
				"WHERE Particulier.particulierID = Adopte.particulierID AND EntiteMobile.entiteID = Adopte.entiteID " + 
				"AND EntiteMobile.capteurID = Capteur_GPS.capteurID " +
				"AND Adopte.particulierID = " + particulierID;


		Statement stmt = (Statement) conn.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		while(rs.next()){

			int capteurID = rs.getInt("capteurID");
			String modele = rs.getString("modele");
			String fabricant = rs.getString("modele");
			String precisionGPS = rs.getString("modele");
			Date dateDebut = rs.getTimestamp("dateDebut");
			Date dateFin = rs.getTimestamp("dateFin");

			Capteur_GPS cgps = new Capteur_GPS(capteurID, modele, fabricant,precisionGPS, dateDebut, dateFin);
			result.add(cgps);	
		}

		for (Capteur_GPS x : result){
			System.out.println(x);
		}

		return result;
	}


	public static ArrayList<EntiteMobile> getAllEntiteMobile(Connection conn) throws SQLException{
		ArrayList<EntiteMobile> result = new ArrayList<EntiteMobile>();
		String query = "SELECT * FROM EntiteMobile";


		Statement stmt = (Statement) conn.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		while(rs.next()){

			int entiteID = rs.getInt("entiteID");
			String nom = rs.getString("nom");
			int capteurID = rs.getInt("capteurID");

			EntiteMobile em = new EntiteMobile(entiteID, nom, capteurID);
			result.add(em);	
		}

		for (EntiteMobile x : result){
			System.out.println(x);
		}

		return result;
	}
	public static ArrayList<EntiteMobile> getEntiteMobileForParticulier(Connection conn, int particulierID) throws SQLException{
		ArrayList<EntiteMobile> result = new ArrayList<EntiteMobile>();
		String query = "SELECT EntiteMobile.entiteID, EntiteMobile.nom, EntiteMobile.capteurID " +
				"FROM " + "Particulier, EntiteMobile, Adopte " +
				"WHERE Particulier.particulierID = Adopte.particulierID AND EntiteMobile.entiteID = Adopte.entiteID " +
				"AND Adopte.particulierID = " + particulierID;


		Statement stmt = (Statement) conn.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		while(rs.next()){

			int entiteID = rs.getInt("entiteID");
			String nom = rs.getString("nom");
			int capteurID = rs.getInt("capteurID");

			EntiteMobile em = new EntiteMobile(entiteID, nom, capteurID);
			result.add(em);	
		}

		for (EntiteMobile x : result){
			System.out.println(x);
		}

		return result;
	}


	/*
	 * GET ALL GPS points related to a specific capteurID
	 */
	public static ArrayList<GPS_Point> getGPSPointsForCapteurID(Connection conn, int capteurID) throws SQLException{
		ArrayList<GPS_Point> points = new ArrayList<GPS_Point>();
		String query = "SELECT * FROM DB_GPS where capteurID =" + capteurID ;
		Statement stmt = (Statement) conn.createStatement();
		ResultSet rs = stmt.executeQuery(query);

		while(rs.next()){

			int captID = rs.getInt("capteurID");
			Date sampleDate = rs.getTimestamp("sampleDate");
			double latitude = rs.getDouble("latitude");
			double longitude = rs.getDouble("longitude");
			GPS_Point gp = new GPS_Point(captID, sampleDate, latitude, longitude);
			points.add(gp);
		}
		for (GPS_Point x : points){
			System.out.println(x);
		}
		return points;
	}


	/*
	 * GET ALL Particulier entries
	 */
	public static void getAllParticulier(Connection conn) throws SQLException{
		String query = "SELECT * FROM Particulier";
		Statement stmt = (Statement) conn.createStatement();
		ResultSet rs = stmt.executeQuery(query);

		while(rs.next()){

			int particulierID = rs.getInt("particulierID");
			String nom = rs.getString("nom");
			String type = rs.getString("type");
			String username = rs.getString("username");
			String password = rs.getString("password");
			String streetNumber = rs.getString("streetNumber");
			String streetName = rs.getString("streetName");
			String city = rs.getString("city");
			String provState = rs.getString("provState");
			String country = rs.getString("country");
			String postalCodeZip = rs.getString("postalCodeZip");
			String tel = rs.getString("tel");

			Particulier p = new Particulier(particulierID, nom, type, username, password, streetNumber, streetName, city, provState, country, postalCodeZip, tel);
			Particulier.listParticulier.add(p);
		}
		for (Particulier x : Particulier.listParticulier){
			System.out.println(x);
		}
	}
	
	
	//This method returns a map of the username passwords and ID necessary for login.
	public static Map<String, PasswordWrapper> getUsernamesPasswords(Connection conn) throws SQLException{
		
		System.out.println("\nGetting username and passwords...");
		//Map of usernames and passwords to be returned
		Map<String, PasswordWrapper> usernamePasswordsMap = new HashMap<String, PasswordWrapper>();
		
		String query = "SELECT * FROM Particulier";
		Statement stmt = (Statement) conn.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		
		while(rs.next()){

			int particulierID = rs.getInt("particulierID");
			String username = rs.getString("username");
			String password = rs.getString("password");
			
			PasswordWrapper currentWrapper = new PasswordWrapper(password, particulierID);
			usernamePasswordsMap.put(username, currentWrapper);
			
		}
		
		for(Map.Entry<String, PasswordWrapper> entry : usernamePasswordsMap.entrySet()){
			System.out.println(" ");
			System.out.println("Username: " +entry.getKey());
			System.out.println("Password: " +entry.getValue().getPassword());
			System.out.println("ID: " +entry.getValue().getID());
		}
		
		return usernamePasswordsMap;
		
	}
	
	public static void adoptEntity(Connection conn, Particulier pa, EntiteMobile en, String interet) throws SQLException{
		
		System.out.println("Particulier "+pa.getNom() +" is adopting Entity " + en.getNom() + ".");
		//

		String query = "Insert into Adopte values ("
				+ en.getEntiteID()+", "
				+ pa.getParticulierID()+", "
				+ "300" + ", "							//Dates... It had to be dates...
				+ "to_date('09/03/2011 02/07/09', 'DD/MM/YYYY HH24/MI/SS') , to_date('07/03/2013 02/07/09', 'DD/MM/YYYY HH24/MI/SS'), "
				+ "\'" +interet+"\')";	
		
		System.out.println(query);
		Statement stmt = (Statement) conn.createStatement();
		stmt.executeQuery(query);

	}
	
	public static void unAdoptEntity(Connection conn, Particulier pa, EntiteMobile en, String interet) throws SQLException{
		
		System.out.println("Particulier "+pa.getNom() +" is un-adopting Entity " + en.getNom() + ".");
		

		String query = "DELETE FROM Adopte WHERE entiteID = "+en.getEntiteID()+" AND particulierID = "+pa.getParticulierID() ;
		
		System.out.println(query);
		Statement stmt = (Statement) conn.createStatement();
		stmt.executeQuery(query);

	}






















}
