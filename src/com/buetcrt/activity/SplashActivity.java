package com.buetcrt.activity;

import com.buetcrt.csefest.R;
import com.buetcrt.utils.AppUtility;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.textservice.SpellCheckerSubtype;

public class SplashActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash);
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				//start other activity
				if(AppUtility.isLoggedIn(SplashActivity.this))
				{
					Intent i=new Intent(SplashActivity.this,ProductsActivity.class);
					startActivity(i);
				}
				else
				{
				Intent i=new Intent(SplashActivity.this,LoginActivity.class);
				startActivity(i);
				}
				overridePendingTransition(R.anim.fadein, R.anim.fadeout);
				finish();
			}
		}, 3000);
	}
	private void test()
	{
		AppUtility.setInt("test",SplashActivity.this,3);
		Log.e("put value",""+3);
		Log.e("get value",""+AppUtility.getInt("test",SplashActivity.this));
		if(AppUtility.hasInternet(SplashActivity.this))
		{
			AppUtility.simpleAlert(SplashActivity.this,"Got internet");
		}
		else
			AppUtility.simpleAlert(SplashActivity.this,"No internet Available");
	}

}
