package com.example.loanandrepay;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class InstallmentRequestActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private Spinner spinner1;
    private EditText loanAmount;
    private EditText loanAmount2;
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
    public EditText AmountToPay;
    public TextView totalAmountToPay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_installment_request);


        loanAmount = findViewById(R.id.txtAmount);
        radiosixMonths = findViewById(R.id.radiobtnSixMonths);
        radiontvelveMonths = findViewById(R.id.radiobtntvelveMonths);
        loanAmount.addTextChangedListener(radioButtonTextWatcher);
        ///////////////////////////////////////
        AmountToPay = findViewById(R.id.txtAmount);
        AmountToPay.addTextChangedListener(AmountTextWatcher);
        txtFirstName = findViewById(R.id.txtFirstName);
        txtFirstName.addTextChangedListener(RequestLoanBtnTextWatcher);
        txtLastName = findViewById(R.id.txtLastName);
        txtLastName.addTextChangedListener(RequestLoanBtnTextWatcher);
        txtEmail = findViewById(R.id.txtEmail);
        txtEmail.addTextChangedListener(RequestLoanBtnTextWatcher);
        txtAge = findViewById(R.id.txtAge);
        txtAge.addTextChangedListener(RequestLoanBtnTextWatcher);
        txtPhone = findViewById(R.id.txtPhone);
        txtPhone.addTextChangedListener(RequestLoanBtnTextWatcher);
        StreetName = findViewById(R.id.StreetName);
        StreetName.addTextChangedListener(RequestLoanBtnTextWatcher);
        HouseNumber = findViewById(R.id.HouseNumber);
        HouseNumber.addTextChangedListener(RequestLoanBtnTextWatcher);
        CityName = findViewById(R.id.CityName);
        CityName.addTextChangedListener(RequestLoanBtnTextWatcher);
        PostCode = findViewById(R.id.PostCode);
        PostCode.addTextChangedListener(RequestLoanBtnTextWatcher);
        loanAmount = findViewById(R.id.txtAmount);
        loanAmount.addTextChangedListener(RequestLoanBtnTextWatcher);
        RequestLoanBtn = findViewById(R.id.RequestLoanBtn);



        ///////////////////////////////////////

//Here we calculate the amount that the user has given and calculate it according to the radio button selected
        RadioGroup rg = (RadioGroup) findViewById(R.id.radio_group);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radiobtnSixMonths) {
                    //RadioButton radioButton = (RadioButton) findViewById(R.id.radiobtnSixMonths);

                    AmountToPay = findViewById(R.id.txtAmount);

                    String value = AmountToPay.getText().toString();
                    double finalValue = Integer.parseInt(value);

                    double getResult = calculationSixMonths(finalValue);
                    String getResultinString = String.format("%1.2f", getResult);

                     totalAmountToPay = findViewById(R.id.LoanToRepay);

                    totalAmountToPay.setText(getResultinString);

                }

                if (checkedId == R.id.radiobtntvelveMonths) {
                    //RadioButton radioButton = (RadioButton) findViewById(R.id.radiobtnSixMonths);

                    AmountToPay = findViewById(R.id.txtAmount);

                    String value = AmountToPay.getText().toString();
                    double finalValue = Integer.parseInt(value);

                    double getResult = calculationTwelveMonths(finalValue);
                    String getResultinString = String.format("%1.2f", getResult);

                    totalAmountToPay = findViewById(R.id.LoanToRepay);


                    totalAmountToPay.setText(getResultinString);

                }

            }
        });
    }


    //enable radion button and request loan button after all fields are filled
    private final TextWatcher AmountTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {


            RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radio_group);

            // get selected radio button from radioGroup
            int selectedId = radioGroup.getCheckedRadioButtonId();
            // find the radiobutton by returned id
            RadioButton radionButton = (RadioButton) findViewById(selectedId);
            radioGroup.clearCheck();
            TextView textView = findViewById(R.id.LoanToRepay);
            textView.setText("");

        }

        @Override
        public void afterTextChanged(Editable s) {


        }
    };


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
            String usernameInput = loanAmount.getText().toString();

            RequestLoanBtn.setEnabled(!txtFirstNameFiled.isEmpty());
            RequestLoanBtn.setEnabled(!txtLastNameFiled.isEmpty());
            RequestLoanBtn.setEnabled(!txtEmailFiled.isEmpty());
            RequestLoanBtn.setEnabled(!txtAgeFiled.isEmpty());
            RequestLoanBtn.setEnabled(!txtPhoneFiled.isEmpty());
            RequestLoanBtn.setEnabled(!StreetNameFiled.isEmpty());
            RequestLoanBtn.setEnabled(!HouseNumberFiled.isEmpty());
            RequestLoanBtn.setEnabled(!CityNameFiled.isEmpty());
            RequestLoanBtn.setEnabled(!PostCodeFiled.isEmpty());
            RequestLoanBtn.setEnabled(!usernameInput.isEmpty());


        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
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


    public void onClickRequestLoan(View view) {


        new InstallmentRequest().execute();

    }



    public class InstallmentRequest extends AsyncTask<String, Void, Void> {

        HttpConnection httpConnection = new HttpConnection();

        Spinner enterCompanyName = (Spinner) findViewById(R.id.spinnerCompany);
        String CompanyName = enterCompanyName.getSelectedItem().toString();

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
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        // get selected radio button from radioGroup
        int selectedId = radioGroup.getCheckedRadioButtonId();
        // find the radiobutton by returned id
        RadioButton radionButton = (RadioButton) findViewById(selectedId);


        @Override
        protected Void doInBackground(String... params) {
            URL url;
            HttpURLConnection urlConnection = null;

            try {
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("Company", CompanyName);
                postDataParams.put("FirstName", enterFirstName.getText());
                postDataParams.put("LastName", enterLastName.getText());
                postDataParams.put("Email", enterEmail.getText());
                postDataParams.put("Age", enterAge.getText());
                postDataParams.put("Phone", enterPhone.getText());
                postDataParams.put("StreetName", enterStreetName.getText());
                postDataParams.put("HouseNumber", enterHouseNumber.getText());
                postDataParams.put("CityName", enterCityName.getText());
                postDataParams.put("PostCode", enterPostCode.getText());
                postDataParams.put("Amount", enterloanAmount.getText());
                postDataParams.put("PayWithIn", radionButton.getText());
                // postDataParams.put("MonthlyPayment", enterloanAmount.getText());

                SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);

                String token = sharedPref.getString("token", "");

                url = new URL("http://192.168.1.171:4567/api/InstallmentRequest");


                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestProperty("Authorization", "Bearer " + token);

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

                    Intent goToAuthentication = new Intent(InstallmentRequestActivity.this, MainActivity.class);
                    startActivity(goToAuthentication);
                    finish();


                } else if (responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Request failed, please try again",
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

