package com.example.mohit.oauthcompleteapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.joda.time.format.ISODateTimeFormat;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;


/**
 * Created by princ on 25-11-2016.
 */

public class OrderPlacedFragment extends Fragment {

    Controller controller = null;
    View myView;
    TextView thankyouName,orderId,OrderDAteTime,OrderAddress,totalAmount;
    String name,date,address,amountTotal;
    Date formattedDate;
    int Orderid;

    private ArrayList<ModelProducts> productsOrdered = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.order_placed,container,false);

        //FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        //fab.setVisibility(View.VISIBLE);

        thankyouName = (TextView) myView.findViewById(R.id.thanks);
        orderId = (TextView) myView.findViewById(R.id.orderId);
        OrderDAteTime = (TextView) myView.findViewById(R.id.OrderDAteTime);
        OrderAddress = (TextView) myView.findViewById(R.id.OrderAddress);
        totalAmount = (TextView) myView.findViewById(R.id.totalAmountOP);
        ListView listView = (ListView) myView.findViewById(R.id.listViewOrderItems);
        controller = (Controller) getActivity().getApplicationContext();
        String jsonResponse = controller.getOrderPlacedResponseJson();
        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONObject order = jsonObject.getJSONObject("order");
            Orderid = order.getInt("id");
            amountTotal = order.getString("total");
            JSONArray lineItems = order.getJSONArray("line_items");
            int lineItemSize = lineItems.length();
            for (int i = 0; i < lineItemSize; i++){
                JSONObject jsonObject1 = lineItems.getJSONObject(i);
                int Productid = jsonObject1.getInt("product_id");
                int productQty = jsonObject1.getInt("quantity");
                String productName = jsonObject1.getString("name");
                int price = jsonObject1.getInt("price");
                ModelProducts modelProducts = new ModelProducts(productName,null,price,productQty,Productid,null,null);
                productsOrdered.add(modelProducts);
            }

            JSONObject customer = order.getJSONObject("customer");
            date = order.getString("created_at");
            JSONObject billingAddress = customer.getJSONObject("billing_address");
            name = billingAddress.getString("first_name");
            String address1 = billingAddress.getString("address_1");
            String address2 = billingAddress.getString("address_2");
            String city = billingAddress.getString("city");
            String state = billingAddress.getString("state");
            if (address2 == null){
                address = address1 + ", " +city+", "+state;
            }else {
                address = address1 + ", "+address2+", " +city+", "+state;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        thankyouName.setText(String.format("Thank You %s", name));
        orderId.setText(String.format("Order id: %s", Orderid));

        DateTime dateTime = ISODateTimeFormat.dateTimeParser().parseDateTime(date);
        LocalTime time = dateTime.toLocalTime();


        OrderDAteTime.setText("Time: " +dateTime.toString("HH:mm"));
        totalAmount.setText(amountTotal);
        OrderAddress.setText("Address: "+address);

        ListAdapterOP hvhvhjvh = new ListAdapterOP(getActivity().getBaseContext(),R.layout.order_placed_list_item,productsOrdered,controller);
        listView.setAdapter(hvhvhjvh);
        //Log.i("orderProduct",productsOrdered.get(1).getProductName());


        controller.deleteOrderPlacedJSon();
        return myView;

    }











}
