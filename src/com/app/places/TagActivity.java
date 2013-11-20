package com.app.places;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class TagActivity extends Activity {
	EditText _tagName;
	ProgressDialog _dialog;
	AlertDialog _alertDialog;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tag);

		Button btnSave = (Button) findViewById(R.id.btn_Save);
		btnSave.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				_tagName = (EditText) findViewById(R.id.txt_tag);
				
				if(_tagName.getText().toString().matches("")){
					_tagName.requestFocus();
				}else{
					new SaveTag().execute();
				}
			}
		});
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}

	public class SaveTag extends AsyncTask<Integer, String, Integer> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			_dialog = new ProgressDialog(TagActivity.this);
			_dialog.setMessage("Saving...");
			_dialog.setCancelable(false);
			_dialog.show();
		}
		
		@Override
		protected Integer doInBackground(Integer... params) {
			Tags tag = new Tags();

			tag.setTag(_tagName.getText().toString());
			
			String query = "INSERT INTO tags VALUES (null, '"+ tag.getTag() +"')";
			
			int count = 0;
			
			DatabaseHelper db = new DatabaseHelper();

			try {
				db.open();
				db.executeUpdate(query);
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				db.close();
			}
			
			return count;
		}

		@Override
		protected void onPostExecute(Integer result) {
				_dialog.dismiss();
				_tagName.setText("");
				AlertDialog.Builder builder = new AlertDialog.Builder(TagActivity.this); 
				builder.setMessage("Tag added!").setTitle("New tag");
				builder.setNegativeButton("Ok",
						new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog,
							int which) {
						_dialog.dismiss();
					}
				});
				_alertDialog = builder.create();
				_alertDialog.show();
		}
	}
}
