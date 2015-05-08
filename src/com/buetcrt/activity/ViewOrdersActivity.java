package com.buetcrt.activity;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.ListActivity;
import android.app.ProgressDialog;
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
import com.buetcrt.utils.AppUtility;
import com.buetcrt.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public class ViewOrdersActivity extends ListActivity {
	
	private List<Order> orders;
	private OrderAdapter orderAdapter;
	private RestAdapter restAdapter;
	private CartService cartService;
	private ProgressDialog progressDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_orders);
		
		restAdapter = new RestAdapter.Builder()
			.setEndpoint(Constants.API_END_POINT)
			.setRequestInterceptor(new AuthenticatedRequestInterceptor(this))
			.build();
		
		String whichCart = "{\"cart\":{\"__type\":\"Pointer\",\"className\":\"Cart\",\"objectId\":\"" + 
				AppUtility.getCartId(ViewOrdersActivity.this) + "\"}}";
		
		cartService = restAdapter.create(CartService.class);
		cartService.getOrders(whichCart, new Callback<JsonElement>() {
			
			@Override
			public void success(JsonElement response, Response arg1) {
				JsonArray orderArray = response.getAsJsonObject().get("results").getAsJsonArray();
				orders = new ArrayList<Order>();
				
				if (orders.size() == 0) {
					Toast.makeText(ViewOrdersActivity.this, "No active orders found", Toast.LENGTH_SHORT).show();
					finish();
				}
				
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
				
				System.out.println(response.toString());
			}
			
			@Override
			public void failure(RetrofitError arg0) {
				Toast.makeText(ViewOrdersActivity.this, "Error loading data. Please retry", 
						Toast.LENGTH_SHORT).show();
				System.err.println(arg0.getMessage());
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
			cartService.checkout(new Callback<JsonElement>() {
				
				@Override
				public void success(JsonElement arg0, Response arg1) {
					Toast.makeText(ViewOrdersActivity.this, 
							"Checkout completed successfully", Toast.LENGTH_SHORT).show();
					System.out.println(arg0.toString());
					progressDialog.cancel();
					finish();
				}
				
				@Override
				public void failure(RetrofitError arg0) {
					Toast.makeText(ViewOrdersActivity.this, 
							"Error checking out. Please try again", Toast.LENGTH_SHORT).show();
					progressDialog.cancel();
					finish();
				}
			});
			
			progressDialog = new ProgressDialog(ViewOrdersActivity.this);
			progressDialog.setIndeterminate(true);
			progressDialog.setMessage("Checking out your orders. Please wait..");
			progressDialog.show();
			
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
}
