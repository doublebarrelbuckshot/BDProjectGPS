package org.geotools.tutorial;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
	private boolean isVivant = false;
	private ArrayList<String> capteurIDs;
	private Connection conn;

	public EMNewGUI(String s, Connection conn){
		super(s);
		this.conn = conn;
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
		capteurIDs = null;
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
		this.capteurIDCB = new JComboBox(capteurIDs.toArray());




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
		JLabel artificielAnneeFabricationLabel = new JLabel("Annee Fabrication: (YYYY)");
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


		JLabel vivantDateNaissanceLabel= new JLabel("<html>Date Naissance: <br/>(DD/MM/YYYY)</html>");
		vivantDateNaissanceTF = new JTextField();
		JLabel vivantDateDecesLabel = new JLabel("<html>Date Deces: <br/>(DD/MM/YYYY)</html>");
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









		JPanel PanRadios = new JPanel();
		PanRadios.setLayout(new GridLayout(1,2));
		PanRadios.add(radioArt);
		PanRadios.add(radioViv);

		ArtVivantTogether = new JPanel();
		ArtVivantTogether.setLayout(new GridLayout(1, 2));
		ArtVivantTogether.add(PanEMNewArtificiel);
		ArtVivantTogether.add(PanEMNewVivant);
		ArtVivantTogether.setVisible(true);


		/*
		 * SORT OUT RADIO BUTTONS
		 */
		ButtonGroup bg = new ButtonGroup();
		bg.add(radioViv);
		bg.add(radioArt);
		radioArt.setSelected(true);

		radioViv.setActionCommand("radioViv");
		radioArt.setActionCommand("radioArt");

		radioViv.addActionListener(this);
		radioArt.addActionListener(this);



		/*
		 * CODE FOR BUTTONS AT BOTTOM
		 */

		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(this);
		btnSave.setActionCommand("btnSave");

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(this);
		btnCancel.setActionCommand("btnCancel");




		JPanel PanBottomButtons = new JPanel();

		PanBottomButtons.setLayout(new GridLayout(1,2));
		PanBottomButtons.add(btnSave);
		PanBottomButtons.add(btnCancel);




		mega = new JPanel();
		mega.setLayout(new BorderLayout());
		mega.add(PanRadios, BorderLayout.NORTH);
		mega.add(ArtVivantTogether, BorderLayout.CENTER);
		mega.setVisible(false);
		this.add(PanEMNew, BorderLayout.NORTH);
		//PanEMRadio.add(PanEMNewArtificiel);
		this.add(mega, BorderLayout.CENTER);
		this.add(PanBottomButtons, BorderLayout.SOUTH);
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
		if(action.equals("radioArt")){
			isVivant = false;

		}

		if(action.equals("radioViv")){
			isVivant = true;

		}

		if(action.equals("btnCancel"))
		{
			this.dispose();
		}
		if(action.equalsIgnoreCase("btnSave")){

			boolean valid = true;


			EntiteMobile em = new EntiteMobile();
			Vivant viv = new Vivant();
			Artificiel art = new Artificiel();

			//if(!showArtVivOptions){
			em.setEntiteID(Integer.parseInt(entiteIDTF.getText()));
			System.out.println(Integer.parseInt(capteurIDs.get(this.capteurIDCB.getSelectedIndex())) + "");
			em.setCapteurID(Integer.parseInt(capteurIDs.get(this.capteurIDCB.getSelectedIndex())));
			em.setNom(nomTF.getText());


			//}

			if(showArtVivOptions){
				if(isVivant){
					viv.setEntiteID(Integer.parseInt(entiteIDTF.getText()));
					viv.setEspece(vivantEspeceTF.getText());
					try {
						viv.setDateDeces(vivantDateDecesTF.getText());
						viv.setDateNaissance(vivantDateNaissanceTF.getText());

					} catch (Exception pe) {
						JOptionPane.showMessageDialog(this, "Problem with date format!, use format DD/MM/YYYY");
						valid = false;
					}
					


				}
				else{ //it's artificiel
					try{
						art.setEntiteID(Integer.parseInt(entiteIDTF.getText()));
						art.setMarque(artificielMarqueTF.getText());
						art.setModele(artificielModeleTF.getText());
						art.setAnneeFabrication(Integer.parseInt(artificielAnneeFabricationTF.getText()));
						art.setPuissance(artificielPuissanceTF.getText());
						art.setCombustible(artificielCombustibleTF.getText());
						art.setTypeMachine(artificielTypeMachineTF.getText());
					}
					catch(Exception e){
						JOptionPane.showMessageDialog(this, "Problem Annee Fabrication!");
						valid = false;;
					}


				}
			}
			if(valid){


				try {
					PresetQueries.addEM(conn, em);
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(this, "ERROR, EntiteMobile not added");
					valid = false;
					//	e.printStackTrace();
				}
				System.out.println("NEW EM CREATED");

				if (valid && isVivant){
					try {
						PresetQueries.addVivant(conn, viv);
					} catch (SQLException e) {
						JOptionPane.showMessageDialog(this, "ERROR, Vivant and EntiteMobile not added");
						valid = false;
						try {
							PresetQueries.deleteEM(conn, em);
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
					}
					System.out.println("NEW VIVANT CREATED");
				}
				
				if(valid && !isVivant){ //add artificiel
					try {
						PresetQueries.addArtificiel(conn, art);
					} catch (SQLException e) {
						JOptionPane.showMessageDialog(this, "ERROR, ARTIFICIEL and EntiteMobile not added");
						valid = false;
						try {
							PresetQueries.deleteEM(conn, em);
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						e.printStackTrace();
					}
					System.out.println("NEW ARTIFICIEL CREATED");
				}



			}
			if(valid)
				this.dispose();

		}

	}

}
