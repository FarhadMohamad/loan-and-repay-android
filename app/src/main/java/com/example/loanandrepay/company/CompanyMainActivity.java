package com.example.loanandrepay.company;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.loanandrepay.LoginActivity;
import com.example.loanandrepay.R;
import com.example.loanandrepay.client.InstallmentRequestActivity;
import com.example.loanandrepay.client.MainActivity;
import com.example.loanandrepay.client.ProfileActivity;
import com.example.loanandrepay.client.RequestStatusActivity;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class CompanyMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
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
        setContentView(R.layout.activity_company_main);
        this.setTitle("Main");

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

        switch (item.getItemId()) {

            case R.id.navigation_profile:
                item.setChecked(false);
                Intent a = new Intent(CompanyMainActivity.this, CompanyProfileActivity.class);
                startActivity(a);
                break;
            case R.id.navigation_requestList:
                item.setChecked(true);
                Intent b = new Intent(CompanyMainActivity.this, RequestListActivity.class);
                startActivity(b);
                break;

            case R.id.action_logout:
                SharedPreferences preferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                finish();
                Intent goToLoginActivity = new Intent(CompanyMainActivity.this, LoginActivity.class);
                // set the new task and clear flags
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(goToLoginActivity);
                break;
        }

        return false;
    }


    public void onClickLoginBtn(View view) {
        //Go to login activity
        Intent gotoLoginActivity = new Intent(CompanyMainActivity.this, LoginActivity.class);
        startActivity(gotoLoginActivity);
        finish();
    }

    public void onClickRequestList(View view) {


        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String token = sharedPref.getString("token", "");

        if (Objects.equals(token, "")) {

            Intent goToLogin = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(goToLogin);
        } else {
            //Go to goToRequestActivity
            Intent goToRequestActivity = new Intent(CompanyMainActivity.this, RequestListActivity.class);
            startActivity(goToRequestActivity);
            finish();

        }
    }

    public void onClickProfile(View view) {

        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String token = sharedPref.getString("token", "");

        if (Objects.equals(token, "")) {

            Intent gotoLoginActivity = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(gotoLoginActivity);
        } else {
            //Go to goToRequestActivity
            Intent goToProfileActivity = new Intent(CompanyMainActivity.this, CompanyProfileActivity.class);
            startActivity(goToProfileActivity);
            finish();

        }

    }

    public void onClickLogOutBtn(View view) {
        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.clear();
        editor.apply();
        finish();
        Intent goToLoginActivity = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(goToLoginActivity);
    }
}
