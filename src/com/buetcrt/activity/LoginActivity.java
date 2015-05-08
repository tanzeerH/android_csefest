package com.buetcrt.activity;

import java.util.HashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.buetcrt.apiclient.LoginSignUpService;
import com.buetcrt.apiclient.UnauthenticatedRequestInterceptor;
import com.buetcrt.csefest.R;
import com.buetcrt.model.User;
import com.buetcrt.utils.AppUtility;
import com.buetcrt.utils.Constants;

public class LoginActivity extends Activity {

	private EditText etUserName;
	private EditText etPassword;
	private Button btnLogin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		etUserName = (EditText) findViewById(R.id.et_username);
		etPassword = (EditText) findViewById(R.id.et_password);
		btnLogin = (Button) findViewById(R.id.btn_login);
	}
	
	public void onLoginClick(View v) {
		RestAdapter adapter = new RestAdapter.Builder()
			.setEndpoint(Constants.API_END_POINT)
			.setRequestInterceptor(new UnauthenticatedRequestInterceptor())
			.build();

		Map<String, String> loginCredentials = new HashMap<String, String>();
		loginCredentials.put("username", etUserName.getText().toString());
		loginCredentials.put("password", etPassword.getText().toString());
		
		LoginSignUpService service = adapter.create(LoginSignUpService.class);
		service.login(loginCredentials, new Callback<User>() {
			
			@Override
			public void success(User user, Response arg1) {
				Intent intent = new Intent(LoginActivity.this, ProductsActivity.class);
				AppUtility.saveLoginDetails(LoginActivity.this, user.getEmail(), user.getSessionToken());
				startActivity(intent);
			}
			
			@Override
			public void failure(RetrofitError argten0) {
				Toast.makeText(LoginActivity.this, "Error logging in. Please try again.", 
						Toast.LENGTH_LONG).show();
				btnLogin.setText("Login");
				
			}
		});
		
		btnLogin.setText("Logging In..");
	}
	
	public void onSignUpClick(View v) {
		Intent intent = new Intent(this, SignUpActivity.class);
		startActivity(intent);
		finish();
	}

	@Override
	public void onBackPressed() {
		finish();
		overridePendingTransition(R.anim.prev_slide_in, R.anim.prev_slide_out);
	}

}
