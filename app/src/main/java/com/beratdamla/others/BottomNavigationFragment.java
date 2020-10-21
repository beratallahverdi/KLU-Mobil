package com.beratdamla.others;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.beratdamla.klu_mobil.AnasayfaActivity;
import com.beratdamla.klu_mobil.HomeActivity;
import com.beratdamla.klu_mobil.LoginActivity;
import com.beratdamla.klu_mobil.ProfileActivity;
import com.beratdamla.klu_mobil.R;
import com.beratdamla.klu_mobil.TestActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

public class BottomNavigationFragment extends BottomSheetDialogFragment {
    private String no;
    private String ad;
    private String soyad;
    private String fakulte;
    private String program;
    private String gorunen_ad;
    private String gorunen_program;
    private User user;
    private NavigationView navigationView;
    private RelativeLayout bottom_nav_profile;
    private CircleImageView bottom_nav_image;
    private TextView displayName;
    private TextView schoolName;
    @Override
    public int getTheme(){
        return R.style.BottomSheetDialogTheme;
    }
    @Override
    public Dialog onCreateDialog(Bundle bundle){
        super.onCreateDialog(bundle);

        allData();

        BottomSheetDialog dialog = new BottomSheetDialog(requireContext(),getTheme());
        View contentView = View.inflate(requireContext(), R.layout.bottom_navigation_drawer, null);
        dialog.setContentView(contentView);

        bottom_nav_profile = contentView.findViewById(R.id.bottom_nav_profile);
        bottom_nav_image = contentView.findViewById(R.id.bottom_nav_person_image);
        displayName = contentView.findViewById(R.id.bottom_nav_person_name);
        schoolName = contentView.findViewById(R.id.bottom_nav_person_program);
        navigationView = contentView.findViewById(R.id.navigation_view);

        if(!soyad.equals("SOYAD")){
            navigationView.inflateMenu(R.menu.bottom_navigation_onlogged_in);
            bottom_nav_profile.setVisibility(View.VISIBLE);
        }
        else{
            navigationView.inflateMenu(R.menu.bottom_navigation_logged_out);
            bottom_nav_profile.setVisibility(View.GONE);
        }

        navigationView.setNavigationItemSelectedListener(item -> {
            Intent intent;
            switch (item.getItemId()) {
                case R.id.bottom_nav_signin:
                    intent = new Intent(requireContext(), LoginActivity.class);
                    startActivity(intent);
                    break;
                case R.id.bottom_nav_info:
                    intent = new Intent(requireContext(), ProfileActivity.class);
                    startActivity(intent);
                    break;
                /*
                case R.id.bottom_nav_settings:
                    intent = new Intent(requireContext(), AnasayfaActivity.class);
                    startActivity(intent);
                    break;

                 */
                case R.id.bottom_nav_signout:
                    SharedPreferences settings = requireActivity().getSharedPreferences(TestActivity.PREFS, Context.MODE_PRIVATE);
                    settings.edit().clear().commit();
                    startActivity(new Intent(requireContext(), AnasayfaActivity.class));
                    getActivity().finish();
                    break;
            }
            return false;
        });

        displayName.setText(gorunen_ad);
        schoolName.setText(gorunen_program);
        if(user!=null){
            Picasso.get().load(R.mipmap.logo).into(bottom_nav_image);
            Picasso.get().load(user.getFotoUri()).into(bottom_nav_image);
        }

        return dialog;
    }
    public void allData() {
        try{
            user = new User(new JSONObject(requireContext().getSharedPreferences(getString(R.string.static_sharedprefs), Context.MODE_PRIVATE).getString(getResources().getString(R.string.static_json), "")));
        }catch (JSONException e){
            Toast.makeText(requireContext(),"Veriler Alınırken Hata",Toast.LENGTH_SHORT).show();
        }
        no = requireContext().getSharedPreferences(getString(R.string.static_sharedprefs), Context.MODE_PRIVATE).getString(getResources().getString(R.string.static_unvan), "##########");
        ad = requireContext().getSharedPreferences(getString(R.string.static_sharedprefs), Context.MODE_PRIVATE).getString(getResources().getString(R.string.static_ad), "AD");
        soyad = requireContext().getSharedPreferences(getString(R.string.static_sharedprefs), Context.MODE_PRIVATE).getString(getResources().getString(R.string.static_soyad), "SOYAD");
        fakulte = requireContext().getSharedPreferences(getString(R.string.static_sharedprefs), Context.MODE_PRIVATE).getString(getResources().getString(R.string.static_fakulte), "FAKULTE-YUKSEKOKUL");
        program = requireContext().getSharedPreferences(getString(R.string.static_sharedprefs), Context.MODE_PRIVATE).getString(getResources().getString(R.string.static_program), "PROGRAM");

        gorunen_ad = no+" "+ad+" "+soyad;
        gorunen_program = fakulte+"/"+program;
    }
}
