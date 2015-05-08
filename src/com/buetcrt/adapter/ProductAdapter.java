package com.buetcrt.adapter;

import java.util.List;







import com.buetcrt.activity.ProductsActivity;
import com.buetcrt.csefest.R;
import com.buetcrt.csefest.dao.Products;
import com.buetcrt.lazylist.ImageLoader;
import com.buetcrt.model.NavMenuItem;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class ProductAdapter extends ArrayAdapter<Products>{

	private Context mContext;
	private ImageLoader imageLoader; 
	
	
	public ProductAdapter(Context context, int resource, List<Products> objects) {
		super(context, resource, objects);
		mContext=context;
		imageLoader=new ImageLoader((Activity)context);
		
	}
	private class ViewHolder{
		TextView tvName;
		ImageView ivIcon;
		TextView tvPrice;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder=null;
            LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
    		if (convertView == null) {
   
    				 convertView = mInflater.inflate(R.layout.row_products, null);
    			
    			holder = new ViewHolder();
    			holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
    			holder.ivIcon = (ImageView) convertView.findViewById(R.id.iv_prod);
    			holder.tvPrice = (TextView) convertView.findViewById(R.id.tv_price);

    			convertView.setTag(holder);
    		} else {
    			holder = (ViewHolder) convertView.getTag();
    		}
    	holder.tvName.setText(getItem(position).getName());
    	holder.tvPrice.setText("Price : "+getItem(position).getPrice()+" TK");
    	String image=getItem(position).getThumbURL();
    	image=image.replaceAll("\"","");
    	imageLoader.DisplayImage(image,holder.ivIcon);
    	
		
		return convertView;
	}
	
	

}
