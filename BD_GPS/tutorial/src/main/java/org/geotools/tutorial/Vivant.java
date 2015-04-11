package org.geotools.tutorial;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Vivant extends EntiteMobile{

	
	private String marque;
	private Date dateNaissance;
	private Date dateDeces;
	private String espece;


	public Vivant(){}

	//if animal dead;
	public Vivant(int entiteID, String nom, int capteurID, Date dateNaissance, Date dateDeces, String espece){
		super(entiteID, nom, capteurID);
	
		this.dateNaissance = dateNaissance;
		this.dateDeces = dateDeces;
		this.espece = espece;
	}
	
	
	//if animal not dead
	public Vivant(int entiteID, String nom, int capteurID, Date dateNaissance,  String espece){
		super(entiteID, nom, capteurID);
		
		this.dateNaissance = dateNaissance;
		this.espece = espece;
	}


	
	public String toString(){
		String result = "";
		result += super.toString() +  ", " +
				this.getDateNaissanceString() + ", " +  
				this.getDateDecesString() + ", " + this.espece;
		return result;
	}
	
	
	public static String dateInStringFormat(Date d){
		return new SimpleDateFormat("dd/MM/yyyy").format(d);
	}
	
	public String getDateNaissanceString(){
		return new SimpleDateFormat("dd/MM/yyyy").format(this.dateNaissance);
	}
	
	public String getDateDecesString(){
		if(this.dateDeces == null) return "";
		
		return new SimpleDateFormat("dd/MM/yyyy").format(this.dateDeces);
	}
	
	public String printListString(){
		return "";// this.particulierID + ": " + this.nom;
	}
	
	
	public String getMarque() {
		return marque;
	}

	public void setMarque(String marque) {
		this.marque = marque;
	}

	public Date getDateNaissance() {
		return dateNaissance;
	}
	
	
	public  void setDateNaissance(String str) throws Exception{
		  Date date1;
	     
			date1 = new SimpleDateFormat("dd/MM/yy").parse(str);
		
	      this.dateNaissance = date1;
	}
	
	
	public void setDateDeces(String str)throws Exception{
		  Date date1;
	    
			date1 = new SimpleDateFormat("dd/MM/yy").parse(str);
		
	      this.dateDeces = date1;
	}

	public void setDateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public Date getDateDeces() {
		return dateDeces;
	}

	public void setDateDeces(Date dateDeces) {
		this.dateDeces = dateDeces;
	}

	public String getEspece() {
		return espece;
	}

	public void setEspece(String espece) {
		this.espece = espece;
	}
	
	
	
}
