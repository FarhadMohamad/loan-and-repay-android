package com.example.loanandrepay.company;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loanandrepay.LoginActivity;
import com.example.loanandrepay.client.MainActivity;
import com.example.loanandrepay.R;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class RequestListDetailsActivity extends AppCompatActivity {

    private Request request;
    private TextView Company;
    private TextView FirstName;
    private TextView LastName;
    private TextView Email;
    private TextView Age;
    private TextView Phone;
    private TextView StreetName;
    private TextView HouseNumber;
    private TextView CityName;
    private TextView PostCode;
    private TextView Amount;
    private TextView PayWithIn;
    private TextView MonthlyPayment;
    private TextView Status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_list_details);

        Intent intent = getIntent();
        request = (Request) intent.getSerializableExtra("Request");


        if (request.getStatus() == 1)
        {
            Button btn = findViewById(R.id.btnAccept);
            btn.setEnabled(false);
        }

         if (request.getStatus() == 2)
        {
            Button btn = findViewById(R.id.btnReject);
            btn.setEnabled(false);
        }

        Company = findViewById(R.id.txtCompany);
        Company.setText("Company: " + request.getCompany());

        FirstName = findViewById(R.id.txtFirstName);
        FirstName.setText("First Name: " + request.getFirstName());

        LastName = findViewById(R.id.txtLastName);
        LastName.setText("Last Name: " + request.getLastName());

        Email = findViewById(R.id.txtEmail);
        Email.setText("Email: " + request.getEmail());

        Age = findViewById(R.id.txtAge);
        Age.setText("Age: " + request.getAge());

        Phone = findViewById(R.id.txtPhone);
        Phone.setText("Phone: " + request.getPhone());

        StreetName = findViewById(R.id.txtStreetName);
        StreetName.setText("Street Name: " + request.getStreetName());

        HouseNumber = findViewById(R.id.txtHouseNumber);
        HouseNumber.setText("House number: " + request.getHouseNumber());

        CityName = findViewById(R.id.txtCityName);
        CityName.setText("City name: " + request.getCityName());

        PostCode = findViewById(R.id.txtPostCode);
        PostCode.setText("Post code: " + request.getPostCode());

        Amount = findViewById(R.id.txtAmount);
        Amount.setText("Amount: " + request.getAmount());

        PayWithIn = findViewById(R.id.txtPayWithIn);
        PayWithIn.setText("Pay within: " + request.getPayWithIn());

        MonthlyPayment = findViewById(R.id.txtMonthyPayment);
        MonthlyPayment.setText("Monthly payment: " + request.getMonthlyPayment());

//        Status = findViewById(R.id.txtStatus);
//        Status.setText("Status: " + request.getStatus());

    }


    public void btnAcceptRequest(View view) {

        new ChangeRequestStatus(1).execute();
//        Intent intent = new Intent(this, RequestListDetailsActivity.class);
//        finish();

    }


    public void btnRejectRequest(View view) {


        new ChangeRequestStatus(2).execute();
    }


    public class ChangeRequestStatus extends AsyncTask<String, Void, Void> {

        private int Requeststatus;


        public ChangeRequestStatus(int getRequestStatus) {
            this.Requeststatus = getRequestStatus;

        }

        @Override
        protected Void doInBackground(String... params) {

            URL url;
            HttpURLConnection urlConnection = null;


            try {

                SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);

                String token = sharedPref.getString("token", "");

                url = new URL("http://192.168.1.171:4567/api/InstallmentRequestStatus?id=" + request.getId() + "&status=" + Requeststatus);


                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setRequestProperty("Authorization", "Bearer " + token);

                urlConnection.setRequestMethod("POST");
                urlConnection.setDoInput(true);

                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));


                writer.flush();
                writer.close();
                os.close();


                int responseCode = urlConnection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_ACCEPTED) {


                    Intent intent = new Intent(RequestListDetailsActivity.this, CompanyMainActivity.class);
                    startActivity(intent);
                    finish();


                } else if (responseCode == HttpURLConnection.HTTP_UNAUTHORIZED) {

                    Intent intentLogin = new Intent(RequestListDetailsActivity.this, CompanyMainActivity.class);
                    startActivity(intentLogin);
                    finish();

                    Toast.makeText(getApplicationContext(), "Authorization failed, please login.",
                            Toast.LENGTH_LONG).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null)
                    urlConnection.disconnect();
            }
            return null;
        }
    }

}

