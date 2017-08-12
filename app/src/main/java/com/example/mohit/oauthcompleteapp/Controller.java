package com.example.mohit.oauthcompleteapp;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by mohit on 10/5/2016.
 */

public class Controller extends Application {




    private boolean allProductsAdded;



    private ArrayList<ModelProducts> AllProducts = new ArrayList<ModelProducts>();
    private ArrayList<ModelProducts> noodleProducts = new ArrayList<ModelProducts>();
    private ArrayList<ModelProducts> AppetizerProductAl = new ArrayList<ModelProducts>();
    private ArrayList<ModelProducts> Breads = new ArrayList<ModelProducts>();
    private ArrayList<ModelProducts> VegMainCourse = new ArrayList<ModelProducts>();
    private ArrayList<ModelProducts> NonVegMainAl = new ArrayList<ModelProducts>();

    public String getOrderPlacedResponseJson() {
        return OrderPlacedResponseJson;
    }

    public void setOrderPlacedResponseJson(String orderPlacedResponseJson) {
        OrderPlacedResponseJson = orderPlacedResponseJson;
    }

    public void deleteOrderPlacedJSon(){
        OrderPlacedResponseJson = null;
    }

    private String OrderPlacedResponseJson = null;
    private  ModelCart myCart = new ModelCart();


    public ModelProducts getNoodleP(int pPosition) {

        return noodleProducts.get(pPosition);
    }

    public void setAllProductsAdded(boolean allProductsAdded) {
        this.allProductsAdded = allProductsAdded;
    }

    public boolean isAllProductsAdded() {
        return allProductsAdded;
    }

    public void addAppetizertoAl(List<ModelProducts> productList){
        AppetizerProductAl.addAll(productList);}

    public ArrayList<ModelProducts> getAppetizerProductAl(){
        return AppetizerProductAl;
    }

    public void addNoodlePs(List<ModelProducts> productList){
        noodleProducts.addAll(productList);
    }

    public void setProducts(ModelProducts Products) {

        AllProducts.add(Products);

    }

    public void resetAllProductsQty(){
        ArrayList<ModelProducts> cartProducts = myCart.getCartProducts();

        for (int j = 0; j < cartProducts.size(); j++) {
          int id =  cartProducts.get(j).getId();
            setProductQty0(id);
        }

        cartProducts.removeAll(cartProducts);

    }

    public void setProductQty0(int id) {


        for (int j = 0; j < AllProducts.size(); j++) {
            if (AllProducts.get(j).getId() == id) {
                AllProducts.get(j).setProductQuantity(0);
                break;
            }
        }


    }

    public ModelCart getCart() {

        return myCart;

    }

    public int getProductsArraylistSize() {

        return AllProducts.size();
    }

    public boolean checkid(int id) {

        boolean isTrue = false;
        for (int j = 0; j < noodleProducts.size(); j++) {
            if (noodleProducts.get(j).getId() == id) {
                isTrue =true;
                break;
            }else {isTrue = false;}
        }

        return isTrue;
    }

    public ArrayList<ModelProducts> getCategoryProducts(String category) {
        ArrayList<ModelProducts> categoryProducts = new ArrayList<>();
        for (int j = 0; j < AllProducts.size(); j++) {
            String[] categories = AllProducts.get(j).getCategories();
            int categoryStringSize = categories.length;


            for (int k = 0 ; k< categoryStringSize ; k++ ){
                String StringCAtegory = categories[k];
                if (Objects.equals(StringCAtegory, category)){

                    categoryProducts.add(AllProducts.get(j));

                }

            }

        }

        return categoryProducts;
    }

    public boolean checkAppetizerId(int id) {

        boolean isTrue = false;
        for (int j = 0; j < AppetizerProductAl.size(); j++) {
            if (AppetizerProductAl.get(j).getId() == id) {
                isTrue =true;
                break;
            }else {isTrue = false;}
        }

        return isTrue;
    }

    public ArrayList<ModelProducts> getAllProducts() {
        return AllProducts;
    }

    public ArrayList<ModelProducts> getNonVegMainAl() {
        return NonVegMainAl;
    }

    public void setNonVegMainAl(ArrayList<ModelProducts> nonVegMainAl) {
        NonVegMainAl.addAll(nonVegMainAl);
    }

    public boolean checkNonVegMainCourseId(int id) {

        boolean isTrue = false;
        for (int j = 0; j < NonVegMainAl.size(); j++) {
            if (NonVegMainAl.get(j).getId() == id) {
                isTrue =true;
                break;
            }else {isTrue = false;}
        }

        return isTrue;
    }

    public ArrayList<ModelProducts> getBreads() {
        return Breads;
    }

    public void setBreads(ArrayList<ModelProducts> breads) {
        Breads.addAll(breads);
    }

    public ArrayList<ModelProducts> getVegMainCourse() {
        return VegMainCourse;
    }

    public void setVegMainCourse(ArrayList<ModelProducts> vegMainCourse) {
        VegMainCourse.addAll(vegMainCourse);
    }

    public boolean checkVegMainCourseId(int id) {

        boolean isTrue = false;
        for (int j = 0; j < VegMainCourse.size(); j++) {
            if (VegMainCourse.get(j).getId() == id) {
                isTrue =true;
                break;
            }else {isTrue = false;}
        }

        return isTrue;
    }

    public boolean checkbreadsId(int id) {

        boolean isTrue = false;
        for (int j = 0; j < Breads.size(); j++) {
            if (Breads.get(j).getId() == id) {
                isTrue =true;
                break;
            }else {isTrue = false;}
        }

        return isTrue;
    }

    public void addAllProducts(ArrayList<ModelProducts> allProducts) {
        AllProducts.addAll(allProducts);
    }
}