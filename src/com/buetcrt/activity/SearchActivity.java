package com.buetcrt.activity;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.buetcrt.adapter.ProductAdapter;
import com.buetcrt.apiclient.ProductService;
import com.buetcrt.apiclient.UnauthenticatedRequestInterceptor;
import com.buetcrt.csefest.R;
import com.buetcrt.csefest.dao.Products;
import com.buetcrt.model.SearchQuery;
import com.buetcrt.utils.AppUtility;
import com.buetcrt.utils.Constants;
import com.google.gson.JsonElement;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SearchView.OnQueryTextListener;

public class SearchActivity extends BaseActivity {
	private ActionBar actionBar;
	SearchView searchView;
	private ListView lv;
	private ArrayList<Products> productList = new ArrayList<>();
	ProductAdapter prodAdapter;
	private ProgressDialog pd;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_products);
		actionBar = getActionBar();
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		actionBar.setCustomView(R.layout.actionbar_title_serach);
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
		searchView = (SearchView) findViewById(R.id.sv_search);
		searchView.setOnQueryTextListener(new OnQueryTextListener() {

			@Override
			public boolean onQueryTextSubmit(String query) {
				if (AppUtility.hasInternet(SearchActivity.this)) {

					pd = new ProgressDialog(SearchActivity.this);
					pd.setMessage("Please wait...");
					getProducts(query);
				} else
					AppUtility.simpleAlert(SearchActivity.this,
							"No internet connection.");
				return false;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				// TODO Auto-generated method stub
				return false;
			}
		});
		lv = (ListView) findViewById(R.id.lv_products);
		Log.e("MSG", "called");

		prodAdapter = new ProductAdapter(SearchActivity.this,
				R.layout.row_products, productList);
		lv.setAdapter(prodAdapter);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				AppUtility.selectedProduct = productList.get(position);
				Intent i = new Intent(SearchActivity.this,
						ProductDetailsActivity.class);
				startActivity(i);

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return super.onCreateOptionsMenu(menu);
	}

	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == android.R.id.home) {
			finish();
			overridePendingTransition(R.anim.prev_slide_in,
					R.anim.prev_slide_out);
		}
		return true;
	}

	private void getProducts(String query) {
		pd.show();
		RestAdapter adapter = new RestAdapter.Builder()
				.setEndpoint(Constants.API_END_POINT)
				.setRequestInterceptor(new UnauthenticatedRequestInterceptor())
				.build();
		ProductService productService = adapter.create(ProductService.class);

		productService.getSearchResult(new SearchQuery(query),
				new Callback<JsonElement>() {

					@Override
					public void failure(RetrofitError arg0) {
						if (pd.isShowing())
							pd.dismiss();
						AppUtility.simpleAlert(SearchActivity.this,
								"An error occured.");
					}

					@Override
					public void success(JsonElement json, Response arg1) {
						if (pd.isShowing())
							pd.dismiss();
						if (json.getAsJsonObject().get("result") != null) {
							/*String name = json.getAsJsonObject().get("result")
									.getAsJsonArray().get(0).getAsJsonObject()
									.get("name").toString();
							Log.e("MSG", name);*/
							int size = json.getAsJsonObject().get("result")
									.getAsJsonArray().size();
							for (int i = 0; i < size; i++) {
								JsonElement je = json.getAsJsonObject()
										.get("result").getAsJsonArray().get(i);
								Products p = new Products();
								p.setObjectId(je.getAsJsonObject()
										.get("objectId").toString());
								p.setName(je.getAsJsonObject().get("name")
										.toString());
								p.setPrice(je.getAsJsonObject().get("price")
										.getAsDouble());
								p.setDescription(je.getAsJsonObject()
										.get("description").toString());
								p.setImageURL(je.getAsJsonObject()
										.get("imageURL").toString());
								p.setThumbURL(je.getAsJsonObject()
										.get("thumbURL").toString());

								productList.add(p);
							}
							prodAdapter.notifyDataSetChanged();
						}
						else
							AppUtility.simpleAlert(SearchActivity.this,
									"No result for this keyword.");
							
					}

				});
	}

}
