package com.buetcrt.activity;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.buetcrt.apiclient.AuthenticatedRequestInterceptor;
import com.buetcrt.apiclient.CartService;
import com.buetcrt.csefest.R;
import com.buetcrt.utils.Constants;
import com.google.gson.JsonElement;

public class OrderDetailsActivity extends Activity {
	
	private String orderId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		orderId = getIntent().getStringExtra(Constants.ORDER_ID);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_order_details, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.remove) {

			RestAdapter restAdapter = new RestAdapter.Builder()
				.setEndpoint(Constants.API_END_POINT)
				.setRequestInterceptor(new AuthenticatedRequestInterceptor(this))
				.build();
			
			CartService cartService = restAdapter.create(CartService.class);
			cartService.deleteFromCart(orderId, new Callback<JsonElement>() {
				
				@Override
				public void success(JsonElement arg0, Response arg1) {
					System.out.println(arg0.toString());
					finish();
				}
				
				@Override
				public void failure(RetrofitError arg0) {
					System.out.println(arg0.getMessage());
					finish();
				}
			});
			
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
}
