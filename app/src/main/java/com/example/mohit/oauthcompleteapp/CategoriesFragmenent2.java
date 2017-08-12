package com.example.mohit.oauthcompleteapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by princ on 24-11-2016.
 */

public class CategoriesFragmenent2 extends Fragment {

    View myView;
    ProgressDialog progressDialog;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.categories_fragment_layout,container,false);
        //FragmentManager fragmentManager = getFragmentManager();
        Controller controller = (Controller) getActivity().getApplicationContext();
        Boolean allProductsAdded = controller.isAllProductsAdded();

        //FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        //fab.setVisibility(View.VISIBLE);

        //FragmentManager fragmentManager = getFragmentManager();

        ImageView noodleImage = (ImageView) myView.findViewById(R.id.noodleImage);
        //Button button = new Button()
        noodleImage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Controller acontroller = (Controller) getActivity().getApplicationContext();
                Boolean allProductsAdded = acontroller.isAllProductsAdded();
                Log.i("Download_completed", Boolean.toString(allProductsAdded));
                if (allProductsAdded) {
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.content_Frame, new NoodleFragment()).addToBackStack(null).commit();
                }
                else {

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (allProductsAdded) {
                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.content_Frame, new NoodleFragment()).addToBackStack(null).commit();
                    }
                    else {
                        Toast.makeText(getActivity().getApplication(), "Please check your internet Connectivity", Toast.LENGTH_SHORT).show();
                    }



                }

            }
        });

        ImageView starterImage = (ImageView) myView.findViewById(R.id.imageView3);
        //Button button = new Button()
        starterImage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_Frame, new StarterFragment()).addToBackStack(null).commit();

            }
        });

        ImageView nonVegMain = (ImageView) myView.findViewById(R.id.imageView4);
        //Button button = new Button()
        nonVegMain.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_Frame, new NonVegMainCourseFragment()).addToBackStack(null).commit();

            }
        });
        ImageView VegMain = (ImageView) myView.findViewById(R.id.imageView2);
        //Button button = new Button()
        VegMain.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_Frame, new VegMainCourseFragment()).addToBackStack(null).commit();

            }
        });

        ImageView Breads = (ImageView) myView.findViewById(R.id.Breads);
        //Button button = new Button()
        Breads.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_Frame, new BreadsFragment()).addToBackStack(null).commit();

            }
        });
        return myView;
    }
}
