package org.geotools.tutorial;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class PresetQueries {
	
	
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
