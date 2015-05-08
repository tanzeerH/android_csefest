package com.buetcrt.activity;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
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
				
				TextView orderTotal = new TextView(ViewOrdersActivity.this);
				orderTotal.setText("Total = " + getOrderTotal(orders));
				orderTotal.setGravity(Gravity.CENTER);
				orderTotal.setTextSize(20);
				orderTotal.setLayoutParams(
						new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
				getListView().addFooterView(orderTotal);
			}
			
			@Override
			public void failure(RetrofitError arg0) {
				Toast.makeText(ViewOrdersActivity.this, "Error loading data. Please retry", 
						Toast.LENGTH_SHORT).show();
				finish();
			}
		});
		
	}
	
	private int getOrderTotal(List<Order> orders) {
		int total = 0;
		
		for (int i = 0; i < orders.size(); i++) {
			total += orders.get(i).getSubTotal();
		}
		
		return total;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_view_order, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.checkout) {
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
}
