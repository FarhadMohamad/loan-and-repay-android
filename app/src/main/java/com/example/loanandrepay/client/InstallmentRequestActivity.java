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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loanandrepay.HttpConnection.HttpConnection;
import com.example.loanandrepay.HttpConnection.ReadHttpTask;
import com.example.loanandrepay.LoginActivity;
import com.example.loanandrepay.R;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InstallmentRequestActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private Spinner spinner;
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
    public EditText AmountToPay;
    public TextView totalAmountToPay;



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

        GetCompany getCompany = new GetCompany();
        getCompany.execute("http://192.168.1.171:4567/api/CompanyName");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_installment_request);
        this.setTitle("Installment Request");

        //region Navigation bar related
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
        //endregion


        loanAmount = findViewById(R.id.txtAmount);
        radiosixMonths = findViewById(R.id.radioBtnSixMonths);
        radiontvelveMonths = findViewById(R.id.radioBtntvelveMonths);
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
        StreetName = findViewById(R.id.txtStreetName);
        StreetName.addTextChangedListener(RequestLoanBtnTextWatcher);
        HouseNumber = findViewById(R.id.txtHouseNumber);
        HouseNumber.addTextChangedListener(RequestLoanBtnTextWatcher);
        CityName = findViewById(R.id.txtCityName);
        CityName.addTextChangedListener(RequestLoanBtnTextWatcher);
        PostCode = findViewById(R.id.txtPostCode);
        PostCode.addTextChangedListener(RequestLoanBtnTextWatcher);
        loanAmount = findViewById(R.id.txtAmount);
        loanAmount.addTextChangedListener(RequestLoanBtnTextWatcher);
        RequestLoanBtn = findViewById(R.id.btnRequesttInstallment);

        ///////////////////////////////////////

//Here we calculate the amount that the user has given and calculate it according to the radio button selected
        RadioGroup rg = (RadioGroup) findViewById(R.id.radio_group);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {



                if (checkedId == R.id.radioBtnSixMonths) {
                    //RadioButton radioButton = (RadioButton) findViewById(R.id.radiobtnSixMonths);
                    checkRequiredFields();
                    AmountToPay = findViewById(R.id.txtAmount);

                    String value = AmountToPay.getText().toString();
                    double finalValue = Integer.parseInt(value);

                    double getResult = calculationSixMonths(finalValue);
                    // String getResultinString = String.format("%1.2f", getResult);
                    String getResultinString = String.valueOf(getResult);

                    totalAmountToPay = findViewById(R.id.txtmonthlyPayment);

                    totalAmountToPay.setText(getResultinString);

                }

                if (checkedId == R.id.radioBtntvelveMonths) {
                    //RadioButton radioButton = (RadioButton) findViewById(R.id.radiobtnSixMonths);
                    checkRequiredFields();

                    AmountToPay = findViewById(R.id.txtAmount);

                    String value = AmountToPay.getText().toString();
                    double finalValue = Integer.parseInt(value);

                    double getResult = calculationTwelveMonths(finalValue);
                    //String getResultinString = String.format("%1.2f", getResult);
                    String getResultinString = String.valueOf(getResult);


                    totalAmountToPay = findViewById(R.id.txtmonthlyPayment);


                    totalAmountToPay.setText(getResultinString);

                }

            }
        });
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
                Intent a = new Intent(InstallmentRequestActivity.this, ProfileActivity.class);
                startActivity(a);
                break;
            case R.id.nav_requestStatus:
                item.setChecked(true);
                Intent b = new Intent(InstallmentRequestActivity.this, RequestStatusActivity.class);
                startActivity(b);
                break;
            case R.id.nav_newRequest:
                item.setChecked(false);
                Intent c = new Intent(InstallmentRequestActivity.this, InstallmentRequestActivity.class);
                startActivity(c);
                break;

            case R.id.action_logout:
                SharedPreferences preferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                finish();
                Intent goToLoginActivity = new Intent(InstallmentRequestActivity.this, LoginActivity.class);
                // set the new task and clear flags
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(goToLoginActivity);
                break;
        }

        return false;
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
            TextView textView = findViewById(R.id.txtmonthlyPayment);
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
    public TextWatcher RequestLoanBtnTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            checkRequiredFields();

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private void checkRequiredFields() {
        Button btnRequesttInstallment = findViewById(R.id.btnRequesttInstallment);

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radio_group);

        // get selected radio button from radioGroup
        int selectedId = radioGroup.getCheckedRadioButtonId();


        if (!txtFirstName.getText().toString().isEmpty() && !txtLastName.getText().toString().isEmpty() && !txtEmail.getText().toString().isEmpty() && !txtAge.getText().toString().isEmpty() &&
        !txtPhone.getText().toString().isEmpty() && !StreetName.getText().toString().isEmpty() && !HouseNumber.getText().toString().isEmpty() && !CityName.getText().toString().isEmpty() &&
        !PostCode.getText().toString().isEmpty() && !loanAmount.getText().toString().isEmpty() && selectedId != -1 )   {
            btnRequesttInstallment.setEnabled(true);
        } else {
            btnRequesttInstallment.setEnabled(false);
        }
    }


    public void onClickRequestLoan(View view) {


        new InstallmentRequest().execute();

    }

    public double calculationSixMonths(double finalValue) {
        double actualFinalValue = finalValue;
        double result = (finalValue * 0.15);
        double endResult = (result + actualFinalValue) / 6;
        endResult= Double.parseDouble(new DecimalFormat("##.##").format(endResult));
        return endResult;
    }

    public double calculationTwelveMonths(double finalValue) {
        double actualFinalValue = finalValue;
        double result = (finalValue * 0.25);
        double endResult = (result + actualFinalValue) / 12;
        endResult= Double.parseDouble(new DecimalFormat("##.##").format(endResult));
        return endResult;
    }




    private class GetCompany extends ReadHttpTask {
        @Override
        protected void onPostExecute(CharSequence jsonString) {

            //Gets the data from database and show all info into list by using loop
            final List<CompanyNames> request = new ArrayList<>();

            try {

                JSONArray array = new JSONArray(jsonString.toString());
                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);

                    //token = obj.getString("UserId");
                    String company = obj.getString("Name");

                    CompanyNames companyName = new CompanyNames(company);

                    request.add(companyName);
                }
                spinner = findViewById(R.id.spinnerCompany);
                ArrayAdapter<CompanyNames> adapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_spinner_item, request);
                spinner.setAdapter(adapter);
