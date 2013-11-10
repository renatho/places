package com.app.places;

import android.app.AlertDialog;
import android.content.Context;

public class Util {
	public static void alert(String title, String message, Context ctx) {
		AlertDialog alertDialog = new AlertDialog.Builder(ctx).create();
		alertDialog.setTitle(title);
	    alertDialog.setMessage(message);
	    alertDialog.show();
	}
}