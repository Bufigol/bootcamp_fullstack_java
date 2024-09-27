package com.bufigol.utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

public class JsonHandler {
    public static String[] getAllValues(String jsonString) {
        jsonString = jsonString.replace("\" = \"", "\" : \"");
        jsonString = jsonString.replace("=", ":");
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray values = new JSONArray();
        getAllValuesRecursive(jsonObject, values);
        String[] result = new String[values.length()];
        for (int i = 0; i < values.length(); i++) {
            Object value = values.get(i);
            String stringValue;
            if (value instanceof String) {
                stringValue = (String) value;
            } else {
                stringValue = String.valueOf(value);
            }
            result[i] = stringValue;
        }
        return result;
    }
    public static Map<String, String> getJsonMap(String jsonString) {
        jsonString = jsonString.replace("\" = \"", "\" : \"");
        jsonString = jsonString.replace("=", ":");
        JSONObject jsonObject = new JSONObject(jsonString);
        Map<String, String> result = new HashMap<>();
        getJsonMapRecursive(jsonObject, result);
        return result;
    }
    public static ArrayList<JSONObject> jsonArrayToArrayList(JSONArray jsonArray) {
        ArrayList<JSONObject> result = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            Object value = jsonArray.get(i);
            if (value instanceof JSONObject) {
                result.add((JSONObject) value);
            } else {
                throw new RuntimeException("Expected JSONObject, but got " + value.getClass());
            }
        }
        return result;
    }

    private static void getAllValuesRecursive(JSONObject obj, JSONArray values) {
        Iterator<String> keys = obj.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            Object value = obj.get(key);
            if (value instanceof JSONObject) {
                getAllValuesRecursive((JSONObject) value, values);
            } else if (value instanceof JSONArray) {
                JSONArray array = (JSONArray) value;
                for (int i = 0; i < array.length(); i++) {
                    values.put(array.get(i));
                }
            } else {
                values.put(value);
            }
        }
    }
    private static void getJsonMapRecursive(JSONObject obj, Map<String, String> map) {
        Iterator<String> keys = obj.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            Object value = obj.get(key);
            if (value instanceof JSONObject) {
                getJsonMapRecursive((JSONObject) value, map);
            } else if (value instanceof JSONArray) {
                JSONArray array = (JSONArray) value;
                for (int i = 0; i < array.length(); i++) {
                    Object arrayValue = array.get(i);
                    map.put(key + "[" + i + "]", String.valueOf(arrayValue));
                }
            } else {
                map.put(key, String.valueOf(value));
            }
        }
    }
}