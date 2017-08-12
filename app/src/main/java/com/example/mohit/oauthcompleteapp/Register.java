package com.example.mohit.oauthcompleteapp;

/**
 * Created by princ on 16-01-2017.
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

public class Register extends AppCompatActivity {

    EditText etname, etPhone, etpassword, etAddress, etEmail;
    Button bRegister;
    ProgressDialog progressDialog;
    String UserPhone, userPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
        etname = (EditText) findViewById(R.id.etName);
        etPhone = (EditText) findViewById(R.id.etphone);
        etEmail = (EditText) findViewById(R.id.etemail);
        etAddress = (EditText) findViewById(R.id.etAddress);
        etpassword = (EditText) findViewById(R.id.etPassword);
        bRegister = (Button) findViewById(R.id.bRegister);
    }



    //String body = new String("{\"customer\": {\r\n\"email\": \"5john.doe@example.com\",\r\n  \"first_name\": \"John\",\r\n  \"last_name\": \"Doe\",\r\n  \"username\": \"5john.doe\",\r\n  \"password\":\"123456\",\r\n  \"billing_address\": {\r\n    \"first_name\": \"John\",\r\n    \"last_name\": \"Doe\",\r\n    \"company\": \"\",\r\n    \"address_1\": \"969 Market\",\r\n    \"address_2\": \"\",\r\n    \"city\": \"San Francisco\",\r\n    \"state\": \"CA\",\r\n    \"postcode\": \"94103\",\r\n    \"country\": \"US\",\r\n    \"email\": \"john.doe@example.com\",\r\n    \"phone\": \"(555) 555-5555\"\r\n  },\r\n  \"shipping_address\": {\r\n    \"first_name\": \"John\",\r\n    \"last_name\": \"Doe\",\r\n    \"company\": \"\",\r\n    \"address_1\": \"969 Market\",\r\n    \"address_2\": \"\",\r\n    \"city\": \"San Francisco\",\r\n    \"state\": \"CA\",\r\n    \"postcode\": \"94103\",\r\n    \"country\": \"US\"\r\n  }\r\n}}");




    String body;

    public void RegisterD(View view) {

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Processing");
        progressDialog.setMessage("please wait...");
        progressDialog.show();

        UserPhone  =  etPhone.getText().toString();
        String UserEmail = etEmail.getText().toString();
        String UserName = etname.getText().toString();
        String UserAddress = etAddress.getText().toString();
        userPassword = etpassword.getText().toString();

        Boolean AllfieldsCorrectlyFilled = checkTheFields(UserName,UserAddress,UserEmail,UserPhone,userPassword);

        if (AllfieldsCorrectlyFilled){

         body = new String("{\"customer\": {\n" +
                "\"email\": \""+UserEmail+"\",\n" +
                "  \"first_name\": \""+ UserName +"\",\n" +
                "  \"last_name\": \"\",\n" +
                "  \"username\": \""+ UserPhone +"\",\n" +
                "  \"password\":\""+ userPassword +"\",\n" +
                "  \"billing_address\": {\n" +
                "    \"first_name\": \""+ UserName +"\",\n" +
                "    \"last_name\": \"\",\n" +
                "    \"company\": \"\",\n" +
                "    \"address_1\": \""+ UserAddress +"\",\n" +
                "    \"address_2\": \"\",\n" +
                "    \"city\": \"Mumbai\",\n" +
                "    \"state\": \"IN\",\n" +
                "    \"postcode\": \"12345\",\n" +
                "    \"country\": \"IN\",\n" +
                "    \"email\": \""+ UserEmail +"\",\n" +
                "    \"phone\": \" "+ UserPhone +"\"\n" +
                "  },\n" +
                "  \"shipping_address\": {\n" +
                "    \"first_name\": \"" + UserName + "\",\n" +
                "    \"last_name\": \"\",\n" +
                "    \"company\": \"\",\n" +
                "    \"address_1\": \""+ UserAddress +"\",\n" +
                "    \"address_2\": \"\",\n" +
                "    \"city\": \"Mumbai\",\n" +
                "    \"state\": \"IN\",\n" +
                "    \"postcode\": \"12345\",\n" +
                "    \"country\": \"IN\"\n" +
                "  }\n" +
                "}}");

        new StoreUserDataAsyncTask().execute();}

        else {
            Toast.makeText(this, "Please Fill all The Fields Correctly", Toast.LENGTH_LONG).show();
        }
    }

    private Boolean checkTheFields(String userName, String userAddress, String userEmail, String userPhone, String userPassword) {

        //return true if all the fields are not null
        return userName.length()>=0 && userAddress.length()>=0 && userEmail.length()>=0 && userPassword.length()>=0 && userPhone.length()>=0;

    }

    public class StoreUserDataAsyncTask extends AsyncTask<Void, Void, Void> {

        int responseCode;
        JSONArray jsonErrorArray;
        String errorMessage;
        String errorCodeString;

        @Override
        protected Void doInBackground(Void... params) {

          /*  Map<String, String> dataToSend = new HashMap<>();
            dataToSend.put("name", user.name);
            dataToSend.put("age", user.age + "");
            dataToSend.put("username", user.username);
            dataToSend.put("password", user.password); */

            BufferedReader reader = null;


            try {
                URL url = new URL("https://www.jersershor.com/wc-api/v3/customers?consumer_key=ck_638caaf46271a320075ecee01e89581f91644b98&consumer_secret=cs_0f5fe1845a21396a459fc3961a8255d15a62970b");
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


                if (responseCode >= 200 && responseCode < 400) {
                    // Create an InputStream in order to extract the response object
                   //InputStream is = conn.getInputStream();
                    StringBuilder sb = new StringBuilder();
                    reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        //Read till there is something available
                        sb.append(line + "\n");     //Reading and saving line by line - not all at once
                    }
                    line = sb.toString();           //Saving complete data received in string, you can do it differently

                    UserLocalStore userLocalStore = new UserLocalStore(getBaseContext());
                    userLocalStore.storeUserNumberPassword(UserPhone,userPassword);

                    //Just check to the values received in Logcat
                    Log.i("custom_check", "The values received in the store part are as follows:");
                    Log.i("custom_checkPassword", userLocalStore.getUserPassword());
                    Log.i("custom_check", Integer.toString(responseCode));
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
                    jsonErrorArray = jsonObject.getJSONArray("errors");
                    JSONObject jsonErrorObject = jsonErrorArray.getJSONObject(0);
                    errorMessage = jsonErrorObject.getString("message");
                    errorCodeString = jsonErrorObject.getString("code");

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
            progressDialog.dismiss();
            if (responseCode >= 200 && responseCode < 400) {
                Intent intent = new Intent(Register.this,LoginActivity.class);
                startActivity(intent);

            }
            else {
                if (Objects.equals(errorCodeString, "registration-error-username-exists")){
                    Toast.makeText(Register.this, "An account is already registered with that Phone Number. Please choose another" , Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(Register.this, errorMessage , Toast.LENGTH_LONG).show();

                }
                //InputStream is = conn.getErrorStream();

            }
            //userCallback.done(null);
            super.onPostExecute(aVoid);
        }
    }

}

