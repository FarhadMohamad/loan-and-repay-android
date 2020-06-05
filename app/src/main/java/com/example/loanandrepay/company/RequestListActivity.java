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
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.loanandrepay.LoginActivity;
import com.example.loanandrepay.client.MainActivity;
import com.example.loanandrepay.R;
import com.example.loanandrepay.HttpConnection.ReadHttpTask;
import com.example.loanandrepay.client.ProfileActivity;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RequestListActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;

    // Listview Adapter
   private ArrayAdapter<Request> adapter;
    private ListView listView;
    private EditText inputSearch;

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String showLogUser = sharedPref.getString("userName", "");
        String token = sharedPref.getString("token", "");

        if (Objects.equals(token, "")) {
            //// MenuItem logoutItem = menu.findItem(R.id.action_logout);
            NavigationView navigationView = findViewById(R.id.navigation_view);
            Menu menu = navigationView.getMenu();
            MenuItem menuItem = menu.findItem(R.id.action_logout);
            menuItem.setVisible(false);
        }

        GetRequestList getRequestList = new GetRequestList();

        getRequestList.execute("http://192.168.1.171:4567/api/GetRequestList?email="+ showLogUser);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_list);
        this.setTitle("Request List");


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

       EditText inputSearch = (EditText) findViewById(R.id.search_view);

        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                RequestListActivity.this.adapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });

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

        startActivity( new Intent(this, CompanyMainActivity.class) );
    }


    //Whenever you click on a particular item in the burger menu, it will
    //execute a function
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.navigation_profile:
                item.setChecked(false);
                Intent a = new Intent(RequestListActivity.this, ProfileActivity.class);
                startActivity(a);
                break;
            case R.id.navigation_requestList:
                item.setChecked(true);
                Intent b = new Intent(RequestListActivity.this, RequestListActivity.class);
                startActivity(b);
                break;

            case R.id.action_logout:
                SharedPreferences preferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                finish();
                Intent goToLoginActivity = new Intent(RequestListActivity.this, LoginActivity.class);
                // set the new task and clear flags
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(goToLoginActivity);
                break;
        }

        return false;
    }



    public void btnSearch(View view) {

        EditText editText = (EditText) findViewById(R.id.search_view);
        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String showLogUser = sharedPref.getString("savedUser", "");
        GetRequestList getRequestList = new GetRequestList();
        try {
            getRequestList.execute("http://192.168.1.171:4567/api/SearchRequestByName?name="+editText.getText()+"&email="+ showLogUser);
        } catch (Exception ex) {
            listView.setAdapter(null);
            Toast.makeText(getApplicationContext(), "Sorry, ticket not found, But you can request for the ticket and you will get a notification, whenever the ticket is available", Toast.LENGTH_LONG).show();
            Exception dd = ex;
        }
    }

    private class GetRequestList extends ReadHttpTask {
        @Override
        protected void onPostExecute(CharSequence jsonString) {

            //Gets the data from database and show all info into list by using loop
            final List<Request> requestList = new ArrayList<>();

            try {

                JSONArray array = new JSONArray(jsonString.toString());
                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);

                    //token = obj.getString("UserId");
                    int id = obj.getInt("Id");
                    String company = obj.getString("Company");
                    String firstName = obj.getString("FirstName");
                    String lastName = obj.getString("LastName");
                    String email = obj.getString("Email");
                    int age = obj.getInt("Age");
                    String phone = obj.getString("Phone");
                    String streetName = obj.getString("StreetName");
                    String houseNumber = obj.getString("HouseNumber");
                    String cityName = obj.getString("CityName");
                    int postCode = obj.getInt("PostCode");
                    double amount = obj.getDouble("Amount");
                    String payWithIn = obj.getString("PayWithIn");
                    double monthlyPayment = obj.getDouble("MonthlyPayment");
                    int status = obj.getInt("Status");



                    Request request = new Request (id, company, firstName, lastName, email, age, phone, streetName, houseNumber, cityName, postCode, amount, payWithIn, monthlyPayment,status);

                    requestList.add(request);
                }
                listView = findViewById(R.id.showRequestList);
               adapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1, requestList);
                listView.setAdapter(adapter);



                listView.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {

                    Intent goToRequestListDetail = new Intent(getBaseContext(), RequestListDetailsActivity.class);
                    Request requestdetail = (Request) parent.getItemAtPosition(position);
                    goToRequestListDetail.putExtra("Request", requestdetail);

                    startActivity(goToRequestListDetail);

                });

            } catch (JSONException ex) {
                Log.e("Tickets", ex.getMessage());
            }


        }
    }
}
