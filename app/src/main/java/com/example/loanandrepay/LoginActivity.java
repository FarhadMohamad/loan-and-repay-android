package com.example.loanandrepay;

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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.example.loanandrepay.company.RequestListActivity;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Objects;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


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


    public void onClickRegisterbtn(View view) {
        Intent goToRegisterActivity = new Intent(LoginActivity.this, UserTypeRegistration.class);
        startActivity(goToRegisterActivity);
    }

    public void onClickLoginBtn(View view) {
        new UserLogin().execute();
    }

    public  class UserLogin extends AsyncTask<String, Void, Void> {

        HttpConnection httpConnection = new HttpConnection();
        EditText userId = (EditText) findViewById(R.id.txtLoginUsername);
        EditText userPassword = (EditText) findViewById(R.id.txtLoginPassword);


        @Override
        protected Void doInBackground(String... params) {
            URL url;
            HttpURLConnection urlConnection = null;

            try {
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("username", userId.getText());
                postDataParams.put("password", userPassword.getText());

                //url = new URL("http://25.95.117.73:7549/api/Account/Login");
                //url = new URL("http://25.61.100.41:6429/api/Account/Login");
               url = new URL("http://192.168.1.171:4567/api/Account/Login");


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

                    String responseString = httpConnection.readStream(urlConnection.getInputStream());
                    JSONObject obj = new JSONObject(responseString);
                    String kept = obj.get("access_token").toString();
                    String getUserRole = obj.get("roles").toString().trim();

                    String s1= getUserRole;


                   // "roles": "["Company"]",
                    //Saving Token
                    SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("token", kept);
                    editor.apply();

                    //Saving logged in user for showing it later on in Profile Settings
                    String saveUser = userId.getText().toString();
                    editor.putString("savedUser", saveUser);
                    editor.apply();

                    if (getUserRole.contains("Company"))
                    {
                        //Go to Main after user is logged in
                        Intent goToMain = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(goToMain);
                        finish();
                    }
                    if(getUserRole.contains("Client"))
                    {
                        //Go to ..... after user is logged in
                        Intent goToMain = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(goToMain);
                        finish();
                    }




                } else {
                    //Showing message that you have entered your credential incorrect
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Login failed, please check your username/password",
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




