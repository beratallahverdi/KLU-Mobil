package com.beratdamla.others;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KluHTML extends AsyncTask<String, Void, List<Haber>> {

    private String url_string = "http://www.klu.edu.tr/Sayfa_Gruplari/84-haberler-ve-etkinlikler.klu";

    public OnTaskCompleted delegate = null;
    private Context mContext;

    public KluHTML() {

    }


    public KluHTML(OnTaskCompleted listener, String url, Context context){
        url_string=url;
        delegate=listener;
        mContext = context;
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();

    }

    @Override
    protected List<Haber> doInBackground(String... strings) {
        List<Haber> haberList = new ArrayList<Haber>();
        try {
            Document haberHTML = Jsoup.connect(url_string).get();
            Elements e = haberHTML.select(strings[0]);
            for (Element a: e) {
                String resim = a.select(".resim img").attr("src");
                String link = a.select("a").attr("href");
                String baslik = a.select("a").text();
                a = a.select("td").last();
                String info = a.select("div").first().html();
                String icerik = a.select("div").last().text();
                Haber add = new Haber("http://www.klu.edu.tr/"+resim,link,baslik,info,icerik);
                haberList.add(add);
            }
            Log.d("KLUHTML", "doInBackground: tamamlandı");
            return haberList;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
        return null;
    }

    protected void onPostExecute(List<Haber> result){
        delegate.onTaskCompleted(result);
    }
}
