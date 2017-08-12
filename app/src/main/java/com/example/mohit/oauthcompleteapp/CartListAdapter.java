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
 * Created by mohit on 10/6/2016.
 */

public class CartListAdapter extends ArrayAdapter<ModelProducts> {

    private int layout;
    private List<ModelProducts> productList;
    private Controller controller;
    TextView totalAmountTV;

    public CartListAdapter(Context context, int resource, List<ModelProducts> productList, Controller controller, TextView totalAmount) {
        super(context, resource, productList);
        this.layout = resource;
        this.productList = productList;
        this.controller = controller;
        this.totalAmountTV = totalAmount;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        CartViewHolder mainViewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(getContext());
        final ModelProducts modelProducts = productList.get(position);
        if(convertView == null){
            convertView = inflater.inflate(layout,parent,false);
            mainViewHolder = new CartViewHolder();
            //mainViewHolder.thumbnail = (ImageView) convertView.findViewById(R.id.item_image);

            mainViewHolder.title = (TextView) convertView.findViewById(R.id.item_title);
            mainViewHolder.price = (TextView) convertView.findViewById(R.id.item_price1);
            mainViewHolder.addBtn = (ImageView)convertView.findViewById(R.id.addBtn);
            mainViewHolder.reduceBtn = (ImageView) convertView.findViewById(R.id.reduceBtn);
            mainViewHolder.quantity = (TextView) convertView.findViewById(R.id.tvQuantityCart);
            //mainViewHolder.deleteFromCartIv = (ImageView) convertView.findViewById(R.id.deleteCartIv);

            convertView.setTag(mainViewHolder);

        }
        else{
            mainViewHolder = (CartViewHolder) convertView.getTag();
        }

        final CartViewHolder finalMainViewHolder = mainViewHolder;
        mainViewHolder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String StringQuantity = mainViewHolder.quantity.getText().toString();
                int quantity = productList.get(position).getProductQuantity();
                //int quantity = Integer.parseInt(StringQuantity);
                int newQuant = quantity+1;
                productList.get(position).setProductQuantity(newQuant);
                controller.getCart().getCartProducts().get(position).setProductQuantity(newQuant);
                finalMainViewHolder.quantity.setText(Integer.toString(newQuant));
                refreshTotalAmountTv();
                notifyDataSetChanged();
                //if(quantity == 0){ controller.getCart().setProducts(productList.get(position));}

            }
        });

        /*
        mainViewHolder.deleteFromCartIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //int position1 = position;
                //productList.remove(productList.get(position));
                //controller.getCart().removeProduct(tempProduct);
                // productList.remove(productList.get(position));
                //controller.getCart().getCartProducts().get(position).setProductQuantity(0);
                //CartListAdapter.this.notifyDataSetChanged();
                //refreshTotalAmountTv();
                //productList.clear();
                //productList.addAll(controller.getCart().getCartProducts());
                //notifyDataSetChanged();
                ModelProducts theProduct = controller.getCart().getProducts(position);
                controller.getCart().getCartProducts().get(position).setProductQuantity(0);
                controller.getCart().removeProduct(theProduct);
                CartListAdapter.this.notifyDataSetChanged();
                refreshTotalAmountTv();
                notifyDataSetChanged();
            }
        }); */

        final CartViewHolder finalMainViewHolder1 = mainViewHolder;
        mainViewHolder.reduceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String StringQuantity = productList.get(position).getProductQuantity();
                ModelProducts theProduct = controller.getCart().getProducts(position);
                int quantity = productList.get(position).getProductQuantity();

                if (quantity>1) {
                    int newQuant = quantity - 1;
                    //ModelProducts selectedProduct = productList.get(position);
                    //if(newQuant == 0){controller.getCart().removeProduct(productList.get(position));}
                    productList.get(position).setProductQuantity(newQuant);
                    finalMainViewHolder1.quantity.setText(Integer.toString(newQuant));
                    refreshTotalAmountTv();
                    notifyDataSetChanged();
                }
                if (quantity == 1){
                    //productList.remove(theProduct);
                    //controller.getCart().getCartProducts().get(position).setProductQuantity(0);
                    productList.get(position).setProductQuantity(0);
                    controller.getCart().removeProduct(theProduct);
                    CartListAdapter.this.notifyDataSetChanged();
                    refreshTotalAmountTv();
                    notifyDataSetChanged();
                }
            }
        });
        //mainViewHolder.title.setText(getItem(position).getProductName());
        mainViewHolder.title.setText(productList.get(position).getProductName());
        //Bitmap myBitmap = BitmapFactory.decodeFile(controller.getCart().getCartProducts().get(position).getImage());
        //mainViewHolder.thumbnail.setImageBitmap(myBitmap);
        //mainViewHolder.thumbnail.setImageResource(R.mipmap.ic_launcher);
        mainViewHolder.price.setText(Integer.toString(getItem(position).getProductPrice()));
        mainViewHolder.quantity.setText(Integer.toString(getItem(position).getProductQuantity()));
        return convertView;
    }

    public class CartViewHolder {
        ImageView thumbnail;
        TextView title;
        TextView price;
        ImageView addBtn;
        ImageView reduceBtn;
        TextView quantity;
        //ImageView deleteFromCartIv;
    }

    private void refreshTotalAmountTv() {
        int totalAmount = 0;
        int numberProducts = productList.size();
        for (int i = 0; i < numberProducts; i++) {
            int pQuantity = productList.get(i).getProductQuantity();
            int pPrice = productList.get(i).getProductPrice();
            int pAmountI = pPrice * pQuantity;

            totalAmount = totalAmount + pAmountI;
        }

        totalAmountTV.setText(Double.toString(totalAmount));
    }
}