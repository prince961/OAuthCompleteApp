package com.example.mohit.oauthcompleteapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    Button bLogin;
    EditText etUsername , etPassword;
    TextView tvRegister;
    String body;
    UserLocalStore userLocalStore;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressDialog = new ProgressDialog(LoginActivity.this);

        etUsername = (EditText) findViewById(R.id.etphone);
        etPassword = (EditText) findViewById(R.id.etPasswordLogin);
        userLocalStore = new UserLocalStore(getBaseContext());
        String password =  userLocalStore.getUserPassword();
        //Log.i("password",password);
        if (password == null){}
        else {
            Log.i("password",password);
            etUsername.setText(userLocalStore.getUserNumber());
            etPassword.setText(password);
        }



    }


    public void RegisterC(View view) {
        Intent intent= new Intent(this,Register.class);
        startActivity(intent);
    }

    public void LoginC(View view) {


        String phone = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        body = new String("{\"username\":\""+ phone +"\",\"password\":\""+ password +"\"}");

        new StoreUserDataAsyncTask().execute();
    }

    public class StoreUserDataAsyncTask extends AsyncTask<Void, Void, Void> {

        int responseCode;
        JSONArray jsonErrorArray;
        String errorMessage;
        String errorCodeString;
        String userEmail;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setCancelable(false);
            progressDialog.setTitle("Processing");
            progressDialog.setMessage("please wait...");
            progressDialog.show();


        }

        @Override
        protected Void doInBackground(Void... params) {

            BufferedReader reader = null;


            try {
                URL url = new URL("http://dummytesting.touristhelpgroup.com/");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setRequestProperty("Content-Type", "application/json");
                //int responseCode  = conn.getResponseCode();
                OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
                writer.write(body);
                //Sending the data to the server - This much is enough to send data to server
                //But to read the response of the server, you will have to implement the procedure below
                writer.flush();
                Log.i("custom_check", body);
                responseCode  = conn.getResponseCode();
                Log.i("response_code", Integer.toString(responseCode));


                if (responseCode >= 200 && responseCode < 400) {
                    // Create an InputStream in order to extract the response object
                    //InputStream is = conn.getInputStream();
                    StringBuilder sb = new StringBuilder();
                    reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    userLocalStore.SetUserLoggedIn(true);


                    String line;
                    while ((line = reader.readLine()) != null) {
                        //Read till there is something available
                        sb.append(line + "\n");     //Reading and saving line by line - not all at once
                    }
                    line = sb.toString();           //Saving complete data received in string, you can do it differently

                    JSONObject jsonObject =new JSONObject(line);
                    userEmail = jsonObject.getString("user_email");
                    userLocalStore.storeUserEmail(userEmail);

                    //Just check to the values received in Logcat
                    Log.i("custom_check", "The values received in the store part are as follows:");
                    Log.i("user_email", userEmail);
                    Log.i("response_code", Integer.toString(responseCode));


                }
                else {
                    //InputStream is = conn.getErrorStream();
                    StringBuilder sb = new StringBuilder();
                    reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        //Read till there is something available
                        sb.append(line + "\n");     //Reading and saving line by line - not all at once
                    }
                    line = sb.toString();
                    //Saving complete data received in string, you can do it differently
                    JSONObject jsonObject = new JSONObject(line);
                    //jsonErrorArray = jsonObject.getJSONArray("errors");
                    //JSONObject jsonErrorObject = jsonErrorArray.getJSONObject(0);
                    errorMessage = jsonObject.getString("message");
                    errorCodeString = jsonObject.getString("code");

                    //Just check to the values received in Logcat
                    //Toast.makeText(Register.this, "there is some error", Toast.LENGTH_SHORT).show();



                    Log.i("custom_check", "The values received in the store part are as follows:");
                    Log.i("custom_check", line);
                    Log.i("custom_check", errorMessage);
                    Log.i("Response_Code", Integer.toString(responseCode));

                }


                //Data Read Procedure - Basically reading the data comming line by line


            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();     //Closing the
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }




        @Override
        protected void onPostExecute(Void aVoid) {
            //progressDialog.dismiss();
            if (responseCode >= 200 && responseCode < 400) {
                DownloadUserDetails downloadUserDetails = new DownloadUserDetails();
                downloadUserDetails.execute();
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
            }
            else {
                if (Objects.equals(errorCodeString, "[jwt_auth] invalid_username")){
                    Toast.makeText(getBaseContext(), "Number is not registered" , Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getBaseContext(), "Incorrect Password" , Toast.LENGTH_LONG).show();

                }
                //InputStream is = conn.getErrorStream();

            }
            //userCallback.done(null);
            super.onPostExecute(aVoid);
        }
    }
    private class DownloadUserDetails extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... voids) {

            InputStream inputStream2 = null;
            String data2 = null;
            String userEmail = userLocalStore.getUserEmail();
            String userUrl = "http://dummytesting.touristhelpgroup.com//wc-api/v3/customers/email/"+userEmail+"?consumer_key=ck_638caaf46271a320075ecee01e89581f91644b98&consumer_secret=cs_0f5fe1845a21396a459fc3961a8255d15a62970b";
            try {
                URL url = new URL(userUrl);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                inputStream2 = urlConnection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream2));
                StringBuffer sb2 = new StringBuffer();

                String line = "";
                while ((line = br.readLine()) != null) {
                    sb2.append(line);
                }
                data2 = sb2.toString();
                br.close();

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if ( inputStream2 != null){
                        inputStream2.close();}
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            try {
                JSONObject jsonObject = new JSONObject(data2);
                JSONObject jsonObjectUser = jsonObject.getJSONObject("customer");
                String UserName = jsonObjectUser.getString("first_name");
                int UserId = jsonObjectUser.getInt("id");
                JSONObject shippingAddressJobject = jsonObjectUser.getJSONObject("billing_address");
                String UserPhone = shippingAddressJobject.getString("phone");
                String UserAddress = shippingAddressJobject.getString("address_1");

                userLocalStore.storeUserData(UserName,UserPhone,UserAddress,UserId);
                Log.i("userdatastoredName",userLocalStore.getUserName());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {

            progressDialog.hide();
        }
    }
}
