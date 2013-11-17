package com.app.places;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
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

		/*// Save
		Button btnSave = (Button) findViewById(R.id.btnSave);
		btnSave.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				Log.d(TAG, "qwe qweqwuewuiegqw y gqwueyqw");
			}
		});*/

		// Signout
		Button btnLogin = (Button) findViewById(R.id.btn_login);
		btnLogin.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				Log.d(TAG, "Login");
				Intent intent = new Intent(Login.this, MapViewActivity.class);
				startActivity(intent);
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
}
