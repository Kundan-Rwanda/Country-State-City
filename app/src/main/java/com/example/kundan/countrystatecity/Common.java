package com.example.kundan.countrystatecity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by kundan on 28/09/2018.
 * email: kundan.kumar011@gmail.com
 */

public class Common {

    //JSON URL Emulator Local IP Address is 10.0.2.2
    public static final String BASE_URL="http://10.0.2.2:8888/CountryStateCity/";


    //Tags used in the JSON String
    public static final String countryArray = "country_name";
    public static final String stateArray = "state_name";
    public static final String cityArray = "city_name";

    //JSON array name to hold result
    public static final String JSON_ARRAY = "result";

}
