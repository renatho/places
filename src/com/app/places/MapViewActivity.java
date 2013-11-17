package com.app.places;

import java.sql.ResultSet;
import java.util.List;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
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
	
	List<Overlay> mapOverlays;
	Drawable drawable;
	MapItemizedOverlay itemizedoverlay;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);

		txtLocaisProximos = (TextView) findViewById(R.id.txtLocaisProximos);
    	mapView = (MapView) findViewById(R.id.mapview);
    	mapView.setBuiltInZoomControls(true);

    	mapOverlays = mapView.getOverlays();
    	drawable = this.getResources().getDrawable(R.drawable.marker_map);
    	itemizedoverlay = new MapItemizedOverlay(drawable, this);

    	new loadPins().execute();
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
	
	public class loadPins extends AsyncTask<Integer, String, Integer> {

		@Override
		protected Integer doInBackground(Integer... params) {
	    	
	    	// Chama dados do banco
			DatabaseHelper db = new DatabaseHelper();

			try {
				db.open();
				ResultSet rs = db.executeQuery("SELECT places.description, places.lat, places.long " +
													"FROM places, profile, tagByProfile, tags, tagsByPlace " +
													"WHERE places.id = tagsByPlace.idPlace AND " +
														  "tagsByPlace.idTags = tags.idTag AND " +
														  "tagByProfile.idTag = tags.idTag AND " +
														  "tagByProfile.idProfile = profile.id AND " +
														  "profile.age <= places.max_age AND " +
														  "profile.age >= places.min_age AND " +
														  "profile.id = 2");

				// ***** O 2 do select Ž o id do profile. Deve ser alterado quando o login estiver integrado.
				count = 0;

		    	// Insere pinos
				while (rs.next()) {
					count++;
			    	GeoPoint point = new GeoPoint(rs.getInt("lat"), rs.getInt("long"));
			    	OverlayItem overlayitem = new OverlayItem(point, "Local", rs.getString("description"));

			    	itemizedoverlay.addOverlay(overlayitem);
				}
		    	mapOverlays.add(itemizedoverlay);

		    	// Fecha banco
				rs.close();
				db.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			return count;
		}
		
        @Override
        protected void onPostExecute(Integer result) {
        	txtLocaisProximos.setText(Integer.toString(result));
        }
		
	}

}