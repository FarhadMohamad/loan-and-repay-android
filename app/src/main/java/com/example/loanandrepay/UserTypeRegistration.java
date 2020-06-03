package com.example.loanandrepay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.loanandrepay.client.RegisterActivity;
import com.example.loanandrepay.company.CompanyRegisterActivity;

public class UserTypeRegistration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_type_registration);
    }

    public void OnClickCompanyRegister(View view) {
        Intent goToRegisterActivity = new Intent(UserTypeRegistration.this, CompanyRegisterActivity.class);
        startActivity(goToRegisterActivity);
    }

    public void onClickUserRegister(View view) {
        Intent goToRegisterActivity = new Intent(UserTypeRegistration.this, RegisterActivity.class);
        startActivity(goToRegisterActivity);
    }
}
