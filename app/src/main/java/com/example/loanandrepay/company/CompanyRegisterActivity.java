package com.example.loanandrepay.company;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.loanandrepay.HttpConnection.HttpConnection;
import com.example.loanandrepay.LoginActivity;
import com.example.loanandrepay.R;
import com.example.loanandrepay.client.InstallmentRequestActivity;
import com.example.loanandrepay.client.ProfileActivity;
import com.example.loanandrepay.client.RequestStatusActivity;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

public class CompanyRegisterActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    EditText companyName;
    EditText cvr;
    EditText email;
    EditText password;
    EditText confirmPassword;
    EditText phoneNumber;
    EditText streetName;
    EditText houseNumber;
    EditText city;
    EditText postCode;


    //Here the logout button is hidden, when the user is logged out
    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String token = sharedPref.getString("token", "");

        if (Objects.equals(token, "")) {
            //// MenuItem logoutItem = menu.findItem(R.id.action_logout);
            NavigationView navigationView = findViewById(R.id.navigation_view);
            Menu menu = navigationView.getMenu();
            MenuItem menuItem = menu.findItem(R.id.action_logout);
            menuItem.setVisible(false);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_register);
        this.setTitle("Register");

        //This is for overlaying the navigation header on the screen
        Toolbar toolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (getSupportActionBar() != null) {
            //This will enable the burger menu
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayUseLogoEnabled(true);
        }

        //This will do the job for selecting a specific item in the burger menu
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        companyName = findViewById(R.id.txtCompanyName);
        companyName.addTextChangedListener(RegisterCompanyBtnTextWatcher);
        cvr = findViewById(R.id.txtCVR);
        cvr.addTextChangedListener(RegisterCompanyBtnTextWatcher);
        email = findViewById(R.id.txtEmail);
        email.addTextChangedListener(RegisterCompanyBtnTextWatcher);
        password = findViewById(R.id.txtPassword);
        password.addTextChangedListener(RegisterCompanyBtnTextWatcher);
        confirmPassword = findViewById(R.id.txtConfirmPassword);
        confirmPassword.addTextChangedListener(RegisterCompanyBtnTextWatcher);
        phoneNumber = findViewById(R.id.txtPhone);
        phoneNumber.addTextChangedListener(RegisterCompanyBtnTextWatcher);
        streetName = findViewById(R.id.txtStreetName);
        streetName.addTextChangedListener(RegisterCompanyBtnTextWatcher);
        houseNumber = findViewById(R.id.txtHouseNumber);
        houseNumber.addTextChangedListener(RegisterCompanyBtnTextWatcher);
        city = findViewById(R.id.txtCityName);
        city.addTextChangedListener(RegisterCompanyBtnTextWatcher);
        postCode = findViewById(R.id.txtPostCode);
        postCode.addTextChangedListener(RegisterCompanyBtnTextWatcher);
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


    //Whenever you click on a particular item in the burger menu, it will
    //execute a function
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.nav_profile:
                item.setChecked(false);
                Intent a = new Intent(CompanyRegisterActivity.this, ProfileActivity.class);
                startActivity(a);
                break;
            case R.id.nav_requestStatus:
                item.setChecked(true);
                Intent b = new Intent(CompanyRegisterActivity.this, RequestStatusActivity.class);
                startActivity(b);
                break;
            case R.id.nav_newRequest:
                item.setChecked(false);
                Intent c = new Intent(CompanyRegisterActivity.this, InstallmentRequestActivity.class);
                startActivity(c);
                break;

            case R.id.action_logout:
                SharedPreferences preferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                finish();
                Intent goToLoginActivity = new Intent(CompanyRegisterActivity.this, LoginActivity.class);
                // set the new task and clear flags
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(goToLoginActivity);
                break;
        }

        return false;
    }

    public TextWatcher RegisterCompanyBtnTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            checkRequiredFields();

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private void checkRequiredFields() {
        Button btnRegister = findViewById(R.id.btnRegister);


        if (!companyName.getText().toString().isEmpty() && !cvr.getText().toString().isEmpty() && !email.getText().toString().isEmpty() && !password.getText().toString().isEmpty() && !confirmPassword.getText().toString().isEmpty() &&
                !phoneNumber.getText().toString().isEmpty() && !streetName.getText().toString().isEmpty() && !houseNumber.getText().toString().isEmpty() && !city.getText().toString().isEmpty() &&
                !postCode.getText().toString().isEmpty()) {
            btnRegister.setEnabled(true);
        } else {
            btnRegister.setEnabled(false);
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
        EditText enterPhone = (EditText) findViewById(R.id.txtPhone);
        EditText enterStreetName = (EditText) findViewById(R.id.txtStreetName);
        EditText enterHouseNumber = (EditText) findViewById(R.id.txtHouseNumber);
        EditText enterCityName = (EditText) findViewById(R.id.txtCityName);
        EditText enterPostCode = (EditText) findViewById(R.id.txtPostCode);


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
                postDataParams.put("phone", enterPhone.getText());
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
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "you are registered successfully",
                                    Toast.LENGTH_LONG).show();
                        }
                    });

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

}


