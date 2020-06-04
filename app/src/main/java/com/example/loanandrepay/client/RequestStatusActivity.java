package com.example.loanandrepay.client;

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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.loanandrepay.HttpConnection.ReadHttpTask;
import com.example.loanandrepay.LoginActivity;
import com.example.loanandrepay.R;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RequestStatusActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;

    //Here the logout button is hidden, when the user is logged out
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

        GetPendingRequest getPendingRequest = new GetPendingRequest();
        getPendingRequest.execute("http://192.168.1.171:4567/api/StatusPending?email=" + showLogUser);

        GetAcceptedRequest getAcceptedRequest = new GetAcceptedRequest();
        getAcceptedRequest.execute("http://192.168.1.171:4567/api/StatusAccepted?email=" + showLogUser);

        GetRejectedRequest getRejectedRequest = new GetRejectedRequest();

        getRejectedRequest.execute("http://192.168.1.171:4567/api/StatusRejected?email=" + showLogUser);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_status);

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
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            SharedPreferences preferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.apply();
            finish();
            Intent goToLoginActivity = new Intent(RequestStatusActivity.this, LoginActivity.class);
            // set the new task and clear flags
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(goToLoginActivity);


        }
        return false;
    }


    private class GetPendingRequest extends ReadHttpTask {
        @Override
        protected void onPostExecute(CharSequence jsonString) {

            //Gets the data from database and show all info into list by using loop
            final List<StatusPending> request = new ArrayList<>();

            try {

                JSONArray array = new JSONArray(jsonString.toString());
                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);

                    //token = obj.getString("UserId");
                    String company = obj.getString("Company");
                    String status = obj.getString("Status");


                    StatusPending statusPending = new StatusPending(company, status);

                    request.add(statusPending);
                }
                ListView listView = findViewById(R.id.showPendingRequestList);
                ArrayAdapter<StatusPending> adapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1, request);
                if (adapter.getCount() != 0) {
                    TextView textView = findViewById(R.id.txtPendingNotFound);
                    textView.setVisibility(View.INVISIBLE);
                }
                listView.setAdapter(adapter);


            } catch (JSONException ex) {
                //messageTextView.setText(ex.getMessage());
                Log.e("InstallmentRequest", ex.getMessage());
            }


        }
    }

    private class GetAcceptedRequest extends ReadHttpTask {
        @Override
        protected void onPostExecute(CharSequence jsonString) {

            //Gets the data from database and show all info into list by using loop
            final List<StatusAccepted> request = new ArrayList<>();

            try {

                JSONArray array = new JSONArray(jsonString.toString());
                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);

                    //token = obj.getString("UserId");
                    String company = obj.getString("Company");
                    String status = obj.getString("Status");


                    StatusAccepted statusAccepted = new StatusAccepted(company, status);

                    request.add(statusAccepted);
                }
                ListView listView = findViewById(R.id.showAcceptedRequests);
                ArrayAdapter<StatusAccepted> adapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1, request);
                if (adapter.getCount() != 0) {
                    TextView textView = findViewById(R.id.txtAcceptedNotFound);
                    textView.setVisibility(View.INVISIBLE);
                }
                listView.setAdapter(adapter);


            } catch (JSONException ex) {
                //messageTextView.setText(ex.getMessage());
                Log.e("InstallmentRequest", ex.getMessage());
            }


        }
    }

    private class GetRejectedRequest extends ReadHttpTask {
        @Override
        protected void onPostExecute(CharSequence jsonString) {

            //Gets the data from database and show all info into list by using loop
            final List<StatusRejected> request = new ArrayList<>();

            try {

                JSONArray array = new JSONArray(jsonString.toString());
                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);

                    //token = obj.getString("UserId");
                    String company = obj.getString("Company");
                    String status = obj.getString("Status");


                    StatusRejected statusRejected = new StatusRejected(company, status);

                    request.add(statusRejected);
                }

                ListView listView = findViewById(R.id.showRejectedRequests);

                ArrayAdapter<StatusRejected> adapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1, request);
                if (adapter.getCount() != 0) {
                    TextView textView = findViewById(R.id.txtRejectedNotFound);
                    textView.setVisibility(View.INVISIBLE);
                }
                listView.setAdapter(adapter);


            } catch (JSONException ex) {
                //messageTextView.setText(ex.getMessage());
                Log.e("InstallmentRequest", ex.getMessage());
            }


        }
    }

}
