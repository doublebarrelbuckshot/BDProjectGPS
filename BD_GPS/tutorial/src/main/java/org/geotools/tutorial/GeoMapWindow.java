package org.geotools.tutorial;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JFrame;

import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.collection.ListFeatureCollection;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.geotools.map.FeatureLayer;
import org.geotools.map.Layer;
import org.geotools.map.MapContent;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.geotools.styling.SLD;
import org.geotools.styling.Style;
import org.geotools.swing.JMapFrame;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import com.vividsolutions.jts.awt.PointShapeFactory.Point;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;

public class GeoMapWindow {
	private static final GeometryFactory GEOMFAC = JTSFactoryFinder.getGeometryFactory(); 

	
	public static void showGeoMapWindow(Connection conn, int capteurID) throws SQLException, IOException{
		ArrayList<GPS_Point> pointsForCapteur = PresetQueries.getGPSPointsForCapteurID(conn, capteurID);
		
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

		ArrayList<SimpleFeature> features= new ArrayList<SimpleFeature>();

		for(int i = 0; i<pointsForCapteur.size(); i++){
			Coordinate pos = new Coordinate(pointsForCapteur.get(i).getLatitude(), pointsForCapteur.get(i).getLongitude());
			features.add(createFeature(BLDR, pos, pointsForCapteur.get(i).getCapteurID(), pointsForCapteur.get(i).getSampleDate().toString()));
		}
		
		
		Style style2 = SLD.createPointStyle("circle", Color.RED, 
				Color.BLUE, 1.0f, 5.0f); 
		SimpleFeatureCollection collection = new ListFeatureCollection(TYPE, features); 

		Layer layer2 = new FeatureLayer(collection, style2); 

		map.addLayer(layer2); 

		// It is safe to call this method from any thread (not just 
		// the Event Dispatch Thread as is usual for Swing widgets) 

		
		
		//http://docs.geotools.org/stable/userguide/unsupported/swing/jmapframe.html
		JMapFrame show = new JMapFrame(map);
		show.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		show.setSize(1000, 600);
		//show.enableLayerTable( true );

		// zoom in, zoom out, pan, show all
		show.enableToolBar( true );

		// location of cursor and bounds of current
		show.enableStatusBar( true );
		show.setVisible(true);

		//JMapFrame.showMap(map); 
		
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
	
}
