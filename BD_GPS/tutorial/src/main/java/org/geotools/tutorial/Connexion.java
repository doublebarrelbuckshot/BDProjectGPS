package org.geotools.tutorial;

import java.sql.Connection;
import java.sql.DriverManager;

public class Connexion {
	
	public static Connection initializeConnexion(Connection conn) throws Exception{
		String url = "jdbc:oracle:thin:@delphes.iro.umontreal.ca:1521:a05";		
		String usr = "rizzigia";
		String pwd = "giap103R";
		Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();  

		System.out.println("Connecting to Database URL = " + url);
		conn =DriverManager.getConnection(url, usr, pwd);

		System.out.println("Connected... Now creating a statement");
		return conn;
	}

}
