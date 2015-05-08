package com.buetcrt.fragment;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.buetcrt.activity.ProductDetailsActivity;
import com.buetcrt.adapter.ProductAdapter;
import com.buetcrt.apiclient.ProductService;
import com.buetcrt.apiclient.UnauthenticatedRequestInterceptor;
import com.shopaholic.team16.R;
import com.buetcrt.csefest.dao.Products;
import com.buetcrt.utils.AppUtility;
import com.buetcrt.utils.Constants;
import com.google.gson.JsonElement;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ProductsFragment extends Fragment {
	private ListView lv;
	private ArrayList<Products> productList=new ArrayList<>();
	ProductAdapter prodAdapter;
	private ProgressDialog pd;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_products, null, false);
		lv = (ListView) v.findViewById(R.id.lv_products);
		Log.e("MSG","called");
		if(AppUtility.hasInternet(getActivity()))
		{
		
		pd=new ProgressDialog(getActivity());
		pd.setMessage("Please wait...");
		getProducts();
		}
		else
			AppUtility.simpleAlert(getActivity(),"No internet connection.");
			
		prodAdapter=new ProductAdapter(getActivity(),R.layout.row_products,productList);
		lv.setAdapter(prodAdapter);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				AppUtility.selectedProduct=productList.get(position);
				Intent i=new Intent(getActivity(),ProductDetailsActivity.class);
				startActivity(i);
				
			}
		});
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		

	}

	private void getProducts() {
		pd.show();
		RestAdapter adapter = new RestAdapter.Builder()
				.setEndpoint(Constants.API_END_POINT)
				.setRequestInterceptor(new UnauthenticatedRequestInterceptor())
				.build();
		ProductService productService = adapter.create(ProductService.class);
		productService.getProducts(new Callback<JsonElement>() {

			@Override
			public void failure(RetrofitError arg0) {
				if(pd.isShowing())
					pd.dismiss();
				AppUtility.simpleAlert(getActivity(),"An error occured.");
			}

			@Override
			public void success(JsonElement json, Response arg1) {
				if(pd.isShowing())
					pd.dismiss();
				String name= json.getAsJsonObject().get("results")
						.getAsJsonArray().get(0).getAsJsonObject().get("name")
						.toString();
				Log.e("MSG",name);
				int size=json.getAsJsonObject().get("results").getAsJsonArray().size();
				for(int i=0;i<size;i++)
				{
					JsonElement je=json.getAsJsonObject().get("results").getAsJsonArray().get(i);
					Products p=new Products();
					p.setObjectId(je.getAsJsonObject().get("objectId").toString());
					p.setName(je.getAsJsonObject().get("name").toString());
					p.setPrice(je.getAsJsonObject().get("price").getAsDouble());
					p.setDescription(je.getAsJsonObject().get("description").toString());
					p.setImageURL(je.getAsJsonObject().get("imageURL").toString());
					p.setThumbURL(je.getAsJsonObject().get("thumbURL").toString());
					
					productList.add(p);
				}
				prodAdapter.notifyDataSetChanged();
			}

		});
	}
}
