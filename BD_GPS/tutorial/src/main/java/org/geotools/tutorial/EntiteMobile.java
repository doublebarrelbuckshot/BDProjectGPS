package org.geotools.tutorial;

import java.util.ArrayList;

public class EntiteMobile {

	//public static ArrayList<Particulier> listParticulier = new ArrayList<Particulier>();

	private int entiteID;
	private String nom;
	private int capteurID;



	public EntiteMobile(){}

	public EntiteMobile(int entiteID, String nom, int capteurID){
		this.entiteID = entiteID;
		this.nom = nom;
		this.capteurID = capteurID;

	}
	
	public String printListString(){
		return this.entiteID + ": " + this.nom;
	}
	
	public String toString(){
		String result = "";
		result += this.entiteID + ", " + this.nom + ", " + this.capteurID;
		return result;
	}

	public int getEntiteID() {
		return entiteID;
	}

	public void setEntiteID(int entiteID) {
		this.entiteID = entiteID;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getCapteurID() {
		return capteurID;
	}

	public void setCapteurID(int capteurID) {
		this.capteurID = capteurID;
	}
	
	
}
