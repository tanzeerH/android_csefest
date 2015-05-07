package com.buetcrt.activity;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.buet_crt.csefest.R;
import com.buetcrt.apiclient.GitHubService;
import com.buetcrt.model.User;
import com.buetcrt.utils.Constants;

public class BaseActivity extends Activity {

	private TextView tvResult;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		tvResult = (TextView) findViewById(R.id.tv_result);
		loadData();
	}

	private void loadData() {
		RestAdapter adapter = new RestAdapter.Builder().setEndpoint(
				Constants.API_END_POINT).build();

		GitHubService service = adapter.create(GitHubService.class);
		service.getUser("rafi-kamal", new Callback<User>() {

			@Override
			public void success(User user, Response arg1) {
				tvResult.setText(user.toString());

			}

			@Override
			public void failure(RetrofitError arg0) {
				tvResult.setText("Error loading data");
			}
		});
	}

	@Override
	public void onBackPressed() {
		finish();
		overridePendingTransition(R.anim.prev_slide_in, R.anim.prev_slide_out);
	}

}
