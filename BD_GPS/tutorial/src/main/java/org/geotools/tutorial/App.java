package org.geotools.tutorial;



import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;

import org.geotools.data.DataUtilities;
import org.geotools.data.FeatureSource;
import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.collection.ListFeatureCollection;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.factory.CommonFactoryFinder;
import org.geotools.feature.DefaultFeatureCollection;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureCollections;
import org.geotools.feature.SchemaException;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.geotools.geometry.jts.WKTReader2;
import org.geotools.map.FeatureLayer;
import org.geotools.map.Layer;
import org.geotools.map.MapContent;
import org.geotools.referencing.crs.DefaultEngineeringCRS;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.geotools.renderer.lite.StreamingRenderer;
import org.geotools.styling.FeatureTypeStyle;
import org.geotools.styling.Graphic;
import org.geotools.styling.Mark;
import org.geotools.styling.PointSymbolizer;
import org.geotools.styling.Rule;
import org.geotools.styling.SLD;
import org.geotools.styling.Style;
import org.geotools.swing.JMapFrame;
import org.geotools.swing.data.JFileDataStoreChooser;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.feature.type.GeometryDescriptor;
import org.opengis.filter.FilterFactory;
import org.opengis.style.StyleFactory;

import com.vividsolutions.jts.awt.PointShapeFactory.Point;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.io.ParseException;


public class App implements ActionListener{

	public static final String ADMIN = "admin";
	public static final String USER = "user";
	
	public static Connection conn;
	private String nom_usager;
	private String pwd;
	
	private JPanel pane;
	private JTextField userText;
	private JPasswordField passwordText;
	private static JButton loginButton;
	private static JButton registerButton;
	private JLabel mesg_error_usr;
	private JLabel mesg_error_pwd;
	
	public App() {

		
		// setter les composants
		pane = new JPanel();
		pane.setLayout(null);

		JLabel userLabel = new JLabel("User");
		userLabel.setBounds(10, 10, 80, 25);
		pane.add(userLabel);

		userText = new JTextField(20);
		userText.setBounds(100, 10, 160, 25);
		pane.add(userText);

		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setBounds(10, 40, 80, 25);
		pane.add(passwordLabel);

		passwordText = new JPasswordField(20);
		passwordText.setBounds(100, 40, 160, 25);
		pane.add(passwordText);

		loginButton = new JButton("login");
		loginButton.setBounds(10, 80, 80, 25);
		pane.add(loginButton);
		loginButton.addActionListener(this);
				
		registerButton = new JButton("register");
		registerButton.setBounds(180, 80, 80, 25);
		pane.add(registerButton);
		
		mesg_error_usr = new JLabel("");
		mesg_error_usr.setBounds(80, 120, 80, 25);
		pane.add(mesg_error_usr);
		
		mesg_error_pwd = new JLabel("");
		mesg_error_pwd.setBounds(80, 140, 120, 25);
		pane.add(mesg_error_pwd);
		
	}
	public static void main(String[] args) throws Exception {
		
		//Initalize connection to the DATABASE
		makeConn();
		
		//Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });

		//Test the username login query
//		Map<String, PasswordWrapper> userPassID; 
//		userPassID = PresetQueries.getUsernamesPasswords(conn);
	}
	
	//Connection code
	//Run this again whenever a connection breaks because of an exception
	public static void makeConn(){
		
		Connection conn = null;
		try{
			conn = Connexion.initializeConnexion(conn);

		}
		catch(Exception ex){

			System.out.println("Connection failed.");
			ex.printStackTrace();
		}
		App.conn = conn;
	}
	
	
	
    private static void createAndShowGUI() {
    	JFrame frame = new JFrame("Login application");
		frame.setSize(300, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		App app = new App();
		frame.add(app.pane);
		//placeComponents(panel);

		frame.setVisible(true);
		//GUI gui = new GUI("GPS Tracker");

    }
    
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if( (e.getActionCommand()).equals("login")) {
			try {
				//registerButton.removeActionListener(this);
				
				String usr = userText.getText();
				char[] pwd = passwordText.getPassword();
				System.out.println(pwd);
				if(usr.equals("")){
					mesg_error_usr.setText("User empty !");
				}
				
				if(pwd.length == 0 ){
					mesg_error_pwd.setText("Password empty !");
				}
				
				if(!usr.equals("") && pwd.length > 0) {
					
					GUI gui = new GUI("GPS Tracker", ADMIN, conn);
				}
				
				pane.revalidate();
		        pane.repaint();
				//registerButton.addActionListener(this);
				
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
}




//Test the username login query
//		Map<String, PasswordWrapper> userPassID = PresetQueries.getUsernamesPasswords(gui.conn);