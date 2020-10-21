package com.beratdamla.others;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public interface OnTaskCompleted{
    void onTaskCompleted(JSONObject result) throws JSONException;
    void onTaskCompleted(List<Haber> result);
}