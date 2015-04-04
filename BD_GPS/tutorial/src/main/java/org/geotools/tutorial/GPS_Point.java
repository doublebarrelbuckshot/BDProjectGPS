package org.geotools.tutorial;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GPS_Point {
		
		//public static ArrayList<Capteur_GPS> listCapteurGPS = new ArrayList<Capteur_GPS>();
		
		private int capteurID;
		private Date sampleDate;
		private double latitude;
		private double longitude;
		

		public GPS_Point(int capteurID, Date sampleDate, double latitude, double longitude){
			this.capteurID = capteurID;
			this.sampleDate = sampleDate;
			this.latitude = latitude;
			this.longitude = longitude;
		}
		
		
		
		public int getCapteurID() {
			return capteurID;
		}

		public void setCapteurID(int capteurID) {
			this.capteurID = capteurID;
		}

		public Date getSampleDate() {
			return sampleDate;
		}

		public void setSampleDate(Date sampleDate) {
			this.sampleDate = sampleDate;
		}

		public double getLatitude() {
			return latitude;
		}

		public void setLatitude(double latitude) {
			this.latitude = latitude;
		}

		public double getLongitude() {
			return longitude;
		}

		public void setLongitude(double longitude) {
			this.longitude = longitude;
		}

		public String toString(){
			String result = "";
			result += this.capteurID + ", " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(this.sampleDate) + ", " + this.latitude + ", " + this.longitude;
			return result;
		}
}
