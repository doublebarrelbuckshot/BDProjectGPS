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

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CGPSDetailsGUI extends JFrame implements ActionListener {
	private String EMType="";


	private JLabel capteurID;
	private JTextField modeleTF;
	private JTextField fabricantTF;
	private JTextField precisionGPSTF;
	private JTextField dateDebutTF;
	private JTextField dateFinTF;

	private Connection conn;
private GUI gui;

	public CGPSDetailsGUI(String s, Capteur_GPS cgps, Boolean editableFlag, Connection conn, GUI gui) throws SQLException{
		super(s);

		this.conn = conn;
		this.gui = gui;
		//SET UP MAIN PANEL OF INFO

		JPanel PanCGPSDetails = new JPanel();



		PanCGPSDetails.setLayout(new GridLayout(6,2));


		JLabel capteurIDLabel = new JLabel("Capteur ID:");
		capteurID = new JLabel(Integer.toString(cgps.getCaptID()));
		JLabel modeleLabel = new JLabel("Modele:");
		modeleTF = new JTextField(cgps.getModel());
		JLabel fabricantLabel = new JLabel("Fabricant:");
		fabricantTF = new JTextField(cgps.getFabricant());
		JLabel precisionGPSLabel = new JLabel("Precision GPS:");
		precisionGPSTF = new JTextField(cgps.getPrecisionGPS());
		JLabel dateDebutTFLabel = new JLabel("Date Debut:");
		dateDebutTF = new JTextField(cgps.getDateDebutString());
		JLabel dateFinTFLabel = new JLabel("Date Fin:");
		dateFinTF = new JTextField(cgps.getDateFinString());


		if(!editableFlag){
			modeleTF.setEditable(false);
			fabricantTF.setEditable(false);
			precisionGPSTF.setEditable(false);
			dateDebutTF.setEditable(false);
			dateFinTF.setEditable(false);

		}

		PanCGPSDetails.add(capteurIDLabel);
		PanCGPSDetails.add(capteurID);
		PanCGPSDetails.add(modeleLabel);
		PanCGPSDetails.add(modeleTF);
		PanCGPSDetails.add(fabricantLabel);
		PanCGPSDetails.add(fabricantTF);
		PanCGPSDetails.add(precisionGPSLabel);
		PanCGPSDetails.add(precisionGPSTF);
		PanCGPSDetails.add(dateDebutTFLabel);
		PanCGPSDetails.add(dateDebutTF);
		PanCGPSDetails.add(dateFinTFLabel);
		PanCGPSDetails.add(dateFinTF);


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
			PanBottomButtons.add(btnSave);
			PanBottomButtons.add(btnCancel);
		}

		else{
			PanBottomButtons.add(btnClose);
		}

		this.add(PanCGPSDetails, BorderLayout.CENTER);
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
		//		// TODO Auto-generated method stub
		String action = ae.getActionCommand();

		//IF CLICK ENTITEMOBILE-SHOWMAP
		if(action.equals("btnCancel") || action.equals("btnClose")){
			this.dispose();
		}

		if(action.equals("btnSave")){
			boolean success = true;

			try {
				PresetQueries.updateCapteurGPS(conn, Integer.parseInt(capteurID.getText()), modeleTF.getText(), fabricantTF.getText(), precisionGPSTF.getText(), dateDebutTF.getText(), dateFinTF.getText());
			}  catch (NumberFormatException | SQLException e) {
				JOptionPane.showMessageDialog(this, e.getMessage());
				success = false;
			}

			if(success){
				gui.refreshGUIFromCGPS();
				this.dispose();
			}
			//				}	
		}
	}
	
//	public void callBackGUI(CallBackFromCGPS callback){
//		GUI.refreshGUIFromCGPS()
//	}




}
