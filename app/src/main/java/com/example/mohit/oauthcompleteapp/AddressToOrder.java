package com.example.mohit.oauthcompleteapp;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by princ on 25-01-2017.
 */

public class AddressToOrder extends Fragment {

    EditText etname,etnumber,etemail,etaddress;
    UserLocalStore userLocalStore;
    String newName,newNumber,newAddress,newEmail;
    Controller controller;
    String lineItemString;
    JSONArray lineItemArray;
    int responseCode;
    String errorMessage;
    ProgressDialog progressDialog;

    View myView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.adress_to_order,container,false);
        etname = (EditText) myView.findViewById(R.id.etNameCD);
        etemail = (EditText) myView.findViewById(R.id.etEmailCD);
        etnumber = (EditText) myView.findViewById(R.id.etphoneCD);
        etaddress = (EditText) myView.findViewById(R.id.etAddressCD);
        userLocalStore = new UserLocalStore(getActivity().getBaseContext());
        etname.setText(userLocalStore.getUserName());
        etaddress.setText(userLocalStore.getUserAddress());
        etnumber.setText(userLocalStore.getUserNumber());
        etemail.setText(userLocalStore.getUserEmail());
        controller = (Controller) getActivity().getApplicationContext();

        Button button = (Button) myView.findViewById(R.id.bConfirmDetails);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                newName = etname.getText().toString();
                newNumber = etnumber.getText().toString();
                newAddress = etaddress.getText().toString();
                newEmail = etemail.getText().toString();
                lineItemArray = getLineItemJsonString();
                PlaceOrder placeOrder = new PlaceOrder();
                placeOrder.execute();


            }
        });
        return myView;
    }

    public JSONArray getLineItemJsonString() {
        ArrayList<ModelProducts> cartProducts = controller.getCart().getCartProducts();
        String lineItemJsonString = null;
        int numberProducts =cartProducts.size();
        JSONArray lineitemsArray =new JSONArray();
        JSONObject[] lineItem = new JSONObject[numberProducts] ;
        for (int i = 0; i < numberProducts; i++){
            lineItem[i] = new JSONObject();
            try {
                ModelProducts modelProduct = cartProducts.get(i);
                lineItem[i].put("product_id", Integer.toString(modelProduct.getId()));
                lineItem[i].put("quantity", Integer.toString(modelProduct.getProductQuantity()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            lineitemsArray.put(lineItem[i]);

        }



        return lineitemsArray;
    }

    public JSONArray getShippingLines() {
        JSONArray shippingLines = new JSONArray();
        JSONObject jObjectShipingLines = new JSONObject();
        try {
            jObjectShipingLines.put("method_id","flat_rate");
            jObjectShipingLines.put("method_title","Flat Rate");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        shippingLines.put(jObjectShipingLines);
        return shippingLines;
    }

    public JSONObject getPaymentDetails() {
        JSONObject paymentDetails = new JSONObject();
        try {
            paymentDetails.put("method_id","cod");
            paymentDetails.put("method_title","Cash On Delivery");
            paymentDetails.put("paid",true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return paymentDetails;
    }

    public JSONObject getBillingAdress() {
        JSONObject billingAdress = new JSONObject();
        try {
            billingAdress.put("first_name", newName);
            billingAdress.put("address_1", newAddress);
            billingAdress.put("city","Gurgaon");
            billingAdress.put("state", "Haryana");
            billingAdress.put("postcode","122002");
            billingAdress.put("country","IN");
            billingAdress.put("email",newEmail);
            billingAdress.put("phone", newNumber);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return billingAdress;
    }

    public JSONObject getCustomerJsonObject(JSONObject address2) {
        JSONObject customerJsonObject = new JSONObject();
        try {
            customerJsonObject.put("id", Integer.toString(userLocalStore.getUserId()));
            customerJsonObject.put("email",userLocalStore.getUserEmail());
            customerJsonObject.put("first_name", userLocalStore.getUserName());
            customerJsonObject.put("billing_address",address2);
            customerJsonObject.put("shipping_address",address2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return customerJsonObject;
    }

    private class PlaceOrder extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... voids) {

            String data = null;
            BufferedReader reader = null;
            String RequestBody = makeRequestBody();
            Log.i("body",RequestBody);

            String orderPlaceUrl = "https://www.jersershor.com/wc-api/v3/orders?consumer_key=ck_638caaf46271a320075ecee01e89581f91644b98&consumer_secret=cs_0f5fe1845a21396a459fc3961a8255d15a62970b";
            Log.i("StringUrl",orderPlaceUrl);
            try {
                URL url = new URL(orderPlaceUrl);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setConnectTimeout(15000);
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoOutput(true);
                urlConnection.setRequestProperty("Content-Type", "application/json");

                OutputStreamWriter writer = new OutputStreamWriter(urlConnection.getOutputStream());
                writer.write(RequestBody);
                writer.flush();
                responseCode = urlConnection.getResponseCode();

                if (responseCode >= 200 && responseCode < 400) {

                    StringBuilder sb = new StringBuilder();
                    reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        //Read till there is something available
                        sb.append(line + "\n");     //Reading and saving line by line - not all at once
                    }
                    line = sb.toString();
                    controller.setOrderPlacedResponseJson(line);
                    Log.i("orderMEssage",line);

                }
                else {
                    StringBuilder sb = new StringBuilder();
                    reader = new BufferedReader(new InputStreamReader(urlConnection.getErrorStream()));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        //Read till there is something available
                        sb.append(line + "\n");     //Reading and saving line by line - not all at once
                    }
                    line = sb.toString();
                    //Saving complete data received in string, you can do it differently
                    JSONObject jsonObject = new JSONObject(line);
                    JSONArray errors = jsonObject.getJSONArray("errors");
                    JSONObject errorObject = (JSONObject) errors.get(0);
                    errorMessage =  errorObject.getString("message");
                    String errorCode = errorObject.getString("code");
                    //jsonErrorArray = jsonObject.getJSONArray("errors");
                    //JSONObject jsonErrorObject = jsonErrorArray.getJSONObject(0);


                    //Just check to the values received in Logcat
                    //Toast.makeText(Register.this, "there is some error", Toast.LENGTH_SHORT).show();



                    Log.i("custom_check", "The values received in the store part are as follows:");
                    Log.i("custom_check", line);
                    Log.i("custom_check", errorMessage);
                    Log.i("Response_Code", Integer.toString(responseCode));
                }




            } catch (Exception e) {
                e.printStackTrace();
            } finally {

            }

           /* try {
                JSONObject jsonObject = new JSONObject(data);
                JSONObject jsonObjectUser = jsonObject.getJSONObject("customer");
                String UserName = jsonObjectUser.getString("first_name");
                int UserId = jsonObjectUser.getInt("id");
                JSONObject shippingAddressJobject = jsonObjectUser.getJSONObject("billing_address");
                String UserPhone = shippingAddressJobject.getString("phone");
                String UserAddress = shippingAddressJobject.getString("address_1");

                userLocalStore.storeUserData(UserName,UserPhone,UserAddress,UserId);
            } catch (JSONException e) {
                e.printStackTrace();
            } */
            return null;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setCancelable(false);
            progressDialog.setTitle("Processing");
            progressDialog.setMessage("please wait...");
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //progressDialog.dismiss();
            if (responseCode >= 200 && responseCode < 400) {
                controller.resetAllProductsQty();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_Frame, new OrderPlacedFragment()).commit();
            }
            else {
                new AlertDialog.Builder(getActivity().getBaseContext())
                        .setTitle("Order Not Placed")
                        .setMessage(errorMessage)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }
            progressDialog.hide();
            //userCallback.done(null);
            super.onPostExecute(aVoid);
        }
    }

    private String makeRequestBody() {
        JSONObject bodyObject = new JSONObject();
        JSONArray lineItems = lineItemArray;
        JSONArray shippingLines = getShippingLines();
        JSONObject paymentDetails = getPaymentDetails();
        JSONObject billingAddress = getBillingAdress();
        JSONObject customer = getCustomerJsonObject(billingAddress);
        JSONObject order = new JSONObject();
        try {
            order.put("customer_id", Integer.toString(userLocalStore.getUserId()));
            order.put("payment_details",paymentDetails);
            order.put("billing_address",billingAddress);
            order.put("shipping_address",billingAddress);
            order.put("line_items",lineItems);
            order.put("shipping_lines",shippingLines);
            order.put("customer",customer);

            bodyObject.put("order",order);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return bodyObject.toString();
    }
}
