package com.buetcrt.activity;

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
import com.buetcrt.model.SignUpCredentials;
import com.buetcrt.model.User;
import com.buetcrt.utils.AppUtility;
import com.buetcrt.utils.Constants;

public class SignUpActivity extends Activity {

	private EditText etUserName;
	private EditText etEmail;
	private EditText etPassword;
	private EditText etConfirmPassword;
	private Button btnSignUp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);

		etUserName = (EditText) findViewById(R.id.et_username);
		etEmail = (EditText) findViewById(R.id.et_email);
		etPassword = (EditText) findViewById(R.id.et_password);
		etConfirmPassword = (EditText) findViewById(R.id.et_confirm_password);
		btnSignUp = (Button) findViewById(R.id.btn_signup);
	}
	
	public void onLoginClick(View v) {
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
		finish();
	}
	
	public void onSignUpClick(View v) {
		RestAdapter adapter = new RestAdapter.Builder()
			.setEndpoint(Constants.API_END_POINT)
			.setRequestInterceptor(new UnauthenticatedRequestInterceptor())
			.build();
		
		String email = etEmail.getText().toString();
		String userName = etUserName.getText().toString();
		String password = etPassword .getText().toString();
		String confirmPassword = etConfirmPassword.getText().toString();
		
		if (!password.equals(confirmPassword)) {
			Toast.makeText(this, "Passwords doesn't match", Toast.LENGTH_LONG).show();
			return;
		}
		
		if (userName.isEmpty()) {
			Toast.makeText(this, "Username can't be empty", Toast.LENGTH_LONG).show();
			return;
		}
		
		if (password.length() < 8) {
			Toast.makeText(this, "Passwords need to be at least 8 character long", Toast.LENGTH_LONG).show();
			return;
		}
		
		SignUpCredentials credentials = new SignUpCredentials(userName, email, confirmPassword);
		LoginSignUpService service = adapter.create(LoginSignUpService.class);
		service.signup(credentials, new Callback<User>() {
			
			@Override
			public void success(User user, Response arg1) {
				Intent intent = new Intent(SignUpActivity.this, SplashActivity.class);
				Toast.makeText(SignUpActivity.this, "Congratulation! You have successfully signed up.",
						Toast.LENGTH_SHORT).show();
				AppUtility.saveLoginDetails(SignUpActivity.this, user.getEmail(), user.getSessionToken());
				startActivity(intent);
			}
			
			@Override
			public void failure(RetrofitError argten0) {
				Toast.makeText(SignUpActivity.this, "Error signing up. Please try again.", 
						Toast.LENGTH_LONG).show();
				btnSignUp.setText("Sign Up");
				
			}
		});
		
		btnSignUp.setText("Sigining Up..");
	}

	@Override
	public void onBackPressed() {
		finish();
		overridePendingTransition(R.anim.prev_slide_in, R.anim.prev_slide_out);
	}

}
