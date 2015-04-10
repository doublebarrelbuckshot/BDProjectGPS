package org.geotools.tutorial;

public class Artificiel extends EntiteMobile {

	private String marque;
	private String modele;
	private int anneeFabrication;
	private String puissance;
	private String combustible;
	private String typeMachine;
	
	
	public Artificiel(int entiteID, String nom, int capteurID, String marque, String modele,
			int anneeFabrication, String puissance, String combustible, String typeMachine){
		
		super(entiteID, nom, capteurID);
		this.marque = marque;
		this.modele = modele;
		this.anneeFabrication = anneeFabrication;
		this.puissance = puissance;
		this.combustible = combustible;
		this.typeMachine = typeMachine;
		
	}
	


	public Artificiel() {
		// TODO Auto-generated constructor stub
	}



	public String toString(){
		String result = "";
		result += super.toString() + ", " + this.marque + ", " + this.modele + ", " + this.anneeFabrication + ", " + 
				this.puissance + ", " + this.combustible + this.typeMachine;
		return result;
	}

	public String getMarque() {
		return marque;
	}

	public void setMarque(String marque) {
		this.marque = marque;
	}

	public String getModele() {
		return modele;
	}


	public void setModele(String modele) {
		this.modele = modele;
	}


	public int getAnneeFabrication() {
		return anneeFabrication;
	}

	public void setAnneeFabrication(int anneeFabrication) {
		this.anneeFabrication = anneeFabrication;
	}

	public String getPuissance() {
		return puissance;
	}

	public void setPuissance(String puissance) {
		this.puissance = puissance;
	}

	public String getCombustible() {
		return combustible;
	}

	public void setCombustible(String combustible) {
		this.combustible = combustible;
	}
	public String getTypeMachine() {
		return typeMachine;
	}

	public void setTypeMachine(String typeMachine) {
		this.typeMachine = typeMachine;
	}
	
}
