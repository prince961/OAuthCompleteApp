package com.example.mohit.oauthcompleteapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
//import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.app.FragmentManager;
import android.widget.ImageView;

import com.example.mohit.oauthcompleteapp.services.HMACSha1SignatureService;
import com.example.mohit.oauthcompleteapp.services.TimestampServiceImpl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    FragmentManager fragmentManager = getFragmentManager();
    UserLocalStore userLocalStore ;
    private Controller controller = null;
    final String BASE_SITE = "www.digyfi.in";
    final String BASE_URL = "https://"+BASE_SITE+"/wp-json/wc/v2/products?";
    final String COSTUMER_KEY="ck_f247275c612feeaeea7a13d2d2dd817bf6557586";
    String COSTUMER_SECRET ="cs_cb384cbe4c2b3566ae802786aa1e9798851ac27e";
    String METHORD = "GET";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        userLocalStore = new UserLocalStore(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (checkUserLocalStore()){
            setContentView(R.layout.activity_main);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            ImageView cartLogo = (ImageView) toolbar.findViewById(R.id.cartOnToolbar);
            fragmentManager.beginTransaction().replace(R.id.content_Frame, new CategoriesFragmenent2()).commit();
            cartLogo.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    //FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.content_Frame, new CartFragment()).addToBackStack(null).commit();
                }
            });

            controller = (Controller) getApplicationContext();

            if (!controller.isAllProductsAdded()){
                DownloadProducts downloadProducts = new DownloadProducts();
                downloadProducts.execute();}

/*
            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    //      .setAction("Action", null).show();
                    fragmentManager.beginTransaction().replace(R.id.content_Frame, new CartFragment()).addToBackStack(null).commit();

                }
            });
*/
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);



        }else {
            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
            //Toast.makeText(getApplication(), Boolean.toString(userLocalStore.getUserLoggedIn()), Toast.LENGTH_LONG).show();
        }


    }

    private boolean checkUserLocalStore (){
        return userLocalStore.getUserLoggedIn();
    }

    private class DownloadProducts extends AsyncTask<String, Void,String> {
        String data = null;
        InputStream iStream = null;


        @Override
        protected String doInBackground(String... url1) {
            /*
            final String nonce = new TimestampServiceImpl().getNonce();
            final String timestamp = new TimestampServiceImpl().getTimestampInSeconds();
            // GENERATED NONCE and TIME STAMP
            Log.d("nonce",nonce);
            Log.d("time",timestamp);

            String firstEncodedString = METHORD+"&"+ encodeUrl(BASE_URL);
            Log.d("firstEmcodedString", firstEncodedString);

            String parameterString="oauth_consumer_key="+COSTUMER_KEY+"&oauth_nonce="+nonce+"&oauth_signature_method=HMAC-SHA1&oauth_timestamp="+timestamp+"&oauth_version=1.0";
            String secoundEncodedString="&"+encodeUrl(parameterString);
            Log.d("secoundEncodedString",secoundEncodedString);

            String baseString=firstEncodedString+secoundEncodedString;

            //THE BASE STRING AND COSTUMER_SECRET KEY IS USED FOR GENERATING SIGNATURE
            Log.d("baseString",baseString);

            String signature=new HMACSha1SignatureService().getSignature(baseString,COSTUMER_SECRET,"");
            Log.d("SignatureBefore",signature);

            //Signature is encoded before parsing (ONLY FOR THIS EXAMPLE NOT NECESSARY FOR LIB LIKE RETROFIT,OKHTTP)
            signature=encodeUrl(signature);

            Log.d("SignatureAfter ENCODING",signature);

            final String finalSignature = signature;//BECAUSE I PUT IN SIMPLE THREAD NOT NECESSARY
            String parseUrl=BASE_URL+"?"+"&oauth_signature_method=HMAC-SHA1&oauth_consumer_key="+COSTUMER_KEY+"&oauth_version=1.0&oauth_timestamp="+timestamp+"&oauth_nonce="+nonce+"&oauth_signature="+ finalSignature;
            //Log.i("finalURL",parseUrl);

             */

            String parseUrl = BASE_URL+"consumer_key="+COSTUMER_KEY+"&consumer_secret="+COSTUMER_SECRET;

            try {
                URL url = new URL(parseUrl);
                Log.i("URL", url.toString());
                //URL url = new URL(strUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(5000);
                conn.setRequestMethod("GET");
                conn.setDoOutput(true);
                conn.setRequestProperty("Content-Type", "application/json");
                conn.connect();
                iStream = conn.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
                StringBuffer sb = new StringBuffer();

                String line = "";
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                data = sb.toString();
                //Log.i("String_InputString", data);
                br.close();

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (iStream != null){
                        iStream.close();}
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            Log.i("data",data);
            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.i("s",s);
            super.onPostExecute(s);
            JSONObject jObject = null;
            JSONArray jArray = null;
            try {
                jArray = new JSONArray(s);
                jObject =(JSONObject)  jArray.get(0);
                Log.i("jobject",jObject.toString());
            } catch (JSONException e) {
                Log.i("s",s);
                e.printStackTrace();
            }
            JsonParserProducts jsonParserProducts = new JsonParserProducts();
            ArrayList<ModelProducts> AllProducts = jsonParserProducts.parse(jArray);
            //ModelProducts modelProducts = AllProducts.get(0);
            //modelProducts.getCategories()1
            controller.addAllProducts(AllProducts);
            controller.setAllProductsAdded(true);
            Log.i("main_act_allproducts",Boolean.toString(controller.isAllProductsAdded()));
            //DownloadUserDetails downloadUserDetails = new DownloadUserDetails();
            //downloadUserDetails.execute();

        }
    }


    public String encodeUrl(String url)
    {
        String encodedurl="";
        try {

            encodedurl = URLEncoder.encode(url,"UTF-8");
            Log.d("Encodeurl", encodedurl);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return encodedurl;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_menu) {
            fragmentManager.beginTransaction().replace(R.id.content_Frame, new CategoriesFragmenent2()).addToBackStack(null).commit();
        } else if (id == R.id.nav_cart) {
            fragmentManager.beginTransaction().replace(R.id.content_Frame, new CartFragment()).addToBackStack(null).commit();

        } else if (id == R.id.contact_us) {

        } else if (id == R.id.nav_call) {
            fragmentManager.beginTransaction().replace(R.id.content_Frame, new CallFragment()).addToBackStack(null).commit();

        } else if (id == R.id.nav_User) {


        } else if (id == R.id.nav_Logout) {
            userLocalStore.SetUserLoggedIn(false);
            userLocalStore.ClearUserData();
            Intent intent = new Intent(getBaseContext(),LoginActivity.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
