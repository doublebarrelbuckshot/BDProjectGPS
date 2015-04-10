package org.geotools.tutorial;

//This password wrapper class is used when retrieving the user password and ID so as to login as that user 
public class PasswordWrapper {
	
	String password;
	int ID;
	
	public PasswordWrapper(String password, int ID) {
	       this.password = password;
	       this.ID = ID;
	    }
	
	public String getPassword(){
		return this.password;
	}

	public int getID(){
		return this.ID;
	}
}
