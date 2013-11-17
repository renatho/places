package com.app.places;

import java.sql.ResultSet;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.CursorJoiner.Result;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends Activity {

	private static final String TAG = Login.class.getName();

	private EditText _username;
	private EditText _password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		// Username
		_username = (EditText) findViewById(R.id.txt_user);
		// Password
		_password = (EditText) findViewById(R.id.txt_passw);

		// Signout
		Button btnLogin = (Button) findViewById(R.id.btn_login);
		btnLogin.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				Log.d(TAG, "Login");
				new LoginValidator().execute();
				/*
				Intent intent = new Intent(Login.this, MapViewActivity.class);
				startActivity(intent);*/
			}
		});
		
		//Register
		TextView register = (TextView) findViewById(R.id.txt_register);
		register.setOnClickListener(new OnClickListener(){
			public void onClick(View view){
				Log.d(TAG, "Register");
				
				Intent intent = new Intent(Login.this, ProfileActivity.class);
				startActivity(intent);
			}
		});
	}
	
	
	public class LoginValidator extends AsyncTask<Integer, String, Integer> {

		@Override
		protected Integer doInBackground(Integer... params) {
	    	String user = "'" + _username.getText().toString().toLowerCase() + "'";
	    	String pass = "'" + _password.getText().toString().toLowerCase() + "'";
	    	int count = 0;
	    	// Chama dados do banco
			DatabaseHelper db = new DatabaseHelper();

			try {
				db.open();
				ResultSet rs = db.executeQuery("SELECT profile.id, profile.login, profile.password " +
												" FROM profile" +
												" WHERE profile.login = " + user + "  AND " +
											    " profile.password = " + pass );
				while (rs.next()) {
					Log.d("Login", "count " + count);
					 count++;
				}				
		    	
		    	rs.close();
				db.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return count;
	    	
		}
		
        @Override
        protected void onPostExecute(Integer result) {
        	Log.d("Login", "restul " + result);
        }
		
	}
}
