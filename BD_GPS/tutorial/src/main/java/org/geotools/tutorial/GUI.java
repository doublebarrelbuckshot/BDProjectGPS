package org.geotools.tutorial;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;



///test Florin 16:07
public class GUI extends JFrame implements ListSelectionListener, ActionListener{

	private Connection conn;
	private int capteurIWantToMap = 101;
	private Boolean programChanged = false;
	private static String viewType;
	private static int ID;



	private ArrayList<EntiteMobile> entiteMobile;
	private ArrayList<Capteur_GPS> capteurGPS;
	private ArrayList<Particulier> particulier;


	private final JList<String> listCapteurs;
	private JList<String> listParticuliers;
	private JList<String> listEntiteMobiles;

	private final DefaultListModel<String> emptyModel = new DefaultListModel<>();
	private final DefaultListModel<String> capteursDefaultModel = new DefaultListModel<>();
	private final DefaultListModel<String> particuliersDefaultModel = new DefaultListModel<>();
	private DefaultListModel<String>entiteMobilesDefaultModel = new DefaultListModel<>();

	
	
	public void refreshGUIFromParticulier(){
		
		particuliersDefaultModel.removeAllElements();
		Particulier.listParticulier.clear();

		
		try {
			PresetQueries.getAllParticulier(conn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		Particulier.listParticulier.stream().forEach((item) -> {
			particuliersDefaultModel.addElement(item.printListString());
		});
		
		listParticuliers.setModel(particuliersDefaultModel);
		listParticuliers.repaint();
		

	}
public void  refreshGUIFromCGPS(){
		try {
			PresetQueries.getAllCapteur_GPS(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		capteursDefaultModel.clear();
	Capteur_GPS.listCapteurGPS.stream().forEach((item) ->{
		capteursDefaultModel.addElement(item.printListString());
	});
	listCapteurs.repaint();

}


public void  refreshGUIFromEM(){
	
	if(GUI.viewType == "user"){

		// GET USER NAME, AND SET THE SELECTED PARTICULIER TO THAT USER.
		// THERE IS CURRENTLY NO LOGIN FRAME, SO THIS IF WILL ASSUME UNIVERSITE DE MONTREAL
		//

		try {
			entiteMobile = PresetQueries.getEntiteMobileForParticulier(conn, ID);
			updateEntiteMobileList();
		} 
		catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	} else if (GUI.viewType == "admin") {

		if(listParticuliers.getSelectedIndex() != -1){

			programChanged = true;
			int particulierID = Particulier.listParticulier.get(listParticuliers.getSelectedIndex()).getParticulierID();
			try {
				entiteMobile = PresetQueries.getEntiteMobileForParticulier(conn, particulierID);
				updateEntiteMobileList();
			} 
			catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			programChanged = false;
		}
	}

//	try {
//		PresetQueries.getAllCapteur_GPS(conn);
//	} catch (SQLException e) {
//		e.printStackTrace();
//	}
//	capteursDefaultModel.clear();
//Capteur_GPS.listCapteurGPS.stream().forEach((item) ->{
//	capteursDefaultModel.addElement(item.printListString());
//});
//listCapteurs.repaint();

}


	public  void loadDefaultModels( ) {



		/*
		 * SETUP FOR CLASSES JLIST
		 */
		Particulier.listParticulier.stream().forEach((item) -> {
			particuliersDefaultModel.addElement(item.printListString());
		});

		Capteur_GPS.listCapteurGPS.stream().forEach((item) ->{
			capteursDefaultModel.addElement(item.printListString());
		});

		//listClasses.repaint();
	}


	public GUI(String s, String viewType, int ID, Connection connection) throws Exception{
		super(s);

		GUI.viewType = viewType;
		GUI.ID = ID;

		/*
		 * ************************************************************
		 ******************* INITIALIZE CONNECTION  *******************
		 * ************************************************************
		 */
		
		conn = connection;


		/*
		 * ************************************************************
		 ****************** PRESET QUERIES LAUNCHED *******************
		 * ************************************************************
		 */
		PresetQueries.getAllCapteur_GPS(conn);
		
		PresetQueries.getAllParticulier(conn);



		loadDefaultModels();





		/*
		 * ************************************************************
		 *******************   PARTICULIERS JLIST   *******************
		 * ************************************************************
		 */

		/*
		 * SETUP FOR PARTICULIERS JLIST
		 */
		this.listParticuliers = new JList<>();
		listParticuliers.setModel(particuliersDefaultModel);
		listParticuliers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listParticuliers.addListSelectionListener(this);		

		JScrollPane particulierScroller = new JScrollPane(listParticuliers);
		particulierScroller.setViewportView(listParticuliers);
		particulierScroller.setPreferredSize(new Dimension(250, 450));
		/*
		 * SETUP FOR PARTICULIERS PANEL
		 */
		JPanel particuliersPanel = new JPanel();
		particuliersPanel.setBorder(new TitledBorder("Particuliers"));
		particuliersPanel.add(particulierScroller, BorderLayout.WEST);



		/*
		 * ************************************************************
		 *******************   ENTITE MOBILE JLIST  *******************
		 * ************************************************************
		 */

		/*
		 * SETUP FOR ENTITE MOBILE JLIST
		 */
		this.listEntiteMobiles = new JList<>();
		listEntiteMobiles.setModel(entiteMobilesDefaultModel);
		listEntiteMobiles.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listEntiteMobiles.addListSelectionListener(this);		

		JScrollPane entiteMobileScroller = new JScrollPane(listEntiteMobiles);
		entiteMobileScroller.setViewportView(listEntiteMobiles);
		entiteMobileScroller.setPreferredSize(new Dimension(250, 450));
		/*
		 * SETUP FOR ENTITE MOBILE PANEL
		 */
		JPanel entiteMobilesPanel = new JPanel();
		entiteMobilesPanel.setBorder(new TitledBorder("Entites Mobiles"));
		entiteMobilesPanel.add(entiteMobileScroller, BorderLayout.WEST);




		/*
		 * ************************************************************
		 *******************   CAPTEUR GPS JLIST  *******************
		 * ************************************************************
		 */

		/*
		 * SETUP FOR capteurs JLIST
		 */
		this.listCapteurs = new JList<>();
		listCapteurs.setModel(capteursDefaultModel);
		listCapteurs.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listCapteurs.addListSelectionListener(this);		

		JScrollPane capteurScroller = new JScrollPane(listCapteurs);
		capteurScroller.setViewportView(listCapteurs);
		capteurScroller.setPreferredSize(new Dimension(250, 450));
		/*
		 * SETUP FOR ENTITE MOBILE PANEL
		 */
		JPanel capteursPanel = new JPanel();
		capteursPanel.setBorder(new TitledBorder("Tous les Capteurs GPS"));
		capteursPanel.add(capteurScroller, BorderLayout.WEST);




		/*
		 * ************************************************************
		 ******************* ENTITE MOBILE BUTTONS  *******************
		 * ************************************************************
		 */
		JPanel panEMButtons = new JPanel();
		panEMButtons.setLayout(new GridLayout(4,1));



		/*
		 * Initialize showMap button
		 */
		JButton btnEMShowMap = new JButton("Show Map");
		panEMButtons.add(btnEMShowMap);
		btnEMShowMap.addActionListener(this);
		btnEMShowMap.setActionCommand("btnEMShowMap");

		/*
		 * Initialize details button
		 */
		JButton btnEMDetails = new JButton("Details");
		panEMButtons.add(btnEMDetails);
		btnEMDetails.addActionListener(this);
		btnEMDetails.setActionCommand("btnEMDetails");

		//Only init these buttons if the user is admin
		if(viewType == "admin"){

			/*
			 * Initialize Edit button
			 */
			JButton btnEMEdit = new JButton("Edit");
			panEMButtons.add(btnEMEdit);
			btnEMEdit.addActionListener(this);
			btnEMEdit.setActionCommand("btnEMEdit");

			/*
			 * Initialize NEW button
			 */
			JButton btnEMNew = new JButton("New");
			panEMButtons.add(btnEMNew);
			btnEMNew.addActionListener(this);
			btnEMNew.setActionCommand("btnEMNew");

			//			/*
			//			 * Initialize SHOWALL button
			//			 */
			//			JButton btnEMShowAll = new JButton("Show All");
			//			panEMButtons.add(btnEMShowAll);
			//			btnEMShowAll.addActionListener(this);
			//			btnEMShowAll.setActionCommand("btnEMShowAll");

		}

		entiteMobilesPanel.add(panEMButtons, BorderLayout.SOUTH);

		/*
		 * ************************************************************
		 ******************* PARTICULIER BUTTONS  *******************
		 * ************************************************************
		 */
		/*Only admin sees these buttons. They are added to a panel that is shown only if in admin view*/
		JPanel panPAButtons = new JPanel();
		panPAButtons.setLayout(new GridLayout(4,1));


		/*
		 * Initialize details button
		 */
		JButton btnPADetails = new JButton("Details");
		panPAButtons.add(btnPADetails);
		btnPADetails.addActionListener(this);
		btnPADetails.setActionCommand("btnPADetails");


		/*
		 * Initialize Edit button
		 */
		JButton btnPAEdit = new JButton("Edit");
		panPAButtons.add(btnPAEdit);
		btnPAEdit.addActionListener(this);
		btnPAEdit.setActionCommand("btnPAEdit");

		/*
		 * Initialize NEW button
		 */
		JButton btnPANew = new JButton("New");
		panPAButtons.add(btnPANew);
		btnPANew.addActionListener(this);
		btnPANew.setActionCommand("btnPANew");
		
		/*
		 * Initialize NEW button
		 */
		JButton btnPAAdopt = new JButton("Adopt");
		panPAButtons.add(btnPAAdopt);
		btnPAAdopt.addActionListener(this);
		btnPAAdopt.setActionCommand("btnPAAdopt");

		/*
		 * ADD ALL BUTTONS TO ENTITE MOBILE PANEL
		 */

		particuliersPanel.add(panPAButtons, BorderLayout.SOUTH);




		/*
		 * ************************************************************
		 ******************* CAPTEUR GPS BUTTONS  *******************
		 * ************************************************************
		 */
		JPanel panCGPSButtons = new JPanel();
		panCGPSButtons.setLayout(new GridLayout(4,1));


		/*
		 * Initialize details button
		 */
		JButton btnCGPSDetails = new JButton("Details");
		panCGPSButtons.add(btnCGPSDetails);
		btnCGPSDetails.addActionListener(this);
		btnCGPSDetails.setActionCommand("btnCGPSDetails");

		//Only init these buttons if the user is admin
		if(viewType == "admin"){

			/*
			 * Initialize Edit button
			 */
			JButton btnCGPSEdit = new JButton("Edit");
			panCGPSButtons.add(btnCGPSEdit);
			btnCGPSEdit.addActionListener(this);
			btnCGPSEdit.setActionCommand("btnCGPSEdit");

			/*
			 * Initialize NEW button
			 */
			JButton btnCGPSNew = new JButton("New");
			panCGPSButtons.add(btnCGPSNew);
			btnCGPSNew.addActionListener(this);
			btnCGPSNew.setActionCommand("btnCGPSNew");
			



		}

		capteursPanel.add(panCGPSButtons, BorderLayout.SOUTH);


		/*
		 * ************************************************************
		 ******************* MAIN WINDOW ADD PANEL  *******************
		 * ************************************************************
		 */

		JPanel listsPanel = new JPanel();
		listsPanel.setLayout(new GridLayout(1,3));

		//Only init this particuliers panel if admin
		if(viewType == "admin"){
			listsPanel.add(particuliersPanel);

		}
		listsPanel.add(entiteMobilesPanel);
		
		//Only init this capteursPanel if admin
		if(viewType == "admin"){
			listsPanel.add(capteursPanel);
		}


		

		add(listsPanel, BorderLayout.CENTER);


		/*
		 * ************************************************************
		 *******************  MAIN WINDOW SETTINGS  *******************
		 * ************************************************************
		 */


		//IF IT IS A USER VIEW, THEN POPULATE THE ENTITE LIST WITH THAT USERS' ENTITIES
		//ONCE LOGIN IS ESTABLISHED, CHANGE THIS CODE TO REFLECT USER LOGIN.
		if(GUI.viewType == "user"){

			try {
				entiteMobile = PresetQueries.getEntiteMobileForParticulier(conn, ID);
				updateEntiteMobileList();
			} 
			catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			this.setSize(375,750);	


		} else if (GUI.viewType == "admin"){

			this.setSize(1000,750);	
		}

		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


	}


	/*
	 * ************************************************************
	 *************** LISTENER FOR ALL BUTTON CLICKS ***************
	 * ************************************************************
	 */
	public void actionPerformed(ActionEvent ae){
		String action = ae.getActionCommand();

		//IF CLICK ENTITEMOBILE-SHOWMAP
		if(action.equals("btnEMShowMap")){

			int selectedEM = listEntiteMobiles.getSelectedIndex();
			if(selectedEM == -1){
				JOptionPane.showMessageDialog(this, "Choose an Entite Mobile that you would like to map!");
				return;
			}
			else{
				capteurIWantToMap = entiteMobile.get(selectedEM).getCapteurID();
			}
			try {
				GeoMapWindow.showGeoMapWindow(conn, capteurIWantToMap);
			} 
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//IF CLICK ENTITEMOBILE-DETAILS
		else if(action.equals("btnEMDetails")){
			
			int selectedEMIndex = listEntiteMobiles.getSelectedIndex();
			EntiteMobile selectedEM = null;  
			if(selectedEMIndex == -1){
				JOptionPane.showMessageDialog(this, "Choose an Entite Mobile that you would like to view in detail!");
				return;
			}
			else{
				selectedEM =  entiteMobile.get(selectedEMIndex);
				Boolean editableFlag = false;
				try {
					//Get more details depending on if it's Vivant, Artificiel, or just EntiteMobile
					selectedEM = PresetQueries.getEntiteMobileDetails(conn, selectedEM);
				} 
				catch (SQLException e) {
					e.printStackTrace();
				}

				try {
					EMDetailsGUI newDetails = new EMDetailsGUI("Details: " + selectedEM.getNom(), selectedEM, editableFlag, conn, this);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		//IF CLICK ENTITEMOBILE-EDIT
		else if(action.equals("btnEMEdit")){
			int selectedEMIndex = listEntiteMobiles.getSelectedIndex();
			EntiteMobile selectedEM = null;  
			if(selectedEMIndex == -1){
				JOptionPane.showMessageDialog(this, "Choose an Entite Mobile that you would like to edit!");
				return;
			}
			else{
				selectedEM =  entiteMobile.get(selectedEMIndex);
				Boolean editableFlag = true;

				try {
					//Get more details depending on if it's Vivant, Artificiel, or just EntiteMobile
					selectedEM = PresetQueries.getEntiteMobileDetails(conn, selectedEM); 				
				} 
				catch (SQLException e) {
					e.printStackTrace();
				}

				try {
					EMDetailsGUI newDetails = new EMDetailsGUI("Details: " + selectedEM.getNom(), selectedEM, editableFlag, conn, this);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		else if(action.equals("btnEMNew")){

			EMNewGUI newEM = new EMNewGUI("ADD NEW GUI", conn, this);
		}
		
		else if(action.equals("btnCGPSDetails")){
			//CGPSDetailsGUI newCGPS = new CGPSDetailsGUI("Details: " + selectedCGPS.getNom(), selectedCGPS, editableFlag, conn);
			int selectedCGPSIndex = listCapteurs.getSelectedIndex();
			Capteur_GPS selectedCGPS = null;  
			if(selectedCGPSIndex == -1){
				JOptionPane.showMessageDialog(this, "Choose an Capteur GPS that you would like to view in detail!");
				return;
			}
			else{
				selectedCGPS =  Capteur_GPS.listCapteurGPS.get(selectedCGPSIndex);
				String editableFlag = "Details";
//				try {
//					//Get more details depending on if it's Vivant, Artificiel, or just EntiteMobile
//					selectedCGPS = PresetQueries.getCapteurGPSDetails(conn, selectedCGPS);
//				} 
//				catch (SQLException e) {
//					e.printStackTrace();
//				}

				try {
					CGPSDetailsGUI newDetails = new CGPSDetailsGUI("Details: " + selectedCGPS.getCaptID() + " : " + selectedCGPS.getModel(), selectedCGPS, editableFlag, conn, this);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		else if(action.equals("btnCGPSEdit")){
			//CGPSDetailsGUI newCGPS = new CGPSDetailsGUI("Details: " + selectedCGPS.getNom(), selectedCGPS, editableFlag, conn);
			int selectedCGPSIndex = listCapteurs.getSelectedIndex();
			Capteur_GPS selectedCGPS = null;  
			if(selectedCGPSIndex == -1){
				JOptionPane.showMessageDialog(this, "Choose an Capteur GPS that you would like to view in detail!");
				return;
			}
			else{
				selectedCGPS =  Capteur_GPS.listCapteurGPS.get(selectedCGPSIndex);
				String editableFlag = "Edit";
//				try {
//					//Get more details depending on if it's Vivant, Artificiel, or just EntiteMobile
//					selectedCGPS = PresetQueries.getCapteurGPSDetails(conn, selectedCGPS);
//				} 
//				catch (SQLException e) {
//					e.printStackTrace();
//				}

				try {
					CGPSDetailsGUI newDetails = new CGPSDetailsGUI("Details: " + selectedCGPS.getCaptID() + " : " + selectedCGPS.getModel(), selectedCGPS, editableFlag, conn, this);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		else if(action.equals("btnCGPSNew")){
			int newCGPSID = 0;
			try {
				newCGPSID = PresetQueries.getMaxCGPSID(conn);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			newCGPSID++;
			Capteur_GPS newCGPS = new Capteur_GPS();
			newCGPS.setCaptID(newCGPSID);
			String editableFlag = "New";
			try {
				CGPSDetailsGUI newDetails = new CGPSDetailsGUI("New Capteur_GPS: " + newCGPS.getCaptID(), newCGPS, editableFlag, conn, this);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		//		else if(action.equals("btnEMShowAll")){
		//			listParticuliers.clearSelection();
		//			listEntiteMobiles.setModel(entiteMobilesDefaultModel);
		//			//listParticuliers.repaint();
		//			
		//		}

		else if(action.equals("btnPADetails")){
			//JOptionPane.showMessageDialog(this, "This button (Details) is disabled because of a bug with the oracle driver");
			
			
			int selectedPAIndex = listParticuliers.getSelectedIndex();
			Particulier selectedPA = null;  
			if(selectedPAIndex == -1){
				JOptionPane.showMessageDialog(this, "Choose a particulier that you would like to view in detail!");
				return;
			}
			else{
				
				selectedPA = Particulier.listParticulier.get(selectedPAIndex);
				//System.out.println("SELECTED PA" + Particulier.listParticulier.get(selectedPAIndex));
						
				Boolean editableFlag = false;
				try {
					//Get more details depending on if it's Vivant, Artificiel, or just EntiteMobile
					selectedPA = PresetQueries.getParticulierDetails(conn, selectedPA.getParticulierID());
					
				} 
				catch (SQLException e) {
					e.printStackTrace();
				}

				try {
					PADetailsGUI newDetails = new PADetailsGUI("Details: " + selectedPA.getNom(), selectedPA, editableFlag, conn, this);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} 
		}
		else if(action.equals("btnPAEdit")){
			
			//JOptionPane.showMessageDialog(this, "This button (Edit) is disabled because of a bug with the oracle driver");
			
			
			int selectedPAIndex = listParticuliers.getSelectedIndex();
			Particulier selectedPA = null;  
			if(selectedPAIndex == -1){
				JOptionPane.showMessageDialog(this, "Choose a particulier that you would like to view in detail!");
				return;
			}
			else{
				selectedPA = Particulier.listParticulier.get(selectedPAIndex);
				Boolean editableFlag = true;
				try {
					//Get more details depending on if it's Vivant, Artificiel, or just EntiteMobile
					selectedPA = PresetQueries.getParticulierDetails(conn, selectedPA.getParticulierID());
					
				} 
				catch (SQLException e) {
					e.printStackTrace();
				}

				try {
					PADetailsGUI newDetails = new PADetailsGUI("Details: " + selectedPA.getNom(), selectedPA, editableFlag, conn, this);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//btnPAAdopt
		}
		else if(action.equals("btnPANew")){

			
				Boolean editableFlag = true;

				try {
					//The null indicates to the gui to make a blank constructor
					PADetailsGUI newDetails = new PADetailsGUI("Details: New Particulier", null, editableFlag, conn, this);
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		} else if(action.equals("btnPAAdopt")) {
			

			int selectedPAIndex = listParticuliers.getSelectedIndex();
				if(selectedPAIndex == -1){
					JOptionPane.showMessageDialog(this, "Choose a particulier that you would like to adopt with.");
					return;
				}
				
				
				Particulier selectedPA = Particulier.listParticulier.get(selectedPAIndex);  
				
				AdoptionScreen as = new AdoptionScreen("Adopt an entity, "+selectedPA.getNom()+".", conn, selectedPA, this);
		}

	}


	public void updateEntiteMobileList(){
		entiteMobilesDefaultModel.clear();

		this.entiteMobile.stream().forEach((item) -> {
			entiteMobilesDefaultModel.addElement(item.printListString());
		});
		listEntiteMobiles.repaint();
	}
	
	public void updateCapteurGPSList(){
		capteursDefaultModel.clear();

		this.capteurGPS.stream().forEach((item) -> {
			capteursDefaultModel.addElement(item.printListString());
		});
		listCapteurs.repaint();
	}


	public void valueChanged(ListSelectionEvent e) {
		if(!e.getValueIsAdjusting()){

		if(e.getSource().equals(listCapteurs)){
				if(GUI.viewType == "user"){
	//				updateCapteurGPSList();
				
//
//					// GET USER NAME, AND SET THE SELECTED PARTICULIER TO THAT USER.
//					// THERE IS CURRENTLY NO LOGIN FRAME, SO THIS IF WILL ASSUME UNIVERSITE DE MONTREAL
//					//
//					try {
//						capteurGPS = PresetQueries.getCapteursForParticulier(conn, 1);
//						updateCapteurGPSList();
//					} 
//					catch (Exception e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
			}
				else if (GUI.viewType == "admin") {
//					updateCapteurGPSList();
//					if(listCapteurs.getSelectedIndex() != -1){
//
//						programChanged = true;
//						int capteurGPSid = Capteur_GPS.listCapteurGPS.get(listCapteurs.getSelectedIndex()).getCaptID();
//						try {
//							capteurGPS = Capteur_GPS.listCapteurGPS.get(listCapteurs.getSelectedIndex());//PresetQueries.getCapteursForParticulier(conn, capteurGPSid);
//							//updateCapteurGPSList();
//						} 
//						catch (Exception e1) {
//							// TODO Auto-generated catch block
//							e1.printStackTrace();
//						}
//						programChanged = false;
//					}
//				}
			}
		}
			else
				if (e.getSource().equals(listParticuliers)) {

				if(GUI.viewType == "user"){


					try {
						entiteMobile = PresetQueries.getEntiteMobileForParticulier(conn, 1);
						updateEntiteMobileList();
					} 
					catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				} else if (GUI.viewType == "admin") {

					if(listParticuliers.getSelectedIndex() != -1){

						programChanged = true;
						int particulierID = Particulier.listParticulier.get(listParticuliers.getSelectedIndex()).getParticulierID();
						try {
							entiteMobile = PresetQueries.getEntiteMobileForParticulier(conn, particulierID);
							updateEntiteMobileList();
						} 
						catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						programChanged = false;



						//						programChanged = true;
						//
						//						String test = listClasses.getSelectedValue();
						//						Classe newlyClickedClass = Model.getClassFromName(test);
						//						this.generalization = newlyClickedClass.getListGeneralization();
						//						this.attribute = newlyClickedClass.getListAttribute();
						//						this.operation = newlyClickedClass.getListOperation();
						//						this.relation = new ArrayList<>();
						//
						//						for(int i = 0; i<newlyClickedClass.getListAggregation().size(); i++){
						//							this.relation.add((Relation)newlyClickedClass.getListAggregation().get(i));
						//						}
						//
						//						for(int i = 0; i<newlyClickedClass.getListAssociation().size(); i++){
						//							this.relation.add((Relation)newlyClickedClass.getListAssociation().get(i));
						//						}
						//
						//						updateGeneralizationList();
						//						updateAttributeList();
						//						updateOperationList();
						//						updateRelationList();
						//
						//						listGeneralizations.clearSelection();
						//						listAttributes.clearSelection();
						//						listOperations.clearSelection();
						//						listRelations.clearSelection();
						//						descriptionTextArea.setText("");
						//
						//						programChanged = false;
					}
				}

			}
		}
	}
}
