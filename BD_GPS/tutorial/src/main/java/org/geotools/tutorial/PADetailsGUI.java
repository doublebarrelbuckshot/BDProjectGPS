package org.geotools.tutorial;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

public class PADetailsGUI extends JFrame implements ActionListener {


	
	private JLabel particulierIDTF;
	private JTextField nomTF;
	private JTextField typeTF;
	private JTextField usernameTF;
	
	private JTextField streetNumberTF;
	private JTextField streetNameTF;
	private JTextField cityTF;
	private JTextField provStateTF;
	private JTextField countryTF;
	private JTextField postalCodeZipTF;
	private JTextField telTF;
	private Connection conn;


	private Particulier pa;
	private GUI gui;

	public PADetailsGUI(String s, Particulier em, Boolean editableFlag, Connection conn, GUI gui) throws SQLException{
		super(s);

		this.conn = conn;
		this.pa = em;
		this.gui = gui;
		//SET UP MAIN PANEL OF INFO

		JPanel PanPADetails = new JPanel();
		PanPADetails.setLayout(new GridLayout(11,2));

		
		/*
		 * ************************************************************
		 ********** 		Particulier properties	        ***********
		 * ************************************************************
		 */		
		JLabel particulierIDLabel = new JLabel("ParticulierID:");
		particulierIDTF = new JLabel(Integer.toString(pa.getParticulierID()));
		
		JLabel nomLabel = new JLabel("Nom:");
		nomTF = new JTextField(pa.getNom());
		
		JLabel typeLabel = new JLabel("Type:");
		typeTF = new JTextField(pa.getType());
		
		JLabel usernameLabel = new JLabel("Username:");
		usernameTF = new JTextField(pa.getUsername());
		
		JLabel streetNumberLabel = new JLabel("Street Number:");
		streetNumberTF = new JTextField(pa.getStreetNumber());
		
		JLabel streetNameLabel = new JLabel("Street Name:");
		streetNameTF = new JTextField(pa.getStreetName());
		
		JLabel cityLabel = new JLabel("City:");
		cityTF = new JTextField(pa.getCity());
		
		JLabel provStateLabel = new JLabel("Province/State:");
		provStateTF = new JTextField(pa.getProvState());
		
		JLabel countryLabel = new JLabel("Country:");
		countryTF = new JTextField(pa.getCountry());
		
		JLabel postalCodeZipLabel = new JLabel("PostalCode/Zip:");
		postalCodeZipTF  = new JTextField(pa.getPostalCodeZip());;
		
		JLabel telLabel = new JLabel("Telephone:");
		telTF = new JTextField(pa.getStreetName());;
		

		if(!editableFlag){
			nomTF.setEditable(false);
			typeTF.setEditable(false);
			usernameTF.setEditable(false);
			
			streetNumberTF.setEditable(false);
			streetNameTF.setEditable(false);
			cityTF.setEditable(false);
			provStateTF.setEditable(false);
			countryTF.setEditable(false);
			postalCodeZipTF.setEditable(false);
			telTF.setEditable(false);
		}

		PanPADetails.add(particulierIDLabel);
		PanPADetails.add(particulierIDTF);
		
		PanPADetails.add(nomLabel);
		PanPADetails.add(nomTF);
		
		PanPADetails.add(typeLabel);
		PanPADetails.add(typeTF);
		
		PanPADetails.add(usernameLabel);
		PanPADetails.add(usernameTF);
		
		PanPADetails.add(streetNumberLabel);
		PanPADetails.add(streetNumberTF);
		
		PanPADetails.add(streetNameLabel);
		PanPADetails.add(streetNameTF);
		
		PanPADetails.add(cityLabel);
		PanPADetails.add(cityTF);
		
		PanPADetails.add(provStateLabel);
		PanPADetails.add(provStateTF);
		
		PanPADetails.add(countryLabel);
		PanPADetails.add(countryTF);
		
		PanPADetails.add(postalCodeZipLabel);
		PanPADetails.add(postalCodeZipTF);
		
		PanPADetails.add(telLabel);
		PanPADetails.add(telTF);
		


		/*
		 * ************************************************************
		 ********** 		Buttons					        ***********
		 * ************************************************************
		 */	
		JPanel PanBottomButtons = new JPanel();


		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(this);
		btnClose.setActionCommand("btnClose");

		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(this);
		btnSave.setActionCommand("btnSave");

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(this);
		btnCancel.setActionCommand("btnCancel");

		if(editableFlag){
			
			
			PanBottomButtons.setLayout(new GridLayout(1,2));
//			PanBottomButtons.add(btnSave);
			PanBottomButtons.add(btnCancel);
		}

		else{
			PanBottomButtons.add(btnClose);
		}



		this.add(PanPADetails, BorderLayout.CENTER);
		this.add(PanBottomButtons, BorderLayout.SOUTH);




		/*
		 * ************************************************************
		 *******************  MAIN WINDOW SETTINGS  *******************
		 * ************************************************************
		 */
		setSize(400,400);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


	}


	public static boolean isValidInt(String inInt){
		int i;
		try{
			i = Integer.parseInt(inInt);
		} catch(NumberFormatException pe){
			return false;
		}

		if(i<= Calendar.getInstance().get(Calendar.YEAR))
			return true;

		return false;
	}


	public static boolean isValidDate(String inDate) {
		if(inDate.equals("")) return true;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY");
		dateFormat.setLenient(false);
		try {
			dateFormat.parse(inDate.trim());
		} catch (ParseException pe) {
			return false;
		}
		//VERIFY THAT NAISSANCE <= CURRENT TIME.. NEEDS TO BE ADDED
		return true;
	}



	@Override
	public void actionPerformed(ActionEvent ae) {
		// TODO Auto-generated method stub
		String action = ae.getActionCommand();

		//IF CLICK ENTITEMOBILE-SHOWMAP
		if(action.equals("btnCancel") || action.equals("btnClose")){
			this.dispose();
		}

		if(action.equals("btnSave")){

			boolean updatedParticulier = true;
			if(particulierIDTF.getText().equals("") ||  nomTF.getText().equals(""))
			{
				JOptionPane.showMessageDialog(this, "MISSING VALUES FOR PARTICULIER");
				updatedParticulier = false;
			}
			if(updatedParticulier){
				try {
					//UPDATE THE PARTICULIER IN THE DATABASE
					PresetQueries.updateParticulier(conn, pa.getParticulierID(),
							pa.getNom(), pa.getType(), pa.getUsername(), pa.getStreetNumber(), pa.getStreetName(), pa.getCity(), pa.getProvState(), pa.getCountry(), pa.getPostalCodeZip(), pa.getTel());
					
				}  catch (NumberFormatException  | SQLException e) {
					JOptionPane.showMessageDialog(this, e.getMessage());
					updatedParticulier = false;

				}
			}
			
			if(updatedParticulier){
//				gui.refreshGUIFromEM();
				this.dispose();
			}

		}
	}


}
