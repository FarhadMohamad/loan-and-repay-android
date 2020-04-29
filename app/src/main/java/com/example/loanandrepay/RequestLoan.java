package com.example.loanandrepay;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

public class RequestLoan extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private Spinner spinner1;
    private EditText loanAmount;
    public RadioButton radiosixMonths;
    public RadioButton radiontvelveMonths;
    public Button RequestLoanBtn;
    public EditText txtFirstName;
    public EditText txtLastName;
    public EditText txtEmail;
    public EditText txtAge;
    public EditText txtPhone;
    public EditText StreetName;
    public EditText HouseNumber;
    public EditText CityName;
    public EditText PostCode;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_request_loan);
        addListenerOnButton();
        addListenerOnSpinnerItemSelection();

        loanAmount = findViewById(R.id.txtAmount);
        radiosixMonths = findViewById(R.id.radiobtnSixMonths);
        radiontvelveMonths = findViewById(R.id.radiobtntvelveMonths);
        loanAmount.addTextChangedListener(radioButtonTextWatcher);
        ///////////////////////////////////////
        txtFirstName= findViewById(R.id.txtFirstName);
        txtFirstName.addTextChangedListener(RequestLoanBtnTextWatcher);
        txtLastName= findViewById(R.id.txtLastName);
        txtLastName.addTextChangedListener(RequestLoanBtnTextWatcher);
        txtEmail= findViewById(R.id.txtEmail);
        txtEmail.addTextChangedListener(RequestLoanBtnTextWatcher);
        txtAge= findViewById(R.id.txtAge);
        txtAge.addTextChangedListener(RequestLoanBtnTextWatcher);
        txtPhone= findViewById(R.id.txtPhone);
        txtPhone.addTextChangedListener(RequestLoanBtnTextWatcher);
        StreetName= findViewById(R.id.StreetName);
        StreetName.addTextChangedListener(RequestLoanBtnTextWatcher);
        HouseNumber= findViewById(R.id.HouseNumber);
        HouseNumber.addTextChangedListener(RequestLoanBtnTextWatcher);
        CityName= findViewById(R.id.CityName);
        CityName.addTextChangedListener(RequestLoanBtnTextWatcher);
        PostCode= findViewById(R.id.PostCode);
        PostCode.addTextChangedListener(RequestLoanBtnTextWatcher);
        RequestLoanBtn = findViewById(R.id.RequestLoanBtn);
        ///////////////////////////////////////
    }
   //enable radion button and request loan button after all fields are filled
private TextWatcher radioButtonTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String usernameInput = loanAmount.getText().toString();
            radiosixMonths.setEnabled(!usernameInput.isEmpty());
            radiontvelveMonths.setEnabled(!usernameInput.isEmpty());


        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private TextWatcher RequestLoanBtnTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String txtFirstNameFiled = txtFirstName.getText().toString();
            String txtLastNameFiled = txtLastName.getText().toString();
            String txtEmailFiled = txtEmail.getText().toString();
            String txtAgeFiled = txtAge.getText().toString();
            String txtPhoneFiled = txtPhone.getText().toString();
            String StreetNameFiled = StreetName.getText().toString();
            String HouseNumberFiled = HouseNumber.getText().toString();
            String CityNameFiled = CityName.getText().toString();
            String PostCodeFiled = PostCode.getText().toString();

            RequestLoanBtn.setEnabled(!txtFirstNameFiled.isEmpty());
            RequestLoanBtn.setEnabled(!txtLastNameFiled.isEmpty());
            RequestLoanBtn.setEnabled(!txtEmailFiled.isEmpty());
            RequestLoanBtn.setEnabled(!txtAgeFiled.isEmpty());
            RequestLoanBtn.setEnabled(!txtPhoneFiled.isEmpty());
            RequestLoanBtn.setEnabled(!StreetNameFiled.isEmpty());
            RequestLoanBtn.setEnabled(!HouseNumberFiled.isEmpty());
            RequestLoanBtn.setEnabled(!CityNameFiled.isEmpty());
            RequestLoanBtn.setEnabled(!PostCodeFiled.isEmpty());


        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    public void addListenerOnSpinnerItemSelection() {
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner1.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) new CustomOnItemSelectedListener());
    }

    // get the selected dropdown list value
    public void addListenerOnButton() {

        spinner1 = (Spinner) findViewById(R.id.spinner1);

    }

    public void onClickRequestLoan(View view) {


        Intent goToRegisterActivity = new Intent(this, MainActivity.class);
        startActivity(goToRegisterActivity);

    }


    public class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            Toast.makeText(parent.getContext(),
                    "OnItemSelectedListener : " + parent.getItemAtPosition(pos).toString(),
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
        }
    }
    //Here the logout button is hidden, when the user is logged out
