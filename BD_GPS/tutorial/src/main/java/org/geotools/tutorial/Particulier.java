package org.geotools.tutorial;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Particulier {
	public static ArrayList<Particulier> listParticulier = new ArrayList<Particulier>();

	private int particulierID;
	private String nom;
	private String type;
	private String username;
	private String password;
	private String streetNumber;
	private String streetName;
	private String city;
	private String provState;
	private String country;
	private String postalCodeZip;
	private String tel;
	
	
	public Particulier(){}
	
	public Particulier(int particulierID, String nom, String type, String username, String password, 
			String streetNumber, String streetName, String city, String provState, String country, 
			String postalCodeZip, String tel){
		this.particulierID = particulierID;
		this.nom = nom;
		this.type = type;
		this.username = username;
		this.password = password;
		this.streetNumber = streetNumber;
		this.streetName = streetName;
		this.city = city;
		this.provState = provState;
		this.country = country;
		this.postalCodeZip = postalCodeZip;
		this.tel = tel;
		
	}
	
	
	public String toString(){
		String result = "";
		result += this.particulierID + ", " + this.nom + ", " + this.type + ", " + this.username + ", " + 
				this.password + ", " + this.streetNumber + ", " + this.streetName + ", " + this.city + ", " + 
				this.provState + ", " + this.country + ", " + this.postalCodeZip + ", " + this.tel;
		return result;
	}
	
	public String printListString(){
		return this.particulierID + ": " + this.nom;
	}
	
	
	public int getParticulierID() {
		return particulierID;
	}

	public void setParticulierID(int particulierID) {
		this.particulierID = particulierID;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStreetNumber() {
		return streetNumber;
	}

	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvState() {
		return provState;
	}

	public void setProvState(String provState) {
		this.provState = provState;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPostalCodeZip() {
		return postalCodeZip;
	}

	public void setPostalCodeZip(String postalCodeZip) {
		this.postalCodeZip = postalCodeZip;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	

}
