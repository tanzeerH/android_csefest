package com.buetcrt.activity;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.ListActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.buetcrt.adapter.OrderAdapter;
import com.buetcrt.apiclient.AuthenticatedRequestInterceptor;
import com.buetcrt.apiclient.CartService;
import com.buetcrt.csefest.R;
import com.buetcrt.model.Order;
import com.buetcrt.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public class ViewOrdersActivity extends ListActivity {
	
	private List<Order> orders;
	private OrderAdapter orderAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_orders);
		
		RestAdapter adapter = new RestAdapter.Builder()
			.setEndpoint(Constants.API_END_POINT)
			.setRequestInterceptor(new AuthenticatedRequestInterceptor(this))
			.build();
		
		CartService cartService = adapter.create(CartService.class);
		cartService.getOrders(new Callback<JsonElement>() {
			
			@Override
			public void success(JsonElement response, Response arg1) {
				JsonArray orderArray = response.getAsJsonObject().get("results").getAsJsonArray();
				orders = new ArrayList<Order>();
				for (int i = 0; i < orderArray.size(); i++) {
					Order order = new Gson().fromJson(orderArray.get(i), Order.class);
					orders.add(order);
				}
				
				orderAdapter = new OrderAdapter(ViewOrdersActivity.this, R.layout.row_order, orders);
				setListAdapter(orderAdapter);
			}
			
			@Override
			public void failure(RetrofitError arg0) {
				Toast.makeText(ViewOrdersActivity.this, "Error loading data. Please retry", 
						Toast.LENGTH_SHORT).show();
				finish();
			}
		});
		
	}
}
