package com.buetcrt.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.buetcrt.csefest.R;
import com.buetcrt.model.Order;

public class OrderAdapter extends ArrayAdapter<Order> {
	
	private LayoutInflater inflater;
	private int resource;
	private List<Order> orders;

	public OrderAdapter(Context context, int resource, List<Order> orders) {
		super(context, resource, orders);
		
		this.inflater = (LayoutInflater) 
				context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.resource = resource;
		this.orders = orders;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			view = inflater.inflate(resource, null);
		}
		
		TextView tvId = (TextView) view.findViewById(R.id.tv_id);
		TextView tvQuantity = (TextView) view.findViewById(R.id.tv_quantity);
		TextView tvSubtotal = (TextView) view.findViewById(R.id.tv_subtotal);
		
		Order order = orders.get(position);
		
		tvId.setText(order.getProduct().getObjectId());
		tvQuantity.setText(Integer.toString(order.getQuantity()));
		tvSubtotal.setText(Integer.toString(order.getSubTotal()));
		
		return view;
	}

}
