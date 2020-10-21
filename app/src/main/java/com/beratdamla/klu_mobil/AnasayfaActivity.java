package com.beratdamla.klu_mobil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.beratdamla.others.BottomNavigationFragment;
import com.beratdamla.others.BottomSheetNavigationFragment;
import com.beratdamla.others.Haber;
import com.beratdamla.others.HaberlerAdapter;
import com.beratdamla.others.KluHTML;
import com.beratdamla.others.OnTaskCompleted;
import com.beratdamla.others.User;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.zxing.client.android.CaptureActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class AnasayfaActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anasayfa);
        initializeView();
    }
    private void initializeView(){
        webView = findViewById(R.id.webView);
        jsonText = findViewById(R.id.jsonText);

        bottomAppBar = findViewById(R.id.AnasayfaBar);
        fab = findViewById(R.id.AnasayfaFab);

        swipeRefreshLayout = findViewById(R.id.family_swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(false);
        });
        getHaberler();
        bottomAppBar.setNavigationOnClickListener(view -> {
            Bundle bundle = new Bundle();
            BottomNavigationFragment bottomNavigationFragment = new BottomNavigationFragment();
            bottomNavigationFragment.show(getSupportFragmentManager(), "Bottom Sheet Dialog Fragment");
        });
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
}
