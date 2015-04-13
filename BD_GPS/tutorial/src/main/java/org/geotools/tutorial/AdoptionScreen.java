package org.geotools.tutorial;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class AdoptionScreen extends JFrame implements ListSelectionListener, ActionListener {
	
	
	private JList<String> listEntiteMobiles;
	private DefaultListModel<String>entiteMobilesDefaultModel = new DefaultListModel<>();
	private ArrayList<EntiteMobile> entitees;
	private Particulier leParticulier;
	private String typeOfInterest = "ludique";
	private Connection conn;
	
	AdoptionScreen(String title, Connection conn, Particulier particulier){
		super(title);
		
		this.leParticulier = particulier;
		this.conn = conn;
		
		try {
			entitees = PresetQueries.getAllEntiteMobile(conn);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
		
		this.listEntiteMobiles = new JList<>();
		listEntiteMobiles.setModel(entiteMobilesDefaultModel);
		listEntiteMobiles.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listEntiteMobiles.addListSelectionListener(this);	
		
		JScrollPane entiteMobileScroller = new JScrollPane(listEntiteMobiles);
		entiteMobileScroller.setViewportView(listEntiteMobiles);
		entiteMobileScroller.setPreferredSize(new Dimension(250, 450));
		
		
		JPanel entiteMobilesPanel = new JPanel();
		entiteMobilesPanel.setBorder(new TitledBorder("Entites Mobiles"));
		entiteMobilesPanel.add(entiteMobileScroller, BorderLayout.WEST);
		
		
		
		JPanel panelRadioAndButtons = new JPanel();
		JPanel PanBottomButtons = new JPanel();
		JPanel panRadioButtons = new JPanel();
		
		JLabel scienceLabel = new JLabel("Science: ");
		JLabel ludiqueLabel = new JLabel("Ludique: ");
		//		BUTTONS
		JButton btnAdopt = new JButton("Adopt");
		btnAdopt.addActionListener(this);
		btnAdopt.setActionCommand("btnAdopt");
		
		JButton btnUnAdopt = new JButton("UnAdopt");
		btnUnAdopt.addActionListener(this);
		btnUnAdopt.setActionCommand("btnUnAdopt");

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(this);
		btnCancel.setActionCommand("btnCancel");
		
		
		PanBottomButtons.setLayout(new GridLayout(1,3));
		PanBottomButtons.add(btnAdopt);
		PanBottomButtons.add(btnUnAdopt);
		PanBottomButtons.add(btnCancel);
		

	
		
		
		JRadioButton checkScience = new JRadioButton();
		checkScience.setActionCommand("science");
		
		JRadioButton checkLudique = new JRadioButton();
		checkLudique.setActionCommand("ludique");
		checkLudique.setSelected(true);
		
		ButtonGroup group = new ButtonGroup();
		group.add(checkScience);
		group.add(checkLudique);
		
		checkScience.addActionListener(this);
		checkLudique.addActionListener(this);
		
		panRadioButtons.add(scienceLabel);
		panRadioButtons.add(checkScience);
		
		panRadioButtons.add(ludiqueLabel);
		panRadioButtons.add(checkLudique);
		
		panelRadioAndButtons.add(panRadioButtons, BorderLayout.CENTER);
		panelRadioAndButtons.add(PanBottomButtons, BorderLayout.SOUTH);
		
		
		entiteMobilesDefaultModel.clear();

		this.entitees.stream().forEach((item) -> {
			entiteMobilesDefaultModel.addElement(item.printListString());
		});
		
		listEntiteMobiles.repaint();
	
		this.add(entiteMobilesPanel, BorderLayout.NORTH);
		this.add(panelRadioAndButtons, BorderLayout.CENTER);
		
		
		setSize(400, 700);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		// TODO Auto-generated method stub
		
		String action = ae.getActionCommand();

		//IF CLICK ENTITEMOBILE-SHOWMAP
		if(action.equals("btnCancel")){
			this.dispose();
	
		} else if(action.equals("btnAdopt")){
			
			int selectedEMIndex = listEntiteMobiles.getSelectedIndex();
			EntiteMobile selectedEM = null;  
			if(selectedEMIndex == -1){
				JOptionPane.showMessageDialog(this, "Choose an Entite Mobile that you would like to adopt!");
				return;
			}

			selectedEM =  entitees.get(selectedEMIndex);
			System.out.println("The selected em is: "+selectedEM.getNom());
			
			try {
				PresetQueries.adoptEntity(conn, leParticulier, selectedEM, typeOfInterest);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Entity Adopted");
			
			
		} else if(action.equals("science")){
			typeOfInterest = "science";
			
		} else if(action.equals("ludique")){
			typeOfInterest = "ludique";
			
		} else if(action.equals("btnUnAdopt")){
			
			int selectedEMIndex = listEntiteMobiles.getSelectedIndex();
			EntiteMobile selectedEM = null;  
			if(selectedEMIndex == -1){
				JOptionPane.showMessageDialog(this, "Choose an Entite Mobile that you would like to unAdopt!");
				return;
			}

			selectedEM =  entitees.get(selectedEMIndex);
			System.out.println("The selected em is: "+selectedEM.getNom());

			
			try {
				PresetQueries.unAdoptEntity(conn, leParticulier, selectedEM, typeOfInterest);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Entity UnAdopted");
			this.dispose();
		} 
	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
