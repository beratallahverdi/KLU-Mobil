
package com.beratdamla.others;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.beratdamla.klu_mobil.AnasayfaActivity;
import com.beratdamla.klu_mobil.HomeActivity;
import com.beratdamla.klu_mobil.ProfileActivity;
import com.beratdamla.klu_mobil.R;
import com.beratdamla.klu_mobil.TestActivity;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class BottomSheetNavigationFragment extends BottomSheetDialogFragment {

    public static BottomSheetNavigationFragment newInstance() {

        Bundle args = new Bundle();

        BottomSheetNavigationFragment fragment = new BottomSheetNavigationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    //Bottom Sheet Callback
    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {

        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss();
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            //check the slide offset and change the visibility of close button
            if (slideOffset > 0.5) {
                closeButton.setVisibility(View.VISIBLE);
            } else {
                closeButton.setVisibility(View.GONE);
            }
        }
    };

    private ImageView closeButton;
    private TextView bottom_email;
    private TextView bottom_name;
    private CircleImageView bottom_image;

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        //Get the content View
        final View contentView = View.inflate(getContext(), R.layout.bottom_navigation_drawer, null);
        dialog.setContentView(contentView);

        NavigationView navigationView = contentView.findViewById(R.id.navigation_view);
        //((TestActivity)getActivity()).getUser().getKimlikAd()
        //((TestActivity)getActivity()).getUser().getEposta()
        //((TestActivity)getActivity()).getUser().getFotoUri()

        if(((TestActivity)getActivity()).getUser()!=null){
            Picasso.get().load(R.mipmap.logo).into((CircleImageView)contentView.findViewById(R.id.bottom_nav_person_image));

            Picasso.get().load(((TestActivity)getActivity()).getUser().getFotoUri()).into((CircleImageView)contentView.findViewById(R.id.bottom_nav_person_image));
            ((TextView)contentView.findViewById(R.id.bottom_nav_person_name)).setText(((TestActivity)getActivity()).getUser().getTamAd());
            ((TextView)contentView.findViewById(R.id.bottom_nav_person_program)).setText(((TestActivity)getActivity()).getUser().getEposta());

        }
        //implement navigation menu item click event
        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.bottom_nav_info:
                    Intent intent = new Intent(getContext().getApplicationContext(), ProfileActivity.class);
                    Bundle bundle = new Bundle();
                    intent.putExtra(TestActivity.USER_ACCOUNT,((TestActivity)getActivity()).getUser());
                    startActivity(intent);
                    getActivity().finish();
                    break;
                    /*
                case R.id.bottom_nav_settings:
                    startActivity(new Intent(getContext().getApplicationContext(), AnasayfaActivity.class));
                    break;

                     */
                case R.id.bottom_nav_signout:
                    SharedPreferences settings = getContext().getSharedPreferences(TestActivity.PREFS, Context.MODE_PRIVATE);
                    settings.edit().clear().commit();
                    startActivity(new Intent(getContext().getApplicationContext(), HomeActivity.class));
                    getActivity().finish();
                    break;

            }
            return false;
        });
        closeButton = contentView.findViewById(R.id.close_image_view);
        closeButton.setOnClickListener(view -> dismiss());

        //Set the coordinator layout behavior
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();

        //Set callback
        if (behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
        }
    }
}