package org.geotools.tutorial;

import java.text.SimpleDateFormat;
import java.util.Date;

public class InfosScientifique extends GPS_Point{

	public InfosScientifique(int capteurID, Date sampleDate, double latitude,
			double longitude) {
		super(capteurID, sampleDate, latitude, longitude);
	}
	
	public InfosScientifique(int capteurID, Date sampleDate, double latitude,
			double longitude, int temperatureEnv, int vent,
			String directionVent, int pressionAtm, int houle, int altitude,
			int vitesse) {
		super(capteurID, sampleDate, latitude, longitude);
		this.temperatureEnv = temperatureEnv;
		this.vent = vent;
		this.directionVent = directionVent;
		this.pressionAtm = pressionAtm;
		this.houle = houle;
		this.altitude = altitude;
		this.vitesse = vitesse;
	}



	private int temperatureEnv;
	private int vent;
	private String directionVent;
	private int pressionAtm;
	private int houle;
	private int altitude;
	private int vitesse;
	
	
	public String toString(){
		return "\n" +
				"\tTempEnv: " + this.temperatureEnv  + "\n" + 
				"\tVent: " + this.vent  + "\n" + 
				"\tDirection Vent: " + this.directionVent  + "\n" + 
				"\tPression Atm: " +  this.pressionAtm  + "\n" + 
				"\tHoule: " + this.houle  + "\n" + 
				"\tAltitude: " + this.altitude  + "\n" + 
				"\tVitesse: " + this.vitesse;		
	}
	

	
	public int getTemperatureEnv() {
		return temperatureEnv;
	}
	public void setTemperatureEnv(int temperatureEnv) {
		this.temperatureEnv = temperatureEnv;
	}
	public int getVent() {
		return vent;
	}
	public void setVent(int vent) {
		this.vent = vent;
	}
	public String getDirectionVent() {
		return directionVent;
	}
	public void setDirectionVent(String directionVent) {
		this.directionVent = directionVent;
	}
	public int getPressionAtm() {
		return pressionAtm;
	}
	public void setPressionAtm(int pressionAtm) {
		this.pressionAtm = pressionAtm;
	}
	public int getHoule() {
		return houle;
	}
	public void setHoule(int houle) {
		this.houle = houle;
	}
	public int getAltitude() {
		return altitude;
	}
	public void setAltitude(int altitude) {
		this.altitude = altitude;
	}
	public int getVitesse() {
		return vitesse;
	}
	public void setVitesse(int vitesse) {
		this.vitesse = vitesse;
	}


	
}
