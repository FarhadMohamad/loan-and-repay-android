package com.example.loanandrepay.company;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.loanandrepay.client.MainActivity;
import com.example.loanandrepay.R;
import com.example.loanandrepay.HttpConnection.ReadHttpTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RequestListActivity extends AppCompatActivity {

    // Listview Adapter
   private ArrayAdapter<Request> adapter;
    private ListView listView;
    private EditText inputSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_list);

       EditText inputSearch = (EditText) findViewById(R.id.search_view);

        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                RequestListActivity.this.adapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });

    }




    @Override
    public void onBackPressed(){
        startActivity( new Intent(this, MainActivity.class) );
        //finish();
    }

    @Override
    protected void onStart() {
            super.onStart();
        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String showLogUser = sharedPref.getString("savedUser", "");
        GetRequestList getRequestList = new GetRequestList();

        getRequestList.execute("http://192.168.1.171:4567/api/GetRequestList?email="+ showLogUser);
    }

    public void btnSearch(View view) {

        EditText editText = (EditText) findViewById(R.id.search_view);
        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String showLogUser = sharedPref.getString("savedUser", "");
        GetRequestList getRequestList = new GetRequestList();
        try {
            getRequestList.execute("http://192.168.1.171:4567/api/SearchRequestByName?name="+editText.getText()+"&email="+ showLogUser);
        } catch (Exception ex) {
            listView.setAdapter(null);
            Toast.makeText(getApplicationContext(), "Sorry, ticket not found, But you can request for the ticket and you will get a notification, whenever the ticket is available", Toast.LENGTH_LONG).show();
            Exception dd = ex;
        }
    }

    private class GetRequestList extends ReadHttpTask {
        @Override
        protected void onPostExecute(CharSequence jsonString) {

            //Gets the data from database and show all info into list by using loop
            final List<Request> requestList = new ArrayList<>();

            try {

                JSONArray array = new JSONArray(jsonString.toString());
                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);

                    //token = obj.getString("UserId");
                    int id = obj.getInt("Id");
                    String company = obj.getString("Company");
                    String firstName = obj.getString("FirstName");
                    String lastName = obj.getString("LastName");
                    String email = obj.getString("Email");
                    int age = obj.getInt("Age");
                    String phone = obj.getString("Phone");
                    String streetName = obj.getString("StreetName");
                    String houseNumber = obj.getString("HouseNumber");
                    String cityName = obj.getString("CityName");
                    int postCode = obj.getInt("PostCode");
                    double amount = obj.getDouble("Amount");
                    String payWithIn = obj.getString("PayWithIn");
                    double monthlyPayment = obj.getDouble("MonthlyPayment");
                    int status = obj.getInt("Status");



                    Request request = new Request (id, company, firstName, lastName, email, age, phone, streetName, houseNumber, cityName, postCode, amount, payWithIn, monthlyPayment,status);

                    requestList.add(request);
                }
                listView = findViewById(R.id.showRequestList);
               adapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1, requestList);
                listView.setAdapter(adapter);



                listView.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {

                    Intent goToRequestListDetail = new Intent(getBaseContext(), RequestListDetailsActivity.class);
                    Request requestdetail = (Request) parent.getItemAtPosition(position);
                    goToRequestListDetail.putExtra("Request", requestdetail);

                    startActivity(goToRequestListDetail);

                });

            } catch (JSONException ex) {
                Log.e("Tickets", ex.getMessage());
            }


        }
    }
}
