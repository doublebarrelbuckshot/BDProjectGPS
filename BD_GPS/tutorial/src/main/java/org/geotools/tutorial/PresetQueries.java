package org.geotools.tutorial;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class PresetQueries {

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

	public static void updateVivant(Connection conn, int entiteID, String dateNaissance, String dateDeces, String espece) throws SQLException{
		String decesOption = "";
		if(!dateDeces.equals("")){
			decesOption = "Vivant.dateDeces = to_date(\'" + dateDeces + "\', \'DD/MM/YYYY\'), ";
		}
		String query = "UPDATE Vivant SET Vivant.dateNaissance = to_date(\'" + dateNaissance + "\', \'DD/MM/YYYY\'), " +  
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
		Statement stmt = (Statement) conn.createStatement();
		stmt.executeUpdate(query);
	}


	/*
	 * GET ALL Capteur_GPS devices
	 */
	public static void getAllCapteur_GPS(Connection conn) throws SQLException{
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

}
