package com.example.mohit.oauthcompleteapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by princ on 24-01-2017.
 */

public class JsonParserProducts {

    public ArrayList<ModelProducts> parse(JSONObject jObject) {

        JSONArray jProducts = null;
        try {
            jProducts = jObject.getJSONArray("products");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return getProducts(jProducts);
    }

    private ArrayList<ModelProducts> getProducts(JSONArray jProducts) {
        int productCount = jProducts.length();
        ArrayList<ModelProducts> productList = new ArrayList<ModelProducts>();
        ModelProducts product = null;

        for (int i = 0; i < productCount; i++) {
            try {
                product = getProduct((JSONObject) jProducts.get(i));
                productList.add(product);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return productList;
    }

    private ModelProducts getProduct(JSONObject jProduct) {

        //getting the image array cuz its inside another json object unlike the other details
        JSONArray imgArray = null ;
        JSONArray categories = null;
        try {
            imgArray = jProduct.getJSONArray("images");
            categories = jProduct.getJSONArray("categories");
        }catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject imgObject = null;
        try {
            imgObject = imgArray.getJSONObject(0);
        }catch (JSONException e) {
            e.printStackTrace();
        }

        String[] categoriesArray = new String[categories.length()];
        for (int j = 0; j < categories.length(); j++){
            try {
                categoriesArray[j] = categories.getString(j);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        String title = "";
        int price = 0;
        String description = "";
        String imageLink = "";
        int id = 0;

        try {
            title = jProduct.getString("title");
            price = jProduct.getInt("price");
            description = jProduct.getString("description");
            id = jProduct.getInt("id");
            if (imgObject != null) {
                imageLink = imgObject.getString("src");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return new ModelProducts(title,description,price,0,id,categoriesArray,imageLink);
    }
}


