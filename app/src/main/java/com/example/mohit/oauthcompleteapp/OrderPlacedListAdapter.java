package com.example.mohit.oauthcompleteapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by princ on 28-01-2017.
 */

 class OrderPlacedListAdapter extends ArrayAdapter<ModelProducts> {

    private int layout;
    private ArrayList<ModelProducts> productsArrayList;

    public OrderPlacedListAdapter(Context context, int resource, ArrayList<ModelProducts> productList) {
        super(context, resource);
        this.layout = resource;
        this.productsArrayList = productList;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        OPProductsListViewHolder opProductsListViewHolder = null;
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());

        if (convertView == null){
            convertView = layoutInflater.inflate(layout,parent,false);
            opProductsListViewHolder = new OPProductsListViewHolder();
            opProductsListViewHolder.title = (TextView) convertView .findViewById(R.id.LiItemOP);
            opProductsListViewHolder.price = (TextView) convertView.findViewById(R.id.LiPriceOP);
            opProductsListViewHolder.qty = (TextView) convertView.findViewById(R.id.LiQty);

           convertView.setTag(opProductsListViewHolder);
        }else {
            opProductsListViewHolder = (OPProductsListViewHolder) convertView.getTag();
        }

        final OPProductsListViewHolder abc = opProductsListViewHolder;

        abc.title.setText(getItem(position).getProductName());
        int quantity = getItem(position).getProductQuantity();
        int unitPRice = getItem(position).getProductPrice();
        abc.qty.setText(Integer.toString(quantity));
        abc.price.setText(Integer.toString(quantity*unitPRice));

        Log.i("title",getItem(position).getProductName());
        return convertView;
    }

    private class OPProductsListViewHolder{
        TextView title;
        TextView qty;
        TextView price;
    }
}
