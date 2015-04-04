package org.geotools.tutorial;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class GUI extends JFrame implements ListSelectionListener, ActionListener{

	private static Connection conn;
	private int capteurIWantToMap = 101;

	public GUI(String s) throws Exception{
		super(s);


		/*
		 * Initialize connection
		 */
		GUI.conn = null;
		try{
			conn = Connexion.initializeConnexion(conn);

		}
		catch(Exception ex){

			System.out.println("Connection failed.");
			ex.printStackTrace();
		}


		/*
		 * Load preset queries so that we can populate important lists in GUI
		 */
		PresetQueries.getAllCapteur_GPS(conn);
		PresetQueries.getAllParticulier(conn);

		//


		/*
		 * END Initialize connection
		 */



		/*
		 * Initalize showMap button
		 */
		JPanel panShowMap = new JPanel();
		JButton btnShowMap = new JButton("Show Map");

		panShowMap.add(btnShowMap);
		btnShowMap.addActionListener(this);

		add(panShowMap, BorderLayout.NORTH);

		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/*
		 * Main Window settings
		 */
		setSize(1000,750);
	}



	public void actionPerformed(ActionEvent arg0){

		try {
			GeoMapWindow.showGeoMapWindow(conn, capteurIWantToMap);
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		public void valueChanged(ListSelectionEvent arg0) {
			// TODO Auto-generated method stub

		}

	}
