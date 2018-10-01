package com.example.kundan.countrystatecity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.kundan.countrystatecity.Common.BASE_URL;
import static com.example.kundan.countrystatecity.Common.JSON_ARRAY;
import static com.example.kundan.countrystatecity.Common.cityArray;
import static com.example.kundan.countrystatecity.Common.countryArray;
import static com.example.kundan.countrystatecity.Common.stateArray;

/**
 * Created by kundan on 28/09/2018.
 * email: kundan.kumar011@gmail.com
 */

public class MainActivity extends AppCompatActivity {

    //Spinner to hold Country, State/Province, City
    private Spinner mSpinCountry,mSpinState,mSpinCity;

    // To hold country response from getCountry.php API
    private JSONArray countryResult;

    // To hold state response from getState.php API
    private JSONArray stateResult;

    // To hold city response from getCity.php API
    private JSONArray cityResult;


    //To hold all country
    private ArrayList<String> countryArrayList;

    //To hold all state of the country
    private ArrayList<String> stateArrayList;

    //To hold all scity of the state
    private ArrayList<String> cityArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize country, state and city spinner
        mSpinCountry=(Spinner)findViewById(R.id.spinCountry);
        mSpinState=(Spinner)findViewById(R.id.spinState);
        mSpinCity=(Spinner)findViewById(R.id.spinCity);

        //Initialize Country Array List
        countryArrayList = new ArrayList<String>();


        //Fetch all Country
        getCountry();


        //On Country Change, Change List of State of the country
        mSpinCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

                //fetch Country_ID of the selected country to fetch list of states
                getCountryId(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mSpinState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                //fetch State_ID of the selected country to fetch list of states
                getStateId(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void getCountry() {
        StringRequest stringRequest = new StringRequest(BASE_URL+"getCountry.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            j = new JSONObject(response);
                            countryResult = j.getJSONArray(JSON_ARRAY);

                            //Fetch Country List
                            countryDetails(countryResult);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplication(), ""+error,Toast.LENGTH_LONG).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void countryDetails(JSONArray result) {
        for (int i = 0; i < result.length(); i++) {
            try {
                JSONObject json = result.getJSONObject(i);
                countryArrayList.add(json.getString(countryArray));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        mSpinCountry.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, countryArrayList));

    }
    private void getCountryId(int position) {
        String country_id="";
        try {
            //Getting object of given index
            JSONObject json = countryResult.getJSONObject(position);
            //Fetching name from that object
            country_id= json.getString("country_id");

            getState(country_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    private void getState(String country_id) {
        StringRequest stringRequest = new StringRequest(BASE_URL+"getState.php/?country_id="+country_id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            j = new JSONObject(response);
                            stateResult = j.getJSONArray(JSON_ARRAY);
                            stateDetails(stateResult);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplication(), ""+error,Toast.LENGTH_LONG).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void stateDetails(JSONArray result) {

        //Create stateArrayList object here, because after each change of country state must be added (NOT Appended)
        stateArrayList=new ArrayList<String>();

        for (int i = 0; i < result.length(); i++) {
            try {
                JSONObject json = result.getJSONObject(i);
                stateArrayList.add(json.getString(stateArray));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
       // stateArrayList.add(0,"Select State");
        mSpinState.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, stateArrayList));

    }

    private void getStateId(int position) {

        String state_id="";
        try {
            //Getting object of given index
            JSONObject json = stateResult.getJSONObject(position);
            //Fetching name from that object
            state_id= json.getString("state_id");
            getCity(state_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getCity(String state_id) {

        StringRequest stringRequest = new StringRequest(BASE_URL+"getCity.php/?state_id="+state_id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            j = new JSONObject(response);
                            cityResult = j.getJSONArray(JSON_ARRAY);
                            cityDetails(cityResult);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplication(), ""+error,Toast.LENGTH_LONG).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void cityDetails(JSONArray cityResult) {
        //Create cityArrayList object here, because after each change of state city must be added (NOT Appended)
        cityArrayList=new ArrayList<String>();

        for (int i = 0; i < cityResult.length(); i++) {
            try {
                JSONObject json = cityResult.getJSONObject(i);
                cityArrayList.add(json.getString(cityArray));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        mSpinCity.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, cityArrayList));


    }

}
