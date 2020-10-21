package com.beratdamla.others;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class KluWS extends AsyncTask<HashMap<String,String>, Void, JSONObject> {

    private String url_string = "http://ws.klu.edu.tr/projeler/index.php?";
    private final String user = "";
    private final String pass = "";
    private HashMap<String,String> hashMap;
    private OnTaskCompleted listener;
    private HttpURLConnection urlConnection;
    private BufferedReader bufferedReader;
    private URL url;

    public OnTaskCompleted delegate = null;//Call back interface

    public KluWS() {

    }


    public KluWS(OnTaskCompleted listener){
        urlConnection = null;
        bufferedReader = null;
        hashMap = new HashMap<String, String>();
        hashMap.put("user",user);
        hashMap.put("pass",pass);
        String getSorgu = "";
        Set<Map.Entry<String, String>> set = hashMap.entrySet();

        for (Map.Entry<String, String> me : set) {
            getSorgu += me.getKey()+"="+me.getValue()+"&";
        }
        getSorgu = getSorgu.substring(0,getSorgu.lastIndexOf("&"));
        url_string+=getSorgu;
        delegate=listener;
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
    }

    @Override
    protected JSONObject doInBackground(HashMap<String, String>... hashMaps) {
        String getSorgu = "&";
        Set<Map.Entry<String, String>> set = hashMaps[0].entrySet();

        for (Map.Entry<String, String> me : set) {
            getSorgu += me.getKey()+"="+me.getValue()+"&";
        }
        getSorgu = getSorgu.substring(0,getSorgu.lastIndexOf("&"));
        url_string+=getSorgu;
        Log.d("SORGU",url_string);

        try {
            url = new URL(url_string);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();


            InputStream stream = urlConnection.getInputStream();

            bufferedReader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            String line = "";

            while ((line = bufferedReader.readLine()) != null) {
                buffer.append(line+"\n");
            }

            return new JSONObject(buffer.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    protected void onPostExecute(JSONObject result){
        // your stuff
        try {
            delegate.onTaskCompleted(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
