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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loanandrepay.LoginActivity;
import com.example.loanandrepay.client.MainActivity;
import com.example.loanandrepay.R;
import com.example.loanandrepay.client.ProfileActivity;
import com.google.android.material.navigation.NavigationView;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

public class RequestListDetailsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Request request;
    private TextView Company;
    private TextView FirstName;
    private TextView LastName;
    private TextView Email;
    private TextView Age;
    private TextView Phone;
    private TextView StreetName;
    private TextView HouseNumber;
    private TextView CityName;
    private TextView PostCode;
    private TextView Amount;
    private TextView PayWithIn;
    private TextView MonthlyPayment;
    private TextView Status;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;

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
        setContentView(R.layout.activity_request_list_details);
        this.setTitle("Detail");


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

        Intent intent = getIntent();
        request = (Request) intent.getSerializableExtra("Request");


        if (request.getStatus() == 1) {
            Button btn = findViewById(R.id.btnAccept);
            btn.setEnabled(false);
        }

        if (request.getStatus() == 2) {
            Button btn = findViewById(R.id.btnReject);
            btn.setEnabled(false);
        }

        Company = findViewById(R.id.txtCompany);
        Company.setText("Company: " + request.getCompany());

        FirstName = findViewById(R.id.txtFirstName);
        FirstName.setText("First Name: " + request.getFirstName());

        LastName = findViewById(R.id.txtLastName);
        LastName.setText("Last Name: " + request.getLastName());

        Email = findViewById(R.id.txtEmail);
        Email.setText("Email: " + request.getEmail());

        Age = findViewById(R.id.txtAge);
        Age.setText("Age: " + request.getAge());

        Phone = findViewById(R.id.txtPhone);
        Phone.setText("Phone: " + request.getPhone());

        StreetName = findViewById(R.id.txtStreetName);
        StreetName.setText("Street Name: " + request.getStreetName());

        HouseNumber = findViewById(R.id.txtHouseNumber);
        HouseNumber.setText("House number: " + request.getHouseNumber());

        CityName = findViewById(R.id.txtCityName);
        CityName.setText("City name: " + request.getCityName());

        PostCode = findViewById(R.id.txtPostCode);
        PostCode.setText("Post code: " + request.getPostCode());

        Amount = findViewById(R.id.txtAmount);
        Amount.setText("Amount: " + request.getAmount());

        PayWithIn = findViewById(R.id.txtPayWithIn);
        PayWithIn.setText("Pay within: " + request.getPayWithIn());

        MonthlyPayment = findViewById(R.id.txtMonthyPayment);
        MonthlyPayment.setText("Monthly payment: " + request.getMonthlyPayment());

//        Status = findViewById(R.id.txtStatus);
//        Status.setText("Status: " + request.getStatus());

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

            case R.id.navigation_profile:
                item.setChecked(false);
                Intent a = new Intent(RequestListDetailsActivity.this, ProfileActivity.class);
                startActivity(a);
                break;
            case R.id.navigation_requestList:
                item.setChecked(true);
                Intent b = new Intent(RequestListDetailsActivity.this, RequestListActivity.class);
                startActivity(b);
                break;

            case R.id.action_logout:
                SharedPreferences preferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                finish();
                Intent goToLoginActivity = new Intent(RequestListDetailsActivity.this, LoginActivity.class);
                // set the new task and clear flags
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(goToLoginActivity);
                break;
        }

        return false;
    }

    public void btnAcceptRequest(View view) {

        new ChangeRequestStatus(1).execute();

    }


    public void btnRejectRequest(View view) {


        new ChangeRequestStatus(2).execute();
    }


    public class ChangeRequestStatus extends AsyncTask<String, Void, Void> {

        private int Requeststatus;


        public ChangeRequestStatus(int getRequestStatus) {
            this.Requeststatus = getRequestStatus;

        }

        @Override
        protected Void doInBackground(String... params) {

            URL url;
            HttpURLConnection urlConnection = null;


            try {

                SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);

                String token = sharedPref.getString("token", "");

                url = new URL("http://192.168.1.171:4567/api/InstallmentRequestStatus?id=" + request.getId() + "&status=" + Requeststatus);


                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setRequestProperty("Authorization", "Bearer " + token);

                urlConnection.setRequestMethod("POST");
                urlConnection.setDoInput(true);

                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));


                writer.flush();
                writer.close();
                os.close();


                int responseCode = urlConnection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_ACCEPTED) {


                    Intent intent = new Intent(RequestListDetailsActivity.this, CompanyMainActivity.class);

                    startActivity(intent);

                    finish();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "status changed successfully",
                                    Toast.LENGTH_LONG).show();
                        }

                    });

                } else if (responseCode == HttpURLConnection.HTTP_NOT_FOUND) {

                    Intent intentLogin = new Intent(RequestListDetailsActivity.this, CompanyMainActivity.class);
                    startActivity(intentLogin);
                    finish();

                    Toast.makeText(getApplicationContext(), "request was failed, please login.",
                            Toast.LENGTH_LONG).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null)
                    urlConnection.disconnect();
            }
            return null;
        }
    }

}

