package com.buetcrt.activity;



import com.buet_crt.csefest.R;

import android.app.Activity;

public class BaseActivity  extends Activity{
	
	@Override
	public void onBackPressed() {
		finish();
		overridePendingTransition(R.anim.prev_slide_in, R.anim.prev_slide_out);
	}

}
