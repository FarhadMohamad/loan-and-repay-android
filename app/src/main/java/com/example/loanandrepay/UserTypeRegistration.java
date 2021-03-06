package com.example.loanandrepay;

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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.loanandrepay.client.InstallmentRequestActivity;
import com.example.loanandrepay.client.MainActivity;
import com.example.loanandrepay.client.ProfileActivity;
import com.example.loanandrepay.client.RegisterActivity;
import com.example.loanandrepay.client.RequestStatusActivity;
import com.example.loanandrepay.company.CompanyRegisterActivity;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class UserTypeRegistration extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
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
        setContentView(R.layout.activity_user_type_registration);
        this.setTitle("Registration");

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

        switch (item.getItemId( )) {

            case R.id.nav_profile:
                item.setChecked(false);
                Intent a = new Intent(UserTypeRegistration.this, ProfileActivity.class);
                startActivity(a);
                break;
            case R.id.nav_requestStatus:
                item.setChecked(true);
                Intent b = new Intent(UserTypeRegistration.this, RequestStatusActivity.class);
                startActivity(b);
                break;
            case R.id.nav_newRequest:
                item.setChecked(false);
                Intent c = new Intent(UserTypeRegistration.this, InstallmentRequestActivity.class);
                startActivity(c);
                break;

            case R.id.action_logout:
                SharedPreferences preferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                finish();
                Intent goToLoginActivity = new Intent(UserTypeRegistration.this, LoginActivity.class);
                // set the new task and clear flags
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(goToLoginActivity);
                break;
        }

        return false;
    }



    public void OnClickCompany(View view) {
        Intent goToRegisterActivity = new Intent(UserTypeRegistration.this, CompanyRegisterActivity.class);
        startActivity(goToRegisterActivity);
    }

    public void onClickPrivate(View view) {
        Intent goToRegisterActivity = new Intent(UserTypeRegistration.this, RegisterActivity.class);
        startActivity(goToRegisterActivity);
    }
}