//
            } catch (JSONException ex) {
                //messageTextView.setText(ex.getMessage());
                Log.e("InstallmentRequest", ex.getMessage());
            }
        }
    }

    public class InstallmentRequest extends AsyncTask<String, Void, Void> {

        HttpConnection httpConnection = new HttpConnection();
        Spinner spinner = (Spinner) findViewById(R.id.spinnerCompany);
        String companyName = spinner.getSelectedItem().toString();
        EditText firstName = (EditText) findViewById(R.id.txtFirstName);
        EditText lastName = (EditText) findViewById(R.id.txtLastName);
        EditText email = (EditText) findViewById(R.id.txtEmail);
        EditText age = (EditText) findViewById(R.id.txtAge);
        EditText phone = (EditText) findViewById(R.id.txtPhone);
        EditText streetName = (EditText) findViewById(R.id.txtStreetName);
        EditText houseNumber = (EditText) findViewById(R.id.txtHouseNumber);
        EditText cityName = (EditText) findViewById(R.id.txtCityName);
        EditText postCode = (EditText) findViewById(R.id.txtPostCode);
        EditText loanAmount = (EditText) findViewById(R.id.txtAmount);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        // get selected radio button from radioGroup
        int selectedId = radioGroup.getCheckedRadioButtonId();
        // find the radiobutton by returned id
        RadioButton radionButton = (RadioButton) findViewById(selectedId);
        TextView monthlyPayment = findViewById(R.id.txtmonthlyPayment);
        @Override
        protected Void doInBackground(String... params) {
            URL url;
            HttpURLConnection urlConnection = null;
            try {
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("Company", companyName);
                postDataParams.put("FirstName", firstName.getText());
                postDataParams.put("LastName", lastName.getText());
                postDataParams.put("Email", email.getText());
                postDataParams.put("Age", age.getText());
                postDataParams.put("Phone", phone.getText());
                postDataParams.put("StreetName", streetName.getText());
                postDataParams.put("HouseNumber", houseNumber.getText());
                postDataParams.put("CityName", cityName.getText());
                postDataParams.put("PostCode", postCode.getText());
                postDataParams.put("Amount", loanAmount.getText());
                postDataParams.put("PayWithIn", radionButton.getText());
                postDataParams.put("MonthlyPayment", monthlyPayment.getText());
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
                }
               else if (responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Request failed, you might have already requested this request before",
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

