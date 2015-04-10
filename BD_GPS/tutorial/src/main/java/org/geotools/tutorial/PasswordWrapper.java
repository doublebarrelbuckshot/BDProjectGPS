package org.geotools.tutorial;

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
