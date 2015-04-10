package org.geotools.tutorial;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class EMNewGUI extends JFrame implements ActionListener {

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
	private JCheckBox addArtificielVivant;
	private JRadioButton radioArt;
	private JRadioButton radioViv;

	private JPanel PanEMNewArtificiel;
	private JPanel PanEMNewArtificielWRadio;
	
	private JPanel PanEMNewVivant;
	private JPanel PanEMNewVivantWRadio;
	
	private JPanel ArtVivantTogether;
	private JPanel mega; 
	
	private boolean showArtVivOptions = false;


	public EMNewGUI(String s, Connection conn){
		super(s);

		int entiteID = 0;
		try {
			entiteID = PresetQueries.getMaxEMID(conn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		entiteID++;
		System.out.println(entiteID);
		//GET ALL CAPTEUR IDS
		ArrayList<String> capteurIDs = null;
		try {
			capteurIDs = PresetQueries.getAllCapteurIDs(conn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * ************************************************************
		 ********** COMMON PROPERTIES FOR ALL ENTITE MOBILE ***********
		 * ************************************************************
		 */		
		JLabel entiteIDLabel = new JLabel("EntiteID:");
		entiteIDTF = new JLabel(Integer.toString(entiteID));
		JLabel nomLabel = new JLabel("Nom:");
		nomTF = new JTextField();
		JLabel capteurIDLabel = new JLabel("capteurID:");
		capteurIDCB = new JComboBox(capteurIDs.toArray());




		JPanel PanEMNew = new JPanel();
		PanEMNew.setLayout(new GridLayout(4,2));

		PanEMNew.add(entiteIDLabel);
		PanEMNew.add(entiteIDTF);
		PanEMNew.add(nomLabel);
		PanEMNew.add(nomTF);
		PanEMNew.add(capteurIDLabel);
		PanEMNew.add(capteurIDCB);

		addArtificielVivant = new JCheckBox("Add Vivant/Artificiel");
		PanEMNew.add(addArtificielVivant);
		addArtificielVivant.addActionListener(this);
		addArtificielVivant.setActionCommand("Checkbox");
		addArtificielVivant.setSelected(false);






		/*
		 * SET UP FOR ARTIFICIEL
		 */


		PanEMNewArtificiel = new JPanel();
		
		radioArt = new JRadioButton("Artificiel");

		
		PanEMNewArtificiel.setLayout(new GridLayout(6,2));
		//PanEMNewArtificiel.add(radioArt);
		//PanEMNewArtificiel.add(new JPanel());
		
		
		JLabel artificielMarqueLabel= new JLabel("Marque:");
		artificielMarqueTF = new JTextField();
		JLabel artificielModeleLabel= new JLabel("Modele:");
		artificielModeleTF = new JTextField();
		JLabel artificielAnneeFabricationLabel = new JLabel("Annee Fabrication:");
		artificielAnneeFabricationTF = new JTextField();
		JLabel artificielPuissanceLabel = new JLabel("Puissance:");
		artificielPuissanceTF = new JTextField();
		JLabel artificielCombustibleLabel = new JLabel("Combustible:");
		artificielCombustibleTF = new JTextField();
		JLabel artificielTypeMachineLabel = new JLabel("Type of Machine:");
		artificielTypeMachineTF = new JTextField();

		PanEMNewArtificiel.add(artificielMarqueLabel);
		PanEMNewArtificiel.add(artificielMarqueTF);
		PanEMNewArtificiel.add(artificielModeleLabel);
		PanEMNewArtificiel.add(artificielModeleTF);
		PanEMNewArtificiel.add(artificielAnneeFabricationLabel);
		PanEMNewArtificiel.add(artificielAnneeFabricationTF);
		PanEMNewArtificiel.add(artificielPuissanceLabel);
		PanEMNewArtificiel.add(artificielPuissanceTF);
		PanEMNewArtificiel.add(artificielCombustibleLabel);
		PanEMNewArtificiel.add(artificielCombustibleTF);
		PanEMNewArtificiel.add(artificielTypeMachineLabel);
		PanEMNewArtificiel.add(artificielTypeMachineTF);







		
		
		


		
		/*
		 * SET UP FOR VIVANT
		 */
		//Vivant v = (Vivant) em;
		
		radioViv = new JRadioButton("Vivant");
		
		PanEMNewVivant = new JPanel();
		PanEMNewVivant.setLayout(new GridLayout(6,2));
		//PanEMNewVivant.add(radioViv);
		//PanEMNewVivant.add(new JPanel());
		
		
		JLabel vivantDateNaissanceLabel= new JLabel("Date Naissance:");
		vivantDateNaissanceTF = new JTextField();
		JLabel vivantDateDecesLabel = new JLabel("Date Deces:");
		vivantDateDecesTF = new JTextField();
		JLabel vivantEspeceLabel = new JLabel("Espece:");
		vivantEspeceTF = new JTextField();


		PanEMNewVivant.add(vivantDateNaissanceLabel);
		PanEMNewVivant.add(vivantDateNaissanceTF);
		PanEMNewVivant.add(vivantDateDecesLabel);
		PanEMNewVivant.add(vivantDateDecesTF);
		PanEMNewVivant.add(vivantEspeceLabel);
		PanEMNewVivant.add(vivantEspeceTF);
		PanEMNewVivant.add(new JPanel());
		PanEMNewVivant.add(new JPanel());
		PanEMNewVivant.add(new JPanel());
		PanEMNewVivant.add(new JPanel());
		PanEMNewVivant.add(new JPanel());
		PanEMNewVivant.add(new JPanel());
		


		


		
		
		
		JPanel NewPanel = new JPanel();
		NewPanel.setLayout(new GridLayout(1,2));
		NewPanel.add(radioArt);
		NewPanel.add(radioViv);
		
		ArtVivantTogether = new JPanel();
		ArtVivantTogether.setLayout(new GridLayout(1, 2));
		ArtVivantTogether.add(PanEMNewArtificiel);
		ArtVivantTogether.add(PanEMNewVivant);
		ArtVivantTogether.setVisible(true);
		
		
		mega = new JPanel();
		mega.setLayout(new BorderLayout());
		mega.add(NewPanel, BorderLayout.NORTH);
		mega.add(ArtVivantTogether, BorderLayout.CENTER);
		mega.setVisible(false);
		this.add(PanEMNew, BorderLayout.NORTH);
		//PanEMRadio.add(PanEMNewArtificiel);
		this.add(mega, BorderLayout.CENTER);
		
		setSize(600,400);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	}


	@Override
	public void actionPerformed(ActionEvent ae) {
		String action = ae.getActionCommand();

		if(action.equals("Checkbox") )
		{
			if(showArtVivOptions == false){
				mega.setVisible(true);
				showArtVivOptions = true;
			}
			else if (showArtVivOptions == true){
				showArtVivOptions = false;
				mega.setVisible(false);

			}
		}
		// TODO Auto-generated method stub

	}

}
