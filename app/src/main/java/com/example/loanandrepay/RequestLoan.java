package com.example.loanandrepay;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class RequestLoan extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_loan);
    }

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
}
