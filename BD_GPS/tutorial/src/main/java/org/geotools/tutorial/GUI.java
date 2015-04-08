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



///test Florin
public class GUI extends JFrame implements ListSelectionListener, ActionListener{

	private static Connection conn;
	private int capteurIWantToMap = 101;
	private Boolean programChanged = false;



	private ArrayList<EntiteMobile> entiteMobile;


	//private final JList<String> listCapteurs;
	private final JList<String> listParticuliers;
	private JList<String> listEntiteMobiles;


	//private final DefaultListModel<String> capteursDefaultModel = new DefaultListModel<>();
	private final DefaultListModel<String> particuliersDefaultModel = new DefaultListModel<>();
	private DefaultListModel<String>entiteMobilesDefaultModel = new DefaultListModel<>();


	public  void loadDefaultModels( ) {



		/*
		 * SETUP FOR CLASSES JLIST
		 */
		Particulier.listParticulier.stream().forEach((item) -> {
			particuliersDefaultModel.addElement(item.printListString());
		});

		//listClasses.repaint();
	}


	public GUI(String s) throws Exception{
		super(s);


		/*
		 * ************************************************************
		 ******************* INITIALIZE CONNECTION  *******************
		 * ************************************************************
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


		/*
		 * ADD ALL BUTTONS TO ENTITE MOBILE PANEL
		 */

		entiteMobilesPanel.add(panEMButtons, BorderLayout.SOUTH);





		/*
		 * ************************************************************
		 ******************* MAIN WINDOW ADD PANEL  *******************
		 * ************************************************************
		 */

		JPanel listsPanel = new JPanel();
		listsPanel.setLayout(new GridLayout(1,3));

		listsPanel.add(particuliersPanel);
		listsPanel.add(entiteMobilesPanel);
		listsPanel.add(new JPanel());

		add(listsPanel, BorderLayout.CENTER);


		/*
		 * ************************************************************
		 *******************  MAIN WINDOW SETTINGS  *******************
		 * ************************************************************
		 */
		setSize(1000,750);
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
					EMDetailsGUI newDetails = new EMDetailsGUI("Details: " + selectedEM.getNom(), selectedEM, editableFlag, conn);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
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
					EMDetailsGUI newDetails = new EMDetailsGUI("Details: " + selectedEM.getNom(), selectedEM, editableFlag, conn);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}


	public void updateEntiteMobileList(){
		entiteMobilesDefaultModel.clear();

		this.entiteMobile.stream().forEach((item) -> {
			entiteMobilesDefaultModel.addElement(item.printListString());
		});
		listEntiteMobiles.repaint();
	}


	public void valueChanged(ListSelectionEvent e) {
		if(!e.getValueIsAdjusting()){
			if (e.getSource().equals(listParticuliers)) {
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
