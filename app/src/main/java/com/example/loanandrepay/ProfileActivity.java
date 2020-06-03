package com.example.loanandrepay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        SharedPreferences sharedPref = getSharedPreferences("userName", Context.MODE_PRIVATE);
        String getUserName = sharedPref.getString("userName", "");

        TextView loggedInUser = findViewById(R.id.txtLoggedinAs);
        loggedInUser.setText(getUserName);

    }

    public void onClickLoginBtn(View view) {

        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.clear();
        editor.apply();

        Intent goToMain = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(goToMain);
        finish();
    }
}
