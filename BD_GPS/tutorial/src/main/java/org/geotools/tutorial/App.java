package org.geotools.tutorial;



import java.awt.Color;
import java.sql.Statement;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.swing.UIManager;

import org.geotools.data.DataUtilities;
import org.geotools.data.FeatureSource;
import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.collection.ListFeatureCollection;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.factory.CommonFactoryFinder;
import org.geotools.feature.DefaultFeatureCollection;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureCollections;
import org.geotools.feature.SchemaException;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.geotools.geometry.jts.WKTReader2;
import org.geotools.map.FeatureLayer;
import org.geotools.map.Layer;
import org.geotools.map.MapContent;
import org.geotools.referencing.crs.DefaultEngineeringCRS;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.geotools.renderer.lite.StreamingRenderer;
import org.geotools.styling.FeatureTypeStyle;
import org.geotools.styling.Graphic;
import org.geotools.styling.Mark;
import org.geotools.styling.PointSymbolizer;
import org.geotools.styling.Rule;
import org.geotools.styling.SLD;
import org.geotools.styling.Style;
import org.geotools.swing.JMapFrame;
import org.geotools.swing.data.JFileDataStoreChooser;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.feature.type.GeometryDescriptor;
import org.opengis.filter.FilterFactory;
import org.opengis.style.StyleFactory;

import com.vividsolutions.jts.awt.PointShapeFactory.Point;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.io.ParseException;

/**
 * Prompts the user for a shapefile and displays the contents on the screen in a map frame.
 * <p>
 * This is the GeoTools Quickstart application used in documentationa and tutorials. *
 */
public class App {
	private static final GeometryFactory GEOMFAC = JTSFactoryFinder.getGeometryFactory(); 
	/**
	 * GeoTools Quickstart demo application. Prompts the user for a shapefile and displays its
	 * contents on the screen in a map frame
	 */
	public static void main(String[] args) throws Exception {
		Connection conn = null;
		try{
		String url = "jdbc:oracle:thin:@delphes.iro.umontreal.ca:1521:a05";		
		String usr = "rizzigia";
		String pwd = "giap103R";
		Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();  

		System.out.println("Connecting to Database URL = " + url);
		conn =DriverManager.getConnection(url, usr, pwd);
		
		
		//Connection conn = DriverManager.getConnection(url, usr, pwd ); 
		System.out.println("Connected... Now creating a statement");
		}
		catch(Exception ex){
			
			System.out.println("Connection failed.");
			ex.printStackTrace();
			}
		
		String query = "SELECT * FROM Capteur_GPS";
		Statement stmt = (Statement) conn.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		
		while(rs.next()){
			int captID = rs.getInt("CapteurID");
			String model = rs.getString("modele");
			String fabricant = rs.getString("fabricant");
			String precisionGPS = rs.getString("precisionGPS");
			Date dateDebut = rs.getDate("dateDebut");
			Date dateFin = rs.getDate("dateFin");
			
			String result = captID + ", " + model + ", " + fabricant + ", " + precisionGPS +
					", " + dateDebut.toString();
			if(dateFin != null){
				result += ", " + dateFin.toString();
			}
			System.out.println(result);
		}
		conn.close();
		
		File file = new File("Maps//ne_50m_admin_0_sovereignty.shp");
		

		FileDataStore store = FileDataStoreFinder.getDataStore(file);
		SimpleFeatureSource featureSource = store.getFeatureSource();

		// Create a map content and add our shapefile to it
		MapContent map = new MapContent();
		map.setTitle("Quickstart");

		Style style = SLD.createSimpleStyle(featureSource.getSchema());
		Layer layer = new FeatureLayer(featureSource, style);
		map.addLayer(layer);


		final SimpleFeatureType TYPE = createFeatureType(); 
		final SimpleFeatureBuilder BLDR = new SimpleFeatureBuilder(TYPE); 

		//@SuppressWarnings("deprecation")
		ArrayList<SimpleFeature> features= new ArrayList<SimpleFeature>();
		// arbitrary start pos 
		
		
		//Coordinate pos = new Coordinate(0, 0); 
		Coordinate pos = new Coordinate( (-74.006), 40.714); //NY
		//final double pointSpacing = 1.0; 
		int id = 0; 
		features.add(createFeature(BLDR, pos, id, "monday")); 

		pos = new Coordinate((-80.1936600),  25.774270); //MIAMI
		features.add(createFeature(BLDR, pos, ++id,"tuesday"));
		
		pos = new Coordinate((-115.13722), 36.174970); //Vegas
		features.add(createFeature(BLDR, pos, ++id, "wednesday"));
		
		pos = new Coordinate((-122.4194), 37.77493); //San Fran
		features.add(createFeature(BLDR, pos, ++id, "thursday"));
		
		pos = new Coordinate((-71.214540), 46.81228); //MIAMI
		features.add(createFeature(BLDR, pos, ++id, "friday"));
		
		//while (true) { 
		//	pos = nextPos(pos, pointSpacing); 
		//	if (pos.y <  0) { 
		//		break; 
		//	} 

		//	features.add(createFeature(BLDR, pos, ++id)); 
		//} 

		// Display the points on screen 
		Style style2 = SLD.createPointStyle("circle", Color.RED, 
				Color.BLUE, 1.0f, 5.0f); 
		SimpleFeatureCollection collection = new ListFeatureCollection(TYPE, features); 

		Layer layer2 = new FeatureLayer(collection, style2); 


		map.addLayer(layer2); 

		// It is safe to call this method from any thread (not just 
		// the Event Dispatch Thread as is usual for Swing widgets) 
		JMapFrame.showMap(map); 
	}

	private static  SimpleFeature createFeature(SimpleFeatureBuilder bldr, Coordinate pos, int id, String time) { 
		com.vividsolutions.jts.geom.Point p = GEOMFAC.createPoint(pos); 
		bldr.add(p); 
		bldr.add(id); 
		bldr.add(time);

		// null arg means allow the builder to generate a default feature ID 
		return bldr.buildFeature(null); 
	} 
	private static SimpleFeatureType createFeatureType() { 
		SimpleFeatureTypeBuilder typeBldr = new SimpleFeatureTypeBuilder(); 
		typeBldr.setName("trajectory"); 
		typeBldr.add("pos", Point.class, DefaultGeographicCRS.WGS84); 
		typeBldr.add("id", Integer.class); 
		typeBldr.add("time", String.class);
		return typeBldr.buildFeatureType(); 
	} 

	private static Coordinate nextPos(Coordinate startPos, double dist) { 
		double x = startPos.x + dist; 
		return new Coordinate(x, -0.01 * x * x + x ); 
	} 

}