package com.buetcrt.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.util.Log;

import com.buetcrt.csefest.R;

public class AppUtility {
	public static boolean hasInternet(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public static void simpleAlert(Context context, String message) {
		AlertDialog.Builder bld = new AlertDialog.Builder(context,
				AlertDialog.THEME_HOLO_LIGHT);
		bld.setTitle(context.getResources().getString(R.string.app_name));
		bld.setMessage(message);
		bld.setCancelable(false);
		bld.setNeutralButton("Ok", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		bld.create().show();
	}

	public static void setInt(String key, Context context, int value) {
		SharedPreferences.Editor editor = PreferenceManager
				.getDefaultSharedPreferences(context).edit();
		editor.putInt(key, value);
		editor.commit();
	}

	public static int getInt(String key, Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context).getInt(
				key, -1);
	}

	public static boolean isLoggedIn(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context).contains(Constants.SESSION_TOKEN);
	}
	
	public static String getSessionToken(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context).getString(Constants.SESSION_TOKEN, null);
	}
	
	public static void saveLoginDetails(Context context, String email, String sessionToken) {
		SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
		editor.putString(Constants.EMAIL, email);
		editor.putString(Constants.SESSION_TOKEN, sessionToken);
		Log.d(Constants.SESSION_TOKEN, sessionToken);
		editor.commit();
	}
	
	public static void logOut(Context context) {
		SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
		editor.remove(Constants.SESSION_TOKEN);
		editor.commit();
	}
	
	public static void saveCartId(Context context, String cartId) {
		SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
		editor.putString(Constants.CART_ID, cartId);
		editor.commit();
	}
	
	public static String getCartId(Context context, String cartId) {
		return PreferenceManager.getDefaultSharedPreferences(context).getString(Constants.CART_ID, null);
	}
}
