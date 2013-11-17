package com.app.places;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

public class ProfileActivity extends Activity {
	Profile profile;
	
	protected void onCreate(Bundle savedInstanceState) {
		Log.d("Profile", "Entrou no onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		
		
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}



}
