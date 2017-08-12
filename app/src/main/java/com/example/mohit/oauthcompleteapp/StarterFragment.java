package com.example.mohit.oauthcompleteapp;

import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by princ on 25-11-2016.
 */

public class StarterFragment extends Fragment {


    Controller controller = null;
    View myView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.activity_noodle,container,false);

        //FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        //fab.setVisibility(View.VISIBLE);

        ImageView categoryImage = (ImageView) myView.findViewById(R.id.IvNoodleCat);
        Drawable catImage = getResources().getDrawable(R.drawable.appetizers);
        categoryImage.setImageDrawable(catImage);


        ListView listViewAppetizer = (ListView) myView.findViewById(R.id.LvNoodle);
        controller = (Controller) getActivity().getApplicationContext();
        //assuming if 201 is there then all other category products will be there
        boolean productInCart = controller.checkAppetizerId(201);

        if(productInCart == false){



        return myView;}else {
            ArrayList<ModelProducts> AppetizerList = controller.getAppetizerProductAl();
            ListAdapterImagLess listAdapterImagLess = new ListAdapterImagLess(getActivity().getBaseContext(), R.layout.list_item_imageless, AppetizerList, controller);
            listViewAppetizer.setAdapter(listAdapterImagLess);
            return myView;
        }
    }



  //n ftxg = fragmentManager.beginTransaction().replace( R.id.fragment, new MyFragment() ).addToBackStack( "tag" ).commit();







}
