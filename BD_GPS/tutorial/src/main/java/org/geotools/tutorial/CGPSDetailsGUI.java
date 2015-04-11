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




public class CGPSDetailsGUI extends JFrame implements ActionListener {
	private String EMType="";


	private JLabel capteurID;
	private JTextField modeleTF;
	private JTextField fabricantTF;
	private JTextField precisionGPSTF;
	private JTextField dateDebutTF;
	private JTextField dateFinTF;
	private String editableFlag;
	private Connection conn;
	private GUI gui;
	private Capteur_GPS cgps;

	private UtilDateModel modelDateDebut;
	private UtilDateModel modelDateFin;

	private Properties p;

	public CGPSDetailsGUI(String s, Capteur_GPS cgps, String editableFlag, Connection conn, GUI gui) throws SQLException{
		super(s);

		this.conn = conn;
		this.gui = gui;
		this.editableFlag = editableFlag;
		this.cgps = cgps;
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



		p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");


		modelDateDebut = new UtilDateModel();
		if(editableFlag.equals("Edit"))
			modelDateDebut.setValue(cgps.getDateDebut());
		else
			modelDateDebut.setValue(new Date());

		JDatePanelImpl datePanelDateDebut = new JDatePanelImpl(modelDateDebut, p);
		JDatePickerImpl datePickerDateDebut = new JDatePickerImpl(datePanelDateDebut, new DateLabelFormatter());


		modelDateFin= new UtilDateModel();
		if(editableFlag.equals("Edit"))
			modelDateFin.setValue(cgps.getDateFin());
		else
			modelDateFin.setValue(new Date());

		JDatePanelImpl datePanelDateFin = new JDatePanelImpl(modelDateFin, p);
		JDatePickerImpl datePickerDateFin = new JDatePickerImpl(datePanelDateFin, new DateLabelFormatter());


		if(editableFlag.equals("Details")){
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
		if(editableFlag.equals("Details"))
			PanCGPSDetails.add(dateDebutTF);
		else
			PanCGPSDetails.add(datePickerDateDebut);
		//		PanCGPSDetails.add(dateDebutTFLabel);
		//		//PanCGPSDetails.add(dateDebutTF);
		PanCGPSDetails.add(dateFinTFLabel);

		if(editableFlag.equals("Details"))
			PanCGPSDetails.add(dateFinTF);
		else
			PanCGPSDetails.add(datePickerDateFin);

		//PanCGPSDetails.add(dateFinTF);



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

		if(editableFlag.equals("New") || editableFlag.equals("Edit")){
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






	@Override
	public void actionPerformed(ActionEvent ae) {
		//		// TODO Auto-generated method stub
		String action = ae.getActionCommand();

		//IF CLICK ENTITEMOBILE-SHOWMAP
		if(action.equals("btnCancel") || action.equals("btnClose")){
			this.dispose();
		}

		if(action.equals("btnSave")){
			if(editableFlag.equals("Edit")){
				boolean success = true;

				if(capteurID.getText().equals("") || 
						modeleTF.getText().equals("") ||
						fabricantTF.getText().equals("") ||
						precisionGPSTF.getText().equals("")){
					JOptionPane.showMessageDialog(this, "All fields must be filled!");
					success = false;
				}



				if(success){
					cgps.setCaptID(Integer.parseInt(capteurID.getText()));
					cgps.setModel(modeleTF.getText());
					cgps.setFabricant(fabricantTF.getText());
					cgps.setPrecisionGPS(precisionGPSTF.getText());
					cgps.setDateDebut(modelDateDebut.getValue());
					cgps.setDateFin(modelDateFin.getValue());

					try {
						PresetQueries.updateCapteurGPS(conn, cgps.getCaptID(),cgps.getModel(), cgps.getFabricant(), cgps.getPrecisionGPS(), cgps.getDateDebutString(), cgps.getDateFinString());
					}  catch (NumberFormatException | SQLException e) {
						JOptionPane.showMessageDialog(this, e.getMessage());
						success = false;
					}
				}

				if(success){
					gui.refreshGUIFromCGPS();
					this.dispose();
				}
				//				}	
			}
			else if(editableFlag.equals("New")){
				boolean success = true;
				if(capteurID.getText().equals("") || 
						modeleTF.getText().equals("") ||
						fabricantTF.getText().equals("") ||
						precisionGPSTF.getText().equals(""))
				{
					JOptionPane.showMessageDialog(this, "All fields must be filled!");
					success = false;
				}
				
				if(success){
					cgps.setCaptID(Integer.parseInt(capteurID.getText()));
					cgps.setModel(modeleTF.getText());
					cgps.setFabricant(fabricantTF.getText());
					cgps.setPrecisionGPS(precisionGPSTF.getText());
					cgps.setDateDebut(modelDateDebut.getValue());
					cgps.setDateFin(modelDateFin.getValue());

					try {
						PresetQueries.newCapteurGPS(conn, cgps);
					}  catch (NumberFormatException | SQLException e) {
						JOptionPane.showMessageDialog(this, e.getMessage());
						success = false;
					}
				}
				
				if(success){
					gui.refreshGUIFromCGPS();
					this.dispose();
				}
				
				
				
				
				
			}
		}
	}

	//	public void callBackGUI(CallBackFromCGPS callback){
	//		GUI.refreshGUIFromCGPS()
	//	}




}
