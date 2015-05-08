package com.buetcrt.activity;

import retrofit.RestAdapter;
import android.app.Activity;
import android.os.Bundle;

import com.buetcrt.apiclient.AuthenticatedRequestInterceptor;
import com.buetcrt.apiclient.CartService;
import com.buetcrt.utils.Constants;

public class TestActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		RestAdapter adapter = new RestAdapter.Builder()
			.setEndpoint(Constants.API_END_POINT)
			.setRequestInterceptor(new AuthenticatedRequestInterceptor(this))
			.build();
		
		CartService cartService = adapter.create(CartService.class);
		
		// Get active cart
//		cartService.getActiveCart(new Callback<JsonElement>() {
//			
//			@Override
//			public void success(JsonElement response, Response arg1) {
//				String cartId = response.getAsJsonObject()
//						.get("results").getAsJsonArray().get(0).getAsJsonObject()
//						.get("objectId").getAsString();
//				AppUtility.saveCartId(TestActivity.this, cartId);
//			}
//			
//			@Override
//			public void failure(RetrofitError arg0) {
//				System.out.println(arg0.getMessage());
//				
//			}
//		});
		
		// Add to cart
//		Order order = new Order("w5Z2lAi3ad", "aPiLe97hRv", 2);
//		cartService.addToCart(order, new Callback<JsonElement>() {
//
//			@Override
//			public void failure(RetrofitError arg0) {
//				System.out.println(arg0.getMessage());
//			}
//
//			@Override
//			public void success(JsonElement arg0, Response arg1) {
//				System.out.println(arg0.toString());
//			}
//		});
	}
}
