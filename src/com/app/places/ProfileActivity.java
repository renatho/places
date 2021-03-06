package com.app.places;

import java.sql.ResultSet;

import com.app.places.Login.LoginValidator;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class ProfileActivity extends Activity {
	private static final String TAG = ProfileActivity.class.getName();
	
	EditText _username;
	EditText _password;
	EditText _age;
	RadioButton _sex;
	EditText _email;
	String user;
	String pass;
	ProgressDialog _dialog;
	String sex;
	String email;
	int age;
	
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_profile);
		
		Log.d(TAG, "Entrou no onCreate");

		_username = (EditText) findViewById(R.id.txt_user);
		_password = (EditText) findViewById(R.id.txt_passw);
		
		Log.d(TAG, "BEFORE IFS");
    	
    	// Registry
    	Button btnReg = (Button) findViewById(R.id.btn_Register);
    	btnReg.setOnClickListener(new OnClickListener() {
    		public void onClick(View view) {
    			Log.d(TAG, "IF");
    			_age = (EditText) findViewById(R.id.txt_age);
    			_sex = (RadioButton) findViewById(R.id.radio_Feminino);
    			_email = (EditText) findViewById(R.id.txt_tag);
    	    	ValidateProfile v = new ValidateProfile();
    			v.execute();
    		}
    	});
    	
    	// Cancel
    	Button btnCancel = (Button) findViewById(R.id.btn_Cancel);
    	btnCancel.setOnClickListener(new OnClickListener() {
    		public void onClick(View view) {
    			Intent intent = new Intent(ProfileActivity.this, Login.class);
				startActivity(intent);
    		}
    	});

	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}

	public class ValidateProfile extends AsyncTask<Integer, String, Integer> {
	
		@Override
		protected Integer doInBackground(Integer... params) {
			Log.d(TAG, "ENTROU NO MAX");
			Profile prof = new Profile();
			
			// Username
			prof.setLogin((_username.getText()).toString());
			prof.setPassword((_password.getText()).toString());
			prof.setAge(Integer.parseInt((_age.getText()).toString()));
			if (_sex.isChecked()){
				prof.setSex(_sex.toString());
			}else{
				_sex = (RadioButton) findViewById(R.id.radio_Masculino);
				prof.setSex(_sex.toString());
			}
			
			int count = 0;
			// Chama dados do banco
			DatabaseHelper db = new DatabaseHelper();
			
			try {
				db.open();
				ResultSet rs = db.executeQuery("SELECT profile.id, profile.login, profile.password " +
												" FROM profile" +
												" WHERE profile.login = " + user );
				while (rs.next()) {
					Log.d(TAG, "count " + count);
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
        	Log.d(TAG, result.toString());
        if (result <= 0){
           new CreateProfile().execute();
		}else {
			_dialog = new ProgressDialog(ProfileActivity.this);
			_dialog.setMessage("Login already exists");
			_dialog.setCancelable(false);
			_dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Ok", new DialogInterface.OnClickListener() {
			    @Override
			    public void onClick(DialogInterface dialog, int which) {
			        dialog.dismiss();
			    }
			});
			_dialog.show();
			}
        }
	}
	
	public class CreateProfile extends AsyncTask<Integer, String, Integer> {
		
		@Override
		protected Integer doInBackground(Integer... params) {
			Profile prof = new Profile();
			
			// Username
			prof.setLogin((_username.getText()).toString());
			prof.setPassword((_password.getText()).toString());
			prof.setAge(Integer.parseInt((_age.getText().toString())));
			if (_sex.isChecked()){
				prof.setSex(_sex.getText().toString());
			}else{
				_sex = (RadioButton) findViewById(R.id.radio_Masculino);
				prof.setSex(_sex.getText().toString());
			}
			int count = 0;
			int max = 0;
	    	// Chama dados do banco
			DatabaseHelper bd = new DatabaseHelper();
			try {
				bd.open();
				ResultSet rs = bd.executeQuery("SELECT MAX(profile.id) " +
												" FROM profile");
				while (rs.next()) {
					Log.d(TAG, "count " + count);
					max = rs.getInt(1) + 1;
				}				
		    	rs.close();
		    	bd.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			user = "'" + _username.getText().toString() + "'";
	    	user = user.toLowerCase();
	    	pass = "'" + _password.getText().toString()+ "'";
	    	pass = pass.toLowerCase() ;
	    	email = "'" + _email.getText().toString() + "'";
	    	email = email.toLowerCase();
	    	age = Integer.parseInt((_age.getText()).toString());
			
			sex = "'" + prof.getSex().toLowerCase() + "'";
			Log.d(TAG, "insert into profile " +
					" values (" + max + ", " + user +
					" , " + pass + ", " + age + 
					", " + sex + ", " + email + ")");
			
			DatabaseHelper db = new DatabaseHelper();
			
			try {
				db.open();
				db.executeUpdate("insert into profile " +
								 " values (" + max + ", " + user +
								 " , " + pass + ", " + age + 
								 ", " + sex + ", " + email + ")");
				db.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			count = 1;
			return count;
	    	
		}
		
        @Override
        protected void onPostExecute(Integer result) {
        if (result > 0){
        	_dialog = new ProgressDialog(ProfileActivity.this);
			_dialog.setMessage("Login created with sucess!");
			_dialog.setCancelable(false);
			_dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Ok", new DialogInterface.OnClickListener() {
			    @Override
			    public void onClick(DialogInterface dialog, int which) {
			        dialog.dismiss();
			    }
			});
			_dialog.show();
			
		}else {
			_dialog = new ProgressDialog(ProfileActivity.this);
			_dialog.setMessage("Login not created");
			_dialog.setCancelable(false);
			_dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Ok", new DialogInterface.OnClickListener() {
			    @Override
			    public void onClick(DialogInterface dialog, int which) {
			        dialog.dismiss();
			    }
			});
			_dialog.show();
			//_dialog = ProgressDialog.show(Login.this, "Error", "Login does not match", false, true);
			}
		
        }
		
	}



}
