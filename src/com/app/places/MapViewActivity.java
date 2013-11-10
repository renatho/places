package com.app.places;

import java.sql.ResultSet;
import java.util.List;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import android.view.Menu;
import android.widget.TextView;

public class MapViewActivity extends MapActivity {

	MapView mapView;
	int count;
	TextView txtLocaisProximos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);

		txtLocaisProximos = (TextView) findViewById(R.id.txtLocaisProximos);
    	mapView = (MapView) findViewById(R.id.mapview);
    	mapView.setBuiltInZoomControls(true);
    	
    	// Chama dados do banco
		DatabaseHelper db = new DatabaseHelper();

		try {
			db.open();
			ResultSet rs = db.executeQuery("select * counter from places");
			rs.next();
			count = rs.getRow();

	    	// Insere pinos
	    	List<Overlay> mapOverlays = mapView.getOverlays();
	    	Drawable drawable = this.getResources().getDrawable(R.drawable.marker_map);
	    	MapItemizedOverlay itemizedoverlay = new MapItemizedOverlay(drawable, this);
	    	
	    	//for () {
		    	GeoPoint point = new GeoPoint(19240000,-99120000);
		    	OverlayItem overlayitem = new OverlayItem(point, "Hola, Mundo!", "I'm in Mexico City!");
	
		    	//GeoPoint point2 = new GeoPoint(35410000, 139460000);
		    	//OverlayItem overlayitem2 = new OverlayItem(point2, "Sekai, konichiwa!", "I'm in Japan!");
	
		    	itemizedoverlay.addOverlay(overlayitem);
		    	//itemizedoverlay.addOverlay(overlayitem2);
			//}
	    	mapOverlays.add(itemizedoverlay);
			
	    	// Fecha banco
			rs.close();
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		//txtLocaisProximos.setText(Integer.toString(count));
		txtLocaisProximos.setText("10");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map, menu);
		return true;
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

}