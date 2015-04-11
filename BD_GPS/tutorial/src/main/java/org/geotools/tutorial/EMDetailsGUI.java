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

public class EMDetailsGUI extends JFrame implements ActionListener {
	private String EMType="";


	private JLabel entiteIDTF;
	private JTextField nomTF;
	private JComboBox capteurIDCB;
	private JTextField vivantDateNaissanceTF;
	private JTextField vivantDateDecesTF;
	private JTextField vivantEspeceTF;
	private JTextField artificielMarqueTF;
	private JTextField artificielModeleTF;
	private JTextField artificielAnneeFabricationTF;
	private JTextField artificielPuissanceTF;
	private JTextField artificielCombustibleTF;
	private JTextField artificielTypeMachineTF;
	private Connection conn;


	private UtilDateModel modelDateNaissance;
	private UtilDateModel modelDateDeces;
	private Properties p;
	private EntiteMobile em;
	private GUI gui;

	public EMDetailsGUI(String s, EntiteMobile em, Boolean editableFlag, Connection conn, GUI gui) throws SQLException{
		super(s);

		this.conn = conn;
		this.em = em;
		this.gui = gui;
		//SET UP MAIN PANEL OF INFO

		JPanel PanEMDetails = new JPanel();

		if(em instanceof Vivant){
			EMType = "Vivant";
			PanEMDetails.setLayout(new GridLayout(6,2));

			//System.out.println("It's vivant!");
		}
		else if(em instanceof Artificiel){
			EMType = "Artificiel";
			PanEMDetails.setLayout(new GridLayout(9,2));

			//System.out.println("It's artificiel!");
		}

		else if (em instanceof EntiteMobile){
			EMType = "EntiteMobile";
			PanEMDetails.setLayout(new GridLayout(3,2));
			//System.out.println("It's EM Pure and Simple!");
		}




		ArrayList<String> capteurIDs = PresetQueries.getAllCapteurIDs(conn);

		/*
		 * ************************************************************
		 ********** COMMON PROPERTIES FOR ALL ENTITE MOBILE ***********
		 * ************************************************************
		 */		
		JLabel entiteIDLabel = new JLabel("EntiteID:");
		entiteIDTF = new JLabel(Integer.toString(em.getEntiteID()));
		JLabel nomLabel = new JLabel("Nom:");
		nomTF = new JTextField(em.getNom());
		JLabel capteurIDLabel = new JLabel("capteurID:");
		capteurIDCB = new JComboBox(capteurIDs.toArray());
		//set current capteurID
		int index = 0;
		for(int i=0; i<capteurIDs.size(); i++){
			if(capteurIDs.get(i).equals(Integer.toString(em.getCapteurID()))){
				index = i;
				break;
			}
		}
		capteurIDCB.setSelectedIndex(index);

		if(!editableFlag){
			nomTF.setEditable(false);
			capteurIDCB.setEnabled(false);
		}

		PanEMDetails.add(entiteIDLabel);
		PanEMDetails.add(entiteIDTF);
		PanEMDetails.add(nomLabel);
		PanEMDetails.add(nomTF);
		PanEMDetails.add(capteurIDLabel);
		PanEMDetails.add(capteurIDCB);


		/*
		 * ************************************************************
		 ******************* PROPERTIES FOR VIVANT ********************
		 * ************************************************************
		 */		
		if(EMType.equals("Vivant")){
			Vivant v = (Vivant) em;
			JLabel vivantDateNaissanceLabel= new JLabel("Date Naissance:");
			vivantDateNaissanceTF = new JTextField(v.getDateNaissanceString());
			JLabel vivantDateDecesLabel = new JLabel("Date Deces:");
			vivantDateDecesTF = new JTextField(v.getDateDecesString());
			JLabel vivantEspeceLabel = new JLabel("Espece:");
			vivantEspeceTF = new JTextField(v.getEspece());




			p = new Properties();
			p.put("text.today", "Today");
			p.put("text.month", "Month");
			p.put("text.year", "Year");


			modelDateNaissance = new UtilDateModel();
			modelDateDeces= new UtilDateModel();

			JDatePanelImpl datePanelDateNaissance= null;
			JDatePickerImpl datePickerDateNaissance = null;

			JDatePanelImpl datePanelDateDeces = null;
			JDatePickerImpl datePickerDateDeces= null;

			if(editableFlag){
				modelDateNaissance.setValue(v.getDateNaissance());
				modelDateDeces.setValue(v.getDateDeces());
				datePanelDateNaissance = new JDatePanelImpl(modelDateNaissance, p);
				datePickerDateNaissance = new JDatePickerImpl(datePanelDateNaissance, new DateLabelFormatter());

				datePanelDateDeces = new JDatePanelImpl(modelDateDeces, p);
				datePickerDateDeces = new JDatePickerImpl(datePanelDateDeces, new DateLabelFormatter());
			}






			PanEMDetails.add(vivantDateNaissanceLabel);
			if(editableFlag)
				PanEMDetails.add(datePickerDateNaissance);
			else
				PanEMDetails.add(vivantDateNaissanceTF);
			PanEMDetails.add(vivantDateDecesLabel);

			if(editableFlag)
				PanEMDetails.add(datePickerDateDeces);
			else
				PanEMDetails.add(vivantDateDecesTF);

			PanEMDetails.add(vivantEspeceLabel);
			PanEMDetails.add(vivantEspeceTF);


			if(!editableFlag){
				vivantDateNaissanceTF.setEditable(false);
				vivantDateDecesTF.setEditable(false);
				vivantEspeceTF.setEditable(false);

			}

		}


		/*
		 * ************************************************************
		 ***************** PROPERTIES FOR ARTIFICIEL ******************
		 * ************************************************************
		 */	

		else if(EMType.equals("Artificiel")){
			Artificiel a = (Artificiel) em;
			JLabel artificielMarqueLabel= new JLabel("Marque:");
			artificielMarqueTF = new JTextField(a.getMarque());
			JLabel artificielModeleLabel= new JLabel("Modele:");
			artificielModeleTF = new JTextField(a.getModele());
			JLabel artificielAnneeFabricationLabel = new JLabel("Annee Fabrication:");
			artificielAnneeFabricationTF = new JTextField(Integer.toString(a.getAnneeFabrication()));
			JLabel artificielPuissanceLabel = new JLabel("Puissance:");
			artificielPuissanceTF = new JTextField(a.getPuissance());
			JLabel artificielCombustibleLabel = new JLabel("Combustible:");
			artificielCombustibleTF = new JTextField(a.getCombustible());
			JLabel artificielTypeMachineLabel = new JLabel("Type of Machine:");
			artificielTypeMachineTF = new JTextField(a.getTypeMachine());

			PanEMDetails.add(artificielMarqueLabel);
			PanEMDetails.add(artificielMarqueTF);
			PanEMDetails.add(artificielModeleLabel);
			PanEMDetails.add(artificielModeleTF);
			PanEMDetails.add(artificielAnneeFabricationLabel);
			PanEMDetails.add(artificielAnneeFabricationTF);
			PanEMDetails.add(artificielPuissanceLabel);
			PanEMDetails.add(artificielPuissanceTF);
			PanEMDetails.add(artificielCombustibleLabel);
			PanEMDetails.add(artificielCombustibleTF);
			PanEMDetails.add(artificielTypeMachineLabel);
			PanEMDetails.add(artificielTypeMachineTF);


			if(!editableFlag){
				artificielMarqueTF.setEditable(false);
				artificielModeleTF.setEditable(false);
				artificielAnneeFabricationTF.setEditable(false);
				artificielPuissanceTF.setEditable(false);
				artificielCombustibleTF.setEditable(false);
				artificielTypeMachineTF.setEditable(false);
			}

		}


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







		this.add(PanEMDetails, BorderLayout.CENTER);
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
			boolean success = true;
			boolean updatedEntiteMobile = true;
			if(entiteIDTF.getText().equals("") ||  nomTF.getText().equals(""))
			{
				JOptionPane.showMessageDialog(this, "MISSING VALUES FOR ENTITEMOBILE");
				updatedEntiteMobile = false;
			}
			if(updatedEntiteMobile){
				try {
					PresetQueries.updateEntiteMobile(conn, Integer.parseInt(entiteIDTF.getText()), nomTF.getText(), Integer.parseInt(capteurIDCB.getSelectedItem().toString()));
				}  catch (NumberFormatException | SQLException e) {
					JOptionPane.showMessageDialog(this, e.getMessage());
					updatedEntiteMobile = false;
				}
			}
			if(EMType.equals("Artificiel") && updatedEntiteMobile){
				if(entiteIDTF.getText().equals("") || artificielMarqueTF.getText().equals("")
						|| artificielAnneeFabricationTF.getText().equals("")
						|| artificielPuissanceTF.getText().equals("")
						|| artificielModeleTF.getText().equals("")
						|| artificielCombustibleTF.getText().equals("")
						|| artificielTypeMachineTF.getText().equals(""))
				{
					JOptionPane.showMessageDialog(this, "MISSING VALUES FOR ARTIFICIEL");
					success = false;
				}
				if( isValidInt(artificielAnneeFabricationTF.getText()) == false){
					JOptionPane.showMessageDialog(this, "Enter a valid Annee Fabrication!");
					success = false;
				}

				if(success){
					try {
						PresetQueries.updateArtificiel(conn, Integer.parseInt(entiteIDTF.getText()), 
								artificielMarqueTF.getText(), artificielModeleTF.getText(), 
								Integer.parseInt(artificielAnneeFabricationTF.getText()), artificielPuissanceTF.getText(),
								artificielCombustibleTF.getText(), artificielTypeMachineTF.getText());
					} catch (NumberFormatException | SQLException e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(this, e.getMessage());
						success = false;
					}
					if(success){
						gui.refreshGUIFromEM();
						this.dispose();

					}
				}	
			}


			if(EMType.equals("Vivant") && updatedEntiteMobile){
				//				if( vivantDateNaissanceTF.getText().equals("") || isValidDate(vivantDateNaissanceTF.getText()) == false || isValidDate(vivantDateDecesTF.getText()) == false)
				//				{
				//					JOptionPane.showMessageDialog(this, "Ensure Date de Naissance and Date Deces are properly formatted");
				//				}
				//else{

				if(entiteIDTF.getText().equals("") || vivantEspeceTF.getText().equals("") )
				{
					JOptionPane.showMessageDialog(this, "MISSING VALUES FOR VIVANT");
					success = false;

				}
				if(success){

					try {
						PresetQueries.updateVivant(conn, Integer.parseInt(entiteIDTF.getText()), 
								modelDateNaissance.getValue(), modelDateDeces.getValue(), vivantEspeceTF.getText());
					} catch (NumberFormatException | SQLException e) {
						JOptionPane.showMessageDialog(this, e.getMessage());
						success = false;
					}
				}
				if(success){
					gui.refreshGUIFromEM();
					this.dispose();
				}
				//}
			}
			if(EMType.equals("EntiteMobile"))
			{
				if(success){
					gui.refreshGUIFromEM();
					this.dispose();
				}
			}
		}
	}


}
