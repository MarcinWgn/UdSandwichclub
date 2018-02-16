package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String TAG = JsonUtils.class.getSimpleName();
    private static final String NAME = "name";
    private static final String MAIN_NAME = "mainName";
    private static final String ALSO_KNOWN_AS = "alsoKnownAs";
    private static final String PLACE_OF_ORIGIN = "placeOfOrigin";
    private static final String DESCRIPTION = "description";
    private static final String IMAGE = "image";
    private static final String INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) {
        if (json != null && !json.isEmpty()) {
            Log.d(TAG, json);

            try {
                JSONObject sandwichJSONObject = new JSONObject(json);
                JSONObject nameJSONObject = sandwichJSONObject.getJSONObject(NAME);

                String mainName = nameJSONObject.getString(MAIN_NAME);
                JSONArray alsoKnownAsArray = nameJSONObject.getJSONArray(ALSO_KNOWN_AS);

                String placeOfOrigin = sandwichJSONObject.getString(PLACE_OF_ORIGIN);

                String description = sandwichJSONObject.getString(DESCRIPTION);

                String urlString = sandwichJSONObject.getString(IMAGE);

                JSONArray ingredientsArray = sandwichJSONObject.getJSONArray(INGREDIENTS);

                return new Sandwich(mainName, jsonToList(alsoKnownAsArray)
                        , placeOfOrigin, description, urlString, jsonToList(ingredientsArray));

            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                return null;
            }
        } else return null;
    }

    private static List<String> jsonToList(JSONArray array) {

        int lenght = array.length();
        List<String> stringList = new ArrayList<>(lenght);

        for (int i = 0; i < lenght; i++) {
            try {
                stringList.add(array.getString(i));
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                return null;
            }
        }
        return stringList;
    }
}
