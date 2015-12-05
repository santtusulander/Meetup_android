package com.example.qwerty.http;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by H3916 on 1.12.2015.
 */

/**
 * This fragment is developed primarily for the MeetupActivity to retain data over configuration
 * changes, such as device rotation, and date setting. With small changes, it could easily be
 * utilized more globally throughout the app if need be. Though because of the amount of resources
 * already spent on this project, further implementation will be left for the future..
 */
public class DataRetainFragment extends Fragment {

    // data object we want to retain
    private JSONObject data = new JSONObject();

    DateParser dateParser = new DateParser();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // retain this fragment
        setRetainInstance(true);
    }

    public void setData(boolean setDate, JSONObject dataToSave) {
        try {
            if (dataToSave.has("date")) {
                if(setDate) {
                    data.put("parsedDate",
                            dateParser.parseSetDate(dataToSave.getJSONObject("date")));
                    data.put("rawDate", dataToSave.getJSONObject("date"));
                }
                else
                    data.put("parsedDate",
                            dateParser.parseResponseDate(dataToSave.getJSONObject("date")));
            }
            if (dataToSave.has("meetup")) {
                data.put("meetup", dataToSave.getJSONObject("meetup"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public JSONObject getRawDate() {
        try {
            return data.getJSONObject("rawDate");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getParsedDate() {
        try {
            return data.getString("parsedDate");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public JSONObject getData() {
        return data;
    }

}