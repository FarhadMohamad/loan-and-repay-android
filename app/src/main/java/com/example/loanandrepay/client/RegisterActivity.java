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
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.loanandrepay.HttpConnection.HttpConnection;
import com.example.loanandrepay.LoginActivity;
import com.example.loanandrepay.R;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
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
        setContentView(R.layout.activity_register);

        //This is for overlaying the navigation header on the screen
        Toolbar toolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(toolbar);


        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (getSupportActionBar() != null) {
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
//
//        if (id == R.id.action_logout) {
//            SharedPreferences preferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
//            SharedPreferences.Editor editor = preferences.edit();
//            editor.clear();
//            editor.apply();
//            finish();
//            Intent goToLoginActivity = new Intent(RegisterActivity.this, LoginActivity.class);
//            // set the new task and clear flags
//            goToLoginActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(goToLoginActivity);
//        }
       return false;
    }

    public void onClickSignupBtn(View view) {
new Register().execute();
    }

    public class Register extends AsyncTask<String, Void, Void> {
        private EditText editText;

        HttpConnection httpConnection = new HttpConnection();
        EditText enterFirstName = (EditText) findViewById(R.id.txtFirstName);
        EditText enterLastName = (EditText) findViewById(R.id.txtLastName);
        EditText enterPhone = (EditText) findViewById(R.id.txtPhone);
        EditText enterEmail = (EditText) findViewById(R.id.txtEmail);
        EditText enterPassword = (EditText) findViewById(R.id.txtSignupPassword);
        EditText confirmPassword = (EditText) findViewById(R.id.txtConfirmPassword);

        @Override
        protected Void doInBackground(String... params) {
            URL url;
            HttpURLConnection urlConnection = null;

            try {
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("FirstName", enterFirstName.getText());
                postDataParams.put("LastName", enterLastName.getText());
                postDataParams.put("PhoneNumber", enterPhone.getText());
                postDataParams.put("Email", enterEmail.getText());
                postDataParams.put("Password", enterPassword.getText());
                postDataParams.put("ConfirmPassword", confirmPassword.getText());


                //url = new URL("http://25.95.117.73:7549/api/Account/Login");
                url = new URL("http://192.168.1.171:4567/api/Account/RegisterClient");


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

                    Intent goToAuthentication = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(goToAuthentication);
                    finish();


                } if (responseCode == HttpURLConnection.HTTP_NOT_FOUND)
                {
                    runOnUiThread(new Runnable(){
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Sign-up failed. Please try again",
                                    Toast.LENGTH_LONG).show();
                        }
                    });


                }
                else if (responseCode == HttpURLConnection.HTTP_BAD_REQUEST) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Sign-up failed. Email or password incorrect",
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

        protected void onPostExecute(Void result){
            super.onPostExecute(result);

        }

}
}
