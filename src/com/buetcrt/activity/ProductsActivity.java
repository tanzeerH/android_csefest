package com.buetcrt.activity;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.buetcrt.adapter.NavDrawerAdapter;
import com.buetcrt.apiclient.AuthenticatedRequestInterceptor;
import com.buetcrt.apiclient.LoginSignUpService;
import com.buetcrt.apiclient.ProductService;
import com.buetcrt.apiclient.UnauthenticatedRequestInterceptor;
import com.shopaholic.team16.R;
import com.buetcrt.fragment.ProductsFragment;
import com.buetcrt.model.NavMenuItem;
import com.buetcrt.utils.AppUtility;
import com.buetcrt.utils.Constants;
import com.google.gson.JsonElement;


public class ProductsActivity  extends FragmentActivity{
	Context mContext;
	private ActionBarDrawerToggle mDrawerToggle;
	private NavDrawerAdapter navDrawerAdapter;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ArrayList<NavMenuItem> drawerItemList;
	private ProgressDialog pd;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product_list);
		/*RestAdapter adapter = new RestAdapter.Builder()
				.setEndpoint(Constants.API_END_POINT)
				.setRequestInterceptor(new UnauthenticatedRequestInterceptor())
				.build();*/
		if(getActionBar()!=null)
		{
		getActionBar().setTitle(getResources().getString(R.string.app_name));
		getActionBar().setDisplayHomeAsUpEnabled(true);
		//getActionBar().setHomeButtonEnabled(true);
		}
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
		setDrawerListItems();
		
		
		mDrawerList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if(position==3){
					Intent i=new Intent(ProductsActivity.this,SearchActivity.class);
					startActivity(i);
					overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
				}
				else if(position==2){
					Intent i=new Intent(ProductsActivity.this,ViewOrdersActivity.class);
					startActivity(i);
					overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
				}
				else if(position==4){
					if (AppUtility.hasInternet(ProductsActivity.this)) {

						pd = new ProgressDialog(ProductsActivity.this);
						pd.setMessage("Please wait...");
						logout();
					} else
						AppUtility.simpleAlert(ProductsActivity.this,
								"No internet connection.");
				}
				
			}
		});
		
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.nav, // nav menu toggle icon
				R.string.app_name, // nav drawer open - description for
									// accessibility
				R.string.app_name // nav drawer close - description for
									// accessibility
		) {
			public void onDrawerClosed(View view) {
				
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);	
		
		/*ProductService productService=adapter.create(ProductService.class);
		productService.getProducts(new Callback<JsonElement>() {

			@Override
			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void success(JsonElement json, Response arg1) {
				
				String name=json.getAsJsonObject().get("results").getAsJsonArray().get(0).getAsJsonObject().get("name").toString();
				Log.e("msg",""+name);
			}
			
			
		});*/
		
		ProductsFragment productsFragment=new ProductsFragment();
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.replace(R.id.frame_container, productsFragment);
		ft.addToBackStack(null);
		ft.commit();

	}
	private void logout()
	{
		RestAdapter adapter = new RestAdapter.Builder()
		.setEndpoint(Constants.API_END_POINT)
		.setRequestInterceptor(new AuthenticatedRequestInterceptor(ProductsActivity.this))
		.build();
		
		LoginSignUpService loginSignUpService=adapter.create(LoginSignUpService.class);
		loginSignUpService.logout(new Callback<JsonElement>() {

			@Override
			public void failure(RetrofitError arg0) {
				if (pd.isShowing())
					pd.dismiss();
				AppUtility.simpleAlert(ProductsActivity.this,
						"An error occured."+ arg0.getMessage());
				if (pd.isShowing())
					pd.dismiss();
				
			}

			@Override
			public void success(JsonElement arg0, Response arg1) {
				if (pd.isShowing())
					pd.dismiss();
				AppUtility.logOut(ProductsActivity.this);
				Intent i=new Intent(ProductsActivity.this,LoginActivity.class);
				startActivity(i);
				overridePendingTransition(R.anim.prev_slide_in, R.anim.prev_slide_out);
				finish();
				
				
			}
		});
	}
	@Override
	protected void onResume() {
		super.onResume();
		
		navDrawerAdapter=new NavDrawerAdapter(ProductsActivity.this,R.layout.nav_menu_row,drawerItemList);
		mDrawerList.setAdapter(navDrawerAdapter);
		invalidateOptionsMenu();
		// setupMap();

	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		return super.onPrepareOptionsMenu(menu);
	}
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		
		return true;
	}
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}
	private void setDrawerListItems()
	{
		drawerItemList=new ArrayList<NavMenuItem>();
		drawerItemList.add(new NavMenuItem("Products",R.drawable.ic_launcher));
		drawerItemList.add(new NavMenuItem("Profile",R.drawable.profile));
		drawerItemList.add(new NavMenuItem("My Cart",R.drawable.mycart));
		drawerItemList.add(new NavMenuItem("Search",R.drawable.search));
		//drawerItemList.add(new NavMenuItem("Reset Password",R.drawable.resetpass));
		drawerItemList.add(new NavMenuItem("Logout",R.drawable.logout));
		
	}
	
}
