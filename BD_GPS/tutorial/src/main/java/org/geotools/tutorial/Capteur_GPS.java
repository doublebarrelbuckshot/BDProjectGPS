package org.geotools.tutorial;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Capteur_GPS {
	
	public static ArrayList<Capteur_GPS> listCapteurGPS = new ArrayList<Capteur_GPS>();
	
	private int captID;
	private String model;
	private String fabricant;
	private String precisionGPS;
	private Date dateDebut;
	private Date dateFin;
	
	
	
	public Capteur_GPS(int captID, String model, String fabricant, String precisionGPS, Date dateDebut, Date dateFin){
		this.captID = captID;
		this.model = model;
		this.fabricant = fabricant;
		this.precisionGPS = precisionGPS;
		this.dateDebut = dateDebut;
		if(dateFin != null)
			this.dateFin = dateFin;	
	}

	public Capteur_GPS() {
	}

	public String printListString(){
		return captID + ": " + model;
	}

	public int getCaptID() {
		return captID;
	}

	
	public  void setDateDebut(String str) throws Exception{
		  Date date1;
	     
			date1 = new SimpleDateFormat("dd/MM/yy").parse(str);
		
	      this.dateDebut = date1;
	}
	
	
	public void setDateFin(String str)throws Exception{
		  Date date1;
	    
			date1 = new SimpleDateFormat("dd/MM/yy").parse(str);
		
	      this.dateFin = date1;
	}
	
	
	public String getDateDebutString(){
		if (this.dateDebut == null)
			return "";
		return new SimpleDateFormat("dd/MM/yyyy").format(this.dateDebut);
	}
	
	public String getDateFinString(){
		if (this.dateDebut == null)
			return "";
		return new SimpleDateFormat("dd/MM/yyyy").format(this.dateFin);
	}

	public void setCaptID(int captID) {
		this.captID = captID;
	}



	public String getModel() {
		return model;
	}



	public void setModel(String model) {
		this.model = model;
	}



	public String getFabricant() {
		return fabricant;
	}



	public void setFabricant(String fabricant) {
		this.fabricant = fabricant;
	}



	public String getPrecisionGPS() {
		return precisionGPS;
	}



	public void setPrecisionGPS(String precisionGPS) {
		this.precisionGPS = precisionGPS;
	}



	public Date getDateDebut() {
		return dateDebut;
	}



	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}



	public Date getDateFin() {
		return dateFin;
	}



	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}
	
	public String toString(){
		String result = this.captID + ", " +  this.model + ", " +  this.fabricant + ", " +  this.precisionGPS +
				", " +  this.dateDebut.toString();
		if( this.dateFin != null){
			result += ", " +  this.dateFin.toString();
		}
		return result;
	}
	

}
