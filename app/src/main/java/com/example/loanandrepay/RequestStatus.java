package com.example.loanandrepay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RequestStatus extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;

    //Here the logout button is hidden, when the user is logged out
    @Override
    protected void onStart() {
        super.onStart();
//        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
////        String token = sharedPref.getString("token", "");
////
////
////
////
////        if (Objects.equals(token, "")) {
////            //// MenuItem logoutItem = menu.findItem(R.id.action_logout);
////            NavigationView navigationView = findViewById(R.id.navigation_view);
////            Menu menu = navigationView.getMenu();
////            MenuItem menuItem = menu.findItem(R.id.action_logout);
////            menuItem.setVisible(false);
////        }

        GetInstallmentStatus getInstallmentStatus = new GetInstallmentStatus();
        getInstallmentStatus.execute("http://192.168.1.171:4567/api/InstallmentRequestStatus");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_status);


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

        if (drawerLayout.isDrawerOpen((GravityCompat.START))){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
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
            Intent i = new Intent(RequestStatus.this, MainActivity.class);
            // set the new task and clear flags
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);


        }
        return false;
    }



    private class GetInstallmentStatus extends ReadHttpTask {
        @Override
        protected void onPostExecute(CharSequence jsonString) {

            //Gets the data from database and show all info into list by using loop
            final List<InstallmentRequest> request = new ArrayList<>();

            try {

                JSONArray array = new JSONArray(jsonString.toString());
                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);

                    //token = obj.getString("UserId");
                    String company = obj.getString("Company");
                    int status = obj.getInt("Status");


                    InstallmentRequest installmentRequest = new InstallmentRequest(company, status);

                    request.add(installmentRequest);
                }
                ListView listView = findViewById(R.id.showPendingRequestList);
                ArrayAdapter<InstallmentRequest> adapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1, request);
                listView.setAdapter(adapter);


            } catch (JSONException ex)
            {
                //messageTextView.setText(ex.getMessage());
                Log.e("InstallmentRequest", ex.getMessage());
            }


        }
    }

}
