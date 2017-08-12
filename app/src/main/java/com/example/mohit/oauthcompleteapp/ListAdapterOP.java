package com.example.mohit.oauthcompleteapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by mohit on 10/5/2016.
 */

public class ListAdapterOP extends ArrayAdapter<ModelProducts> {

    private int layout;
    private List<ModelProducts> productList;
    Controller controller;

    public ListAdapterOP(Context context, int resource, List<ModelProducts> productList, Controller controller){
        super(context,resource,productList);
        this.productList = productList;
        this.layout = resource;
        this.controller = controller;
    }



    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ProductListViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(getContext());

        if(convertView == null){
            convertView = inflater.inflate(layout,parent,false);
            viewHolder = new ProductListViewHolder();
            //viewHolder.thumbnail = (ImageView) convertView.findViewById(R.id.item_image);

            viewHolder.title = (TextView) convertView.findViewById(R.id.LiItemOP);
            viewHolder.qty = (TextView) convertView.findViewById(R.id.LiQty);
            //viewHolder.title.setText(controller.getNoodleP(position).getProductName());
            //viewHolder.title.setText(getItem(8).getProductName());
            //viewHolder.thumbnail.setImageResource(controller.getProducts(position).getImage());
            //Bitmap myBitmap = BitmapFactory.decodeFile(controller.getProducts(position).getImage());
            //viewHolder.thumbnail.setImageResource(getItem(position).getImage());
            viewHolder.price = (TextView) convertView.findViewById(R.id.LiPriceOP);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ProductListViewHolder) convertView.getTag();
            //mainViewHolder.price.setText(Integer.toString(28));
            //mainViewHolder.title.setText(getItem(position).getProductName());
            //mainViewHolder.price.setText(getItem(position).getProductPrice());
        }
        final ProductListViewHolder finalViewHolder = viewHolder;

        //viewHolder.quantity.setText(Integer.toString(controller.getProducts(position).getProductQuantity()));
        viewHolder.title.setText(getItem(position).getProductName());
        finalViewHolder.qty.setText(Integer.toString(getItem(position).getProductQuantity()));
        viewHolder.price.setText(Integer.toString(getItem(position).getProductPrice()*getItem(position).getProductQuantity()));
        //final ListAdapterImagLess.ProductListViewHolder finalViewHolder = viewHolder;
        //final ProductListViewHolder finalViewHolder = viewHolder;


        return convertView;
    }

    private class ProductListViewHolder {
        ImageView thumbnail;
        TextView title;
        TextView price;
        TextView qty;
    }
}
