package com.example.loanandrepay.company;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.loanandrepay.HttpConnection;
import com.example.loanandrepay.LoginActivity;
import com.example.loanandrepay.R;
import com.example.loanandrepay.RegisterActivity;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class CompanyRegisterActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_register);


        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //This will do the job for selecting a specific item in the burger menu
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    //This is used whenever you click on the burger menu the menu bar will open
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //When a back button is pressed, the drawer will be closed instead of going back to another activity
    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen((GravityCompat.START))) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void onClickSignupBtn(View view) {

        new Register().execute();
    }

    public class Register extends AsyncTask<String, Void, Void> {

        HttpConnection httpConnection = new HttpConnection();
        EditText enterCompanyName = (EditText) findViewById(R.id.txtCompanyName);
        EditText enterCVR = (EditText) findViewById(R.id.txtCVR);
        EditText enterEmail = (EditText) findViewById(R.id.txtEmail);
        EditText enterPassword = (EditText) findViewById(R.id.txtPassword);
        EditText enterConfirmPassword = (EditText) findViewById(R.id.txtConfirmPassword);
        EditText enterWebsite = (EditText) findViewById(R.id.txtWebsite);
        EditText enterPhone = (EditText) findViewById(R.id.txtPhone);

        EditText enterContactPerson = (EditText) findViewById(R.id.txtContactPerson);

        EditText enterStreetName = (EditText) findViewById(R.id.StreetName);

        EditText enterHouseNumber = (EditText) findViewById(R.id.HouseNumber);

        EditText enterCityName = (EditText) findViewById(R.id.CityName);
        EditText enterPostCode = (EditText) findViewById(R.id.PostCode);


        @Override
        protected Void doInBackground(String... params) {
            URL url;
            HttpURLConnection urlConnection = null;

            try {
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("Companyname", enterCompanyName.getText());
                postDataParams.put("CVR", enterCVR.getText());
                postDataParams.put("Email", enterEmail.getText());
                postDataParams.put("Password", enterPassword.getText());
                postDataParams.put("ConfirmPassword", enterConfirmPassword.getText());
                postDataParams.put("Website", enterWebsite.getText());

                postDataParams.put("phone", enterPhone.getText());

                postDataParams.put("ContactPerson", enterContactPerson.getText());

                postDataParams.put("StreetName", enterStreetName.getText());

                postDataParams.put("HouseNumber", enterHouseNumber.getText());
                postDataParams.put("CityName", enterCityName.getText());
                postDataParams.put("PostCode", enterPostCode.getText());

                //url = new URL("http://25.95.117.73:7549/api/Account/Login");
                url = new URL("http://192.168.1.171:4567/api/Account/RegisterCompany");


                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoInput(true);

                OutputStream os = urlConnection.getOutputStream();

                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(httpConnection.getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int responseCode = urlConnection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {

                    Intent goToAuthentication = new Intent(CompanyRegisterActivity.this, LoginActivity.class);
                    startActivity(goToAuthentication);
                    finish();


                } else if (responseCode == HttpURLConnection.HTTP_BAD_REQUEST) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Sign-up failed. Please try again",
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null)
                    urlConnection.disconnect();
            }
            return null;
        }

        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

        }

    }

    //Whenever you click on a particular item in the burger menu, it will
    //execute a function
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            SharedPreferences preferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.apply();
            finish();
            Intent i = new Intent(CompanyRegisterActivity.this, LoginActivity.class);
            // set the new task and clear flags
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);


        }
        return false;
    }
}


