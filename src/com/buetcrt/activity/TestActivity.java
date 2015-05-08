package com.buetcrt.activity;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.Activity;
import android.os.Bundle;

import com.buetcrt.apiclient.AuthenticatedRequestInterceptor;
import com.buetcrt.apiclient.CartService;
import com.buetcrt.utils.Constants;
import com.google.gson.JsonElement;

public class TestActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		RestAdapter adapter = new RestAdapter.Builder()
			.setEndpoint(Constants.API_END_POINT)
			.setRequestInterceptor(new AuthenticatedRequestInterceptor(this))
			.build();
		
		CartService cartService = adapter.create(CartService.class);
		cartService.getActiveCart(new Callback<JsonElement>() {
			
			@Override
			public void success(JsonElement response, Response arg1) {
				String objectId = response.getAsJsonObject()
						.get("results").getAsJsonArray().get(0).getAsJsonObject()
						.get("objectId").getAsString();
				System.out.println(objectId);
			}
			
			@Override
			public void failure(RetrofitError arg0) {
				System.out.println(arg0.getMessage());
				
			}
		});
	}
}
