package org.geotools.tutorial;

import java.util.Date;

public class InfosScientifiqueVivant extends GPS_Point {
	
	private int pouls;
	private int pressionArterielle;
	private Double pourcentageGras;
	private int temperatureCorps;
	
	public InfosScientifiqueVivant(int capteurID, Date sampleDate,
			double latitude, double longitude) {
		super(capteurID, sampleDate, latitude, longitude);
	}
	
	
	public InfosScientifiqueVivant(int capteurID, Date sampleDate,
			double latitude, double longitude, int pouls,
			int pressionArterielle, Double pourcentageGras, int temperatureCorps) {
		super(capteurID, sampleDate, latitude, longitude);
		this.pouls = pouls;
		this.pressionArterielle = pressionArterielle;
		this.pourcentageGras = pourcentageGras;
		this.temperatureCorps = temperatureCorps;
	}

	public String toString(){
		return "\n" +
				"\tPouls: " + this.pouls  + "\n" + 
				"\tPression Arterielle: " + this.pressionArterielle  + "\n" + 
				"\tPourcentage Gras: " + this.pourcentageGras  + "\n" + 
				"\tTemperatureCorps: " +  this.temperatureCorps  + "\n";
			
	}
	public int getPouls() {
		return pouls;
	}

	public void setPouls(int pouls) {
		this.pouls = pouls;
	}

	public int getPressionArterielle() {
		return pressionArterielle;
	}

	public void setPressionArterielle(int pressionArterielle) {
		this.pressionArterielle = pressionArterielle;
	}

	public Double getPourcentageGras() {
		return pourcentageGras;
	}

	public void setPourcentageGras(Double pourcentageGras) {
		this.pourcentageGras = pourcentageGras;
	}

	public int getTemperatureCorps() {
		return temperatureCorps;
	}

	public void setTemperatureCorps(int temperatureCorps) {
		this.temperatureCorps = temperatureCorps;
	}


	


	

}
