package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static final String TAG=JsonUtils.class.getSimpleName();

    public static Sandwich parseSandwichJson(String json) {
        if(json !=  null && !json.isEmpty()){
            Log.d(TAG,json);

            try {
                JSONObject sandwichJSONObject = new JSONObject(json);
                JSONObject nameJSONObject = sandwichJSONObject.getJSONObject("name");

                String mainName = nameJSONObject.getString("mainName");
                JSONArray alsoKnownAsArray = nameJSONObject.getJSONArray("alsoKnownAs");

                Log.d(TAG,"main name: "+mainName);

                String placeOfOrigin = sandwichJSONObject.getString("placeOfOrigin");
                Log.d(TAG,"place of origin: "+placeOfOrigin);

                String description = sandwichJSONObject.getString("description");
                Log.d(TAG,"description: "+description);

                String urlString = sandwichJSONObject.getString( "image");
                Log.d(TAG,"url: "+urlString);

                JSONArray ingredientsArray = sandwichJSONObject.getJSONArray("ingredients");
                Log.d(TAG,ingredientsArray.getString(1));


                return new Sandwich(mainName,jsonToList(alsoKnownAsArray)
                        ,placeOfOrigin,description,urlString,jsonToList(ingredientsArray));

            } catch (JSONException e) {
                Log.e(TAG,e.getMessage());
                return null;
            }
        }else return null;
    }

    private static List<String> jsonToList(JSONArray array){

        int lenght = array.length();
        List<String> stringList = new ArrayList<>(lenght);

        for (int i = 0; i<lenght;i++){
            try {
                stringList.add(array.getString(i));
            } catch (JSONException e) {
                Log.e(TAG,e.getMessage());
                return null;
            }
        }
        return stringList;
    }
}
