package org.geotools.tutorial;

import java.sql.Connection;
import java.sql.DriverManager;

public class Connexion {
	
	//Use alternate username and password when Connection fails
	String altUsr = "rizzigia";
	String altPwd = "giap103R";
	
	public static Connection initializeConnexion(Connection conn) throws Exception{
		String url = "jdbc:oracle:thin:@delphes.iro.umontreal.ca:1521:a05";		
		String usr = "courtemp";
		String pwd = "empp099C";
		Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();  

		System.out.println("Connecting to Database URL = " + url);
		conn =DriverManager.getConnection(url, usr, pwd);

		System.out.println("Connected... Now creating a statement");
		return conn;
	}

}