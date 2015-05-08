package com.buetcrt.activity;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.buetcrt.apiclient.AuthenticatedRequestInterceptor;
import com.buetcrt.apiclient.CartService;
import com.buetcrt.csefest.R;
import com.buetcrt.lazylist.ImageLoader;
import com.buetcrt.model.Order;
import com.buetcrt.utils.AppUtility;
import com.buetcrt.utils.Constants;
import com.google.gson.JsonElement;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ProductDetailsActivity extends BaseActivity implements
		OnClickListener {
	private Button btnPlus, btnMinus, btnAddToCart;
	private ImageView ivProduct;
	private TextView tvQuantity, tvName, tvDes, tvPrice;
	ImageLoader imageLoader;
	int quantity = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product_details);

		imageLoader = new ImageLoader(ProductDetailsActivity.this);
		btnPlus = (Button) findViewById(R.id.btn_plus);
		btnMinus = (Button) findViewById(R.id.btn_minus);
		btnAddToCart = (Button) findViewById(R.id.btn_cart);
		tvQuantity = (TextView) findViewById(R.id.tv_q);
		tvName = (TextView) findViewById(R.id.tv_name);
		tvPrice = (TextView) findViewById(R.id.tv_price);
		tvDes = (TextView) findViewById(R.id.tv_description);
		ivProduct = (ImageView) findViewById(R.id.iv_product);
		btnPlus.setOnClickListener(this);
		btnMinus.setOnClickListener(this);
		btnAddToCart.setOnClickListener(this);
		tvQuantity.setText("" + quantity);
		
		tvDes.setText(AppUtility.selectedProduct.getDescription());
		String image=AppUtility.selectedProduct.getImageURL();
    	image=image.replaceAll("\"","");
		imageLoader.DisplayImage(image,ivProduct);
		tvPrice.setText("Price: "+(AppUtility.selectedProduct.getPrice()*quantity+" TK"));

	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_plus) {
			quantity++;
			tvQuantity.setText("" + quantity);
			tvPrice.setText("Price: "+(AppUtility.selectedProduct.getPrice()*quantity+" TK"));
		} else if (v.getId() == R.id.btn_minus) {
			if (quantity > 1) {
				quantity--;
				tvQuantity.setText("" + quantity);
				tvPrice.setText("Price: "+(AppUtility.selectedProduct.getPrice()*quantity+" TK"));
			}

		} else if (v.getId() == R.id.btn_cart) {
			addToCart();
		}

	}
	private void addToCart()
	{
		RestAdapter adapter = new RestAdapter.Builder()
		.setEndpoint(Constants.API_END_POINT)
		.setRequestInterceptor(new AuthenticatedRequestInterceptor(this))
		.build();
	
		CartService cartService = adapter.create(CartService.class);
		int q=Integer.valueOf(tvQuantity.getText().toString());
		Order order = new Order(AppUtility.getCartId(ProductDetailsActivity.this),AppUtility.selectedProduct.getObjectId(), q);
		cartService.addToCart(order, new Callback<JsonElement>() {

			@Override
			public void failure(RetrofitError arg0) {
				simpleAlert(ProductDetailsActivity.this, "An error occured."+arg0.getMessage());
				
			}

			@Override
			public void success(JsonElement arg0, Response arg1) {
				
				simpleAlert(ProductDetailsActivity.this, "Product is added in the cart.");
				
			}

			
		});
	}
	public  void simpleAlert(Context context, String message) {
		AlertDialog.Builder bld = new AlertDialog.Builder(context,
				AlertDialog.THEME_HOLO_LIGHT);
		bld.setTitle(context.getResources().getString(R.string.app_name));
		bld.setMessage(message);
		bld.setCancelable(false);
		bld.setNeutralButton("Ok", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				finish();
				overridePendingTransition(R.anim.prev_slide_in, R.anim.prev_slide_out);
			}
		});
		bld.create().show();
	}

}