//    @Override
//    protected void onStart() {
//        super.onStart();
//        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
//        String token = sharedPref.getString("token", "");
//
//        if (Objects.equals(token, "")) {
//            //// MenuItem logoutItem = menu.findItem(R.id.action_logout);
//            NavigationView navigationView = findViewById(R.id.navigation_view);
//            Menu menu = navigationView.getMenu();
//            MenuItem menuItem = menu.findItem(R.id.action_logout);
//            menuItem.setVisible(false);
//        }
//
//    }

    //When a back button is pressed, the drawer will be closed instead of going back to another activity
//    @Override
//    public void onBackPressed() {
//
//        if (drawerLayout.isDrawerOpen((GravityCompat.START))){
//            drawerLayout.closeDrawer(GravityCompat.START);
//        }
//        else{
//            super.onBackPressed();
//        }
//    }
    public void onClickSignupBtn(View view) {
        new RegisterActivity.Register().execute();
    }

    public class Register extends AsyncTask<String, Void, Void> {
        private EditText editText;

        HttpConnection httpConnection = new HttpConnection();




        public Button RequestLoanBtn;
        public EditText txtFirstName;
        public EditText txtLastName;
        public EditText txtEmail;
        public EditText txtAge;
        public EditText txtPhone;
        public EditText StreetName;
        public EditText HouseNumber;
        public EditText CityName;
        public EditText PostCode;
        private EditText loanAmount;


        EditText enterFirstName = (EditText) findViewById(R.id.txtFirstName);
        EditText enterLastName = (EditText) findViewById(R.id.txtLastName);
        EditText enterEmail = (EditText) findViewById(R.id.txtEmail);
        EditText enterAge = (EditText) findViewById(R.id.txtAge);
        EditText enterPhone = (EditText) findViewById(R.id.txtPhone);
        EditText enterStreetName = (EditText) findViewById(R.id.StreetName);
        EditText enterHouseNumber = (EditText) findViewById(R.id.HouseNumber);
        EditText enterCityName = (EditText) findViewById(R.id.CityName);
        EditText enterPostCode = (EditText) findViewById(R.id.PostCode);
        EditText enterloanAmount = (EditText) findViewById(R.id.txtAmount);


        @Override
        protected Void doInBackground(String... params) {
            URL url;
            HttpURLConnection urlConnection = null;

            try {
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("FirstName", enterFirstName.getText());
                postDataParams.put("LastName", enterLastName.getText());
                postDataParams.put("Email", enterEmail.getText());
                postDataParams.put("Age", enterAge.getText());
                postDataParams.put("PhoneNumber", enterPhone.getText());
                postDataParams.put("StreetName", enterStreetName.getText());
                postDataParams.put("HouseNumber", enterHouseNumber.getText());
                postDataParams.put("CityName", enterCityName.getText());
                postDataParams.put("PostCode", enterPostCode.getText());
                postDataParams.put("loanAmount", enterloanAmount.getText());





                //url = new URL("http://25.95.117.73:7549/api/Account/Login");
                url = new URL("http://192.168.1.171:4567/api/Account/Register");


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


                } else if (responseCode == HttpURLConnection.HTTP_BAD_REQUEST){
                    runOnUiThread(new Runnable(){
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Sign-up failed. Please try again",
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

