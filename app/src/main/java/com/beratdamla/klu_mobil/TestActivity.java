package com.beratdamla.klu_mobil;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.beratdamla.others.BottomSheetNavigationFragment;
import com.beratdamla.others.Haber;
import com.beratdamla.others.HaberlerAdapter;
import com.beratdamla.others.KluHTML;
import com.beratdamla.others.KluWS;
import com.beratdamla.others.OnTaskCompleted;
import com.beratdamla.others.SorguHazirlama;
import com.beratdamla.others.User;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.zxing.client.android.CaptureActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TestActivity extends AppCompatActivity {
    public static final String PREFS = "PREFS";
    public static final String EMAIL = "EMAIL";
    public static final String FEMAIL = "FEMAIL";
    public static final String USER_ACCOUNT = "user_account";

    private static final int PERMISSION_REQUEST_CODE = 1;
    private final String[] permissions = new String[]{
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_SMS,
            Manifest.permission.READ_PHONE_NUMBERS,
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    private User user;
    private SharedPreferences sharedPref;
    private String sim_one;
    private String lat;
    private String lng;
    private WebView webView;
    private TextView jsonText;
    private JSONObject userJson;

    private List<Haber> haberler;
    private HaberlerAdapter mAdapter;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private BottomAppBar bottomAppBar;
    private FloatingActionButton fab;

    private String ogrenci_no = "1160505048";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        String extras = getIntent().getStringExtra(getString(R.string.static_ogrenci_no_key));
        if(extras!=null){
            ogrenci_no = extras;
        }
        //user = intent.getParcelableExtra(USER_ACCOUNT);
        Log.d("TestActivity", "onCreate: "+ogrenci_no);
        webView = findViewById(R.id.webView);
        jsonText = findViewById(R.id.jsonText);

        bottomAppBar = findViewById(R.id.bar);
        fab = findViewById(R.id.fab);

        swipeRefreshLayout = findViewById(R.id.family_swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            getHaberler();
            swipeRefreshLayout.setRefreshing(false);
        });
        getHaberler();
        setUpBottomAppBar();
        sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.static_ogrenci_no_key), ogrenci_no);
        editor.commit();

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Log.d("UrL", request.getUrl().toString());
                view.loadUrl(request.getUrl().toString());
                return super.shouldOverrideUrlLoading(view, request);
            }
        });
        webView.getSettings().setBuiltInZoomControls(false);
        webView.getSettings().setSupportZoom(false);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setDomStorageEnabled(true);
    }

    private void getHaberler() {
        new KluHTML(new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(List<Haber> result) {
                haberler = result;
                setupHaberler();
            }
            @Override
            public void onTaskCompleted(JSONObject result) throws JSONException {
                return;
            }
        }, getString(R.string.KLUHaberler),this).execute(".table tbody tr");
    }

    private void setupHaberler(){
        mRecyclerView = findViewById(R.id.rvHaberler);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        // Initialize contacts
        mAdapter = new HaberlerAdapter(haberler);
        mRecyclerView.setAdapter(mAdapter);
    }
    private void refreshBilgiler() {
        try {
            HashMap<String,String> sorgu = SorguHazirlama.OBS_Ogrenci_Numara(sharedPref.getString(getString(R.string.static_ogrenci_no_key),""));
            new KluWS(
                    new OnTaskCompleted() {
                        @Override
                        public void onTaskCompleted(JSONObject result) {
                            setAccount(new User(result));
                            try {
                                jsonText.setText(result.getString("durum"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onTaskCompleted(List<Haber> result) {
                            return;
                        }
                    }
            ).execute(sorgu);
        } catch (Exception e){
            e.printStackTrace();
        }
        finally {
        }
    }

    private boolean checkPermission(String[] permissions){
        int result = 0;
        for (String perm: permissions) {
            result += ContextCompat.checkSelfPermission(this, perm);
        }
        if (result == PackageManager.PERMISSION_GRANTED){
            return false;
        }
        else{
            return true;
        }
    }

    private void requestPermission(String[] permissions){
        ActivityCompat.requestPermissions(this, permissions,PERMISSION_REQUEST_CODE);
    }

    @SuppressLint("MissingPermission")
    private String getPhone(){
        if (Build.VERSION.SDK_INT > 22) {
            //for dual sim mobile
            SubscriptionManager localSubscriptionManager = SubscriptionManager.from(this);
            List<SubscriptionInfo> subsInfoList = localSubscriptionManager.getActiveSubscriptionInfoList();
            String number="";

            for (SubscriptionInfo subscriptionInfo : subsInfoList) {

                number = subscriptionInfo.getNumber();
            }
            return number;
            //if there are two sims in dual sim mobile
              /*  List localList = localSubscriptionManager.getActiveSubscriptionInfoList();
                SubscriptionInfo simInfo = (SubscriptionInfo) localList.get(0);
                SubscriptionInfo simInfo1 = (SubscriptionInfo) localList.get(1);

                final String sim1 = simInfo.getTamAd().toString();
                final String sim2 = simInfo1.getTamAd().toString();
                return ContactsContract.CommonDataKinds.Phone.NUMBER;*/


        }else{
            //below android version 22
            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                return "";
            }
            return telephonyManager.getSimSerialNumber();
        }
    }
    public void scanQR(View v){
        if(!checkPermission(permissions))
        {
            lat = String.valueOf(this.getLastKnownLocation().getLatitude());
            lng = String.valueOf(this.getLastKnownLocation().getLongitude());
            sim_one = getPhone();
            Intent intent = new Intent(getApplicationContext(), CaptureActivity.class);
            intent.setAction("com.google.zxing.client.android.SCAN");
            intent.putExtra("SAVE_HISTORY", false);
            startActivityForResult(intent, 0);
        }
        else{
            requestPermission(permissions);
        }
    }

    public void toggleFabMode(View view) {
        View layout;
        if (bottomAppBar.getFabAlignmentMode() == BottomAppBar.FAB_ALIGNMENT_MODE_END) {
            layout = findViewById(R.id.test_homepage);
            layout.setVisibility(View.GONE);
            layout = findViewById(R.id.main_homepage);
            layout.setVisibility(View.VISIBLE);
            bottomAppBar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_CENTER);
            fab.setImageResource(R.drawable.ic_explore);
        } else {
            layout = findViewById(R.id.main_homepage);
            layout.setVisibility(View.GONE);
            layout = findViewById(R.id.test_homepage);
            layout.setVisibility(View.VISIBLE);
            bottomAppBar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_END);
            fab.setImageResource(R.drawable.ic_people);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String contents = data.getStringExtra("SCAN_RESULT");
                contents += "?";
                contents += "t=" + sim_one + "&";
                contents += "lat=" + lat + "&";
                contents += "lng=" + lng;
                webView.loadUrl(contents);
                refreshBilgiler();
            } else if (resultCode == RESULT_CANCELED) {
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        String mesaj = "";
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                for (int i=0;i<permissions.length; i++) {
                    if (grantResults.length > 0 && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    }
                    else {
                        mesaj += permissions[i].substring(permissions[i].lastIndexOf(".")+1)+" : İzin Verilmedi\n";
                    }
                }
                if(!mesaj.equals("")){
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.coordinatorLayout),mesaj,Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        }
    }

    public User getUser(){
        return user;
    }
    private void setUpBottomAppBar() {
        //find id

        //set bottom bar to Action bar as it is similar like Toolbar
        setSupportActionBar(bottomAppBar);

        //click event over Bottom bar menu item
        bottomAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                   /*
                   case R.id.action_notification:
                        Toast.makeText(HomepageActivity.this, "Notification clicked.", Toast.LENGTH_SHORT).show();
                        break;
                   */
                }
                return false;
            }
        });

        //click event over navigation menu like back arrow or hamburger icon
        bottomAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open bottom sheet
                BottomSheetDialogFragment bottomSheetDialogFragment = BottomSheetNavigationFragment.newInstance();
                bottomSheetDialogFragment.show(getSupportFragmentManager(), "Bottom Sheet Dialog Fragment");
            }
        });
    }
    //Inflate menu to bottom bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            /*
            case R.id.action_notification:
                break;
            */
        }
        return super.onOptionsItemSelected(item);
    }
    private Location getLastKnownLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = locationManager.getProviders(false);
        Location bestLocation = null;
        for (String provider : providers) {
            @SuppressLint("MissingPermission") Location l = locationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            else{
                if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                    // Found best last known location: %s", l);
                    bestLocation = l;
                    continue;
                }
            }
        }
        return bestLocation;
    }
    public void setAccount(User a){
        user = a;
        sharedPref.edit().putString(getString(R.string.static_ogrenci_json_key),a.getJson());
       /* user = new User(a.getKimlikAd(),a.getKimlikSoyad(),a.getEposta(),a.getFamilyEmail(),a.getTelefon(),a.getMember(),a.getFotoUri());
        Intent intent = new Intent(this, TestActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtra(TestActivity.USER_ACCOUNT,user);
        startActivity(intent);
        finish();*/
    }
}