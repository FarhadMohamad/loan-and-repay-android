package com.example.loanandrepay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class UserTypeRegistration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_type_registration);
    }

    public void OnCliekCompanyRegister(View view) {

    }

    public void onClickUserRegister(View view) {
        Intent goToRegisterActivity = new Intent(UserTypeRegistration.this, RegisterActivity.class);
        startActivity(goToRegisterActivity);
    }
}
