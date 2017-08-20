package com.example.mohit.oauthcompleteapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by princ on 25-11-2016.
 */

public class NoodleFragment extends Fragment {

    Controller controller = null;
    View myView;
    ArrayList<ModelProducts> noodleProducts;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.activity_noodle, container, false);

        //FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        //fab.setVisibility(View.VISIBLE);


        ListView listViewNoodle = (ListView) myView.findViewById(R.id.LvNoodle);
        controller = (Controller) getActivity().getApplicationContext();

        noodleProducts = controller.getCategoryProducts("Starters");
        //noodleProducts = controller.getAllProducts();
        ModelProducts modelProducts = noodleProducts.get(0);
        String category = Arrays.toString(modelProducts.getCategories());
        ListAdapterImagLess listAdapterImagLess = new ListAdapterImagLess(getActivity().getBaseContext(), R.layout.list_item_imageless, noodleProducts, controller);
        //listViewNoodle.setAdapter(null);
        listViewNoodle.setAdapter(listAdapterImagLess);
        Log.i("listViewCount", Integer.toString(noodleProducts.size()));
        Log.i("TotalProducts", Integer.toString(controller.getAllProducts().size()));
        Log.i("categoryyy",category);
        return myView;



    }

    @Override
    public void onResume() {
        super.onResume();
        //ArrayList<ModelProducts> noodleProducts = controller.getCategoryProducts("Starters");
    }

    @Override
    public void onStart() {
        super.onStart();
        //ArrayList<ModelProducts> noodleProducts = controller.getCategoryProducts("Starters");
    }
}



  //n ftxg = fragmentManager.beginTransaction().replace( R.id.fragment, new MyFragment() ).addToBackStack( "tag" ).commit();








