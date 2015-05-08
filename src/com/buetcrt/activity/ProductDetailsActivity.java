package com.buetcrt.activity;

import com.buetcrt.csefest.R;
import com.buetcrt.lazylist.ImageLoader;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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

		// imageLoader.DisplayImage(url, imageView);

	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_plus) {
			quantity++;
			tvQuantity.setText("" + quantity);
		} else if (v.getId() == R.id.btn_minus) {
			if (quantity > 1) {
				quantity--;
				tvQuantity.setText("" + quantity);
			}

		} else if (v.getId() == R.id.btn_cart) {
			
		}

	}

}
