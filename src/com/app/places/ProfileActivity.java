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
	
	protected void onCreate(Bundle savedInstanceState) {
		Log.d("Profile", "Entrou no onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		
		
		Log.d("REGI", "BEFORE IFS");
    	
    	// Registry
    	Button btnReg = (Button) findViewById(R.id.btn_Register);
    	btnReg.setOnClickListener(new OnClickListener() {
    		public void onClick(View view) {
    			Log.d("REGI", "IF");
    			_username = (EditText) findViewById(R.id.txt_user);
    			_password = (EditText) findViewById(R.id.txt_passw);
    			_age = (EditText) findViewById(R.id.txt_age);
    			_sex = (RadioButton) findViewById(R.id.radio_Feminino);
    			_email = (EditText) findViewById(R.id.txt_email);
    	    	user = "'" + _username.toString().toLowerCase() + "'";
    	    	pass = "'" + _password.toString().toLowerCase() + "'";
    	    	email = "'" + _email.toString().toLowerCase() + "'";
    	    	
    			ValidateProfile v = new ValidateProfile();
    			v.execute();
    		}
    	});
    	
    	// Cancel
    	Button btnCancel = (Button) findViewById(R.id.btn_Cancel);
    	btnReg.setOnClickListener(new OnClickListener() {
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
			Log.d("REG", "ENTROU NO MAX");
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
        	Log.d("REG", result.toString());
        if (result > 0){
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
			prof.setAge(Integer.parseInt((_age.getText()).toString()));
			if (_sex.isChecked()){
				prof.setSex(_sex.toString());
			}else{
				_sex = (RadioButton) findViewById(R.id.radio_Masculino);
				prof.setSex(_sex.toString());
			}
			
			int count = 0;
			int max = 0;
	    	// Chama dados do banco
			DatabaseHelper db = new DatabaseHelper();
			try {
				db.open();
				ResultSet rs = db.executeQuery("SELECT MAX(profile.id) " +
												" FROM profile");
				while (rs.next()) {
					Log.d("Login", "count " + count);
					max = rs.getInt(1) + 1;
				}				
		    	rs.close();
				db.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
	        
			sex = "'" + _password.toString().toLowerCase() + "'";
	        
			try {
				db.open();
				ResultSet rs = db.executeQuery("INSERT INTO profile " +
												" values (" + max + ", " + user +
												" , " + pass + ", " + _age + 
												", " + sex + "," + email + ")");
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
