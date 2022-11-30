package com.example.firebaseuserauthentication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FragmentAccount extends Fragment {
    private FirebaseUser firebaseUser;
    private TextView textsignout, texthelpcenter, textmywallet, textpersonalinfo;
    private SwitchMaterial switchBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textsignout = getActivity().findViewById(R.id.textsignout);
        texthelpcenter = getActivity().findViewById(R.id.texthelpcenter);
        textmywallet = getActivity().findViewById(R.id.textMyWallet);
        textpersonalinfo = getActivity().findViewById(R.id.textpersonalinfo);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        textpersonalinfo.setOnClickListener(v -> {
            Fragment fragment = new FragmentPersonalInfo();
            getParentFragmentManager().beginTransaction().replace(R.id.container_fragment, fragment).commit();
        });

/*
       switchBtn = getActivity().findViewById(R.id.switchDarkMode);

       switchBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

                }
            }
        });

        boolean isDarkModeOn = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES;
        switchBtn.setChecked(isDarkModeOn);

        if (isDarkModeOn){
            switchBtn.setText("Dark Mode");
        } else {
            switchBtn.setText("Light Mode");
        }
       */
        textmywallet.setOnClickListener(v -> {
            Fragment fragment = new FragmentMyWallet();
            getParentFragmentManager().beginTransaction().replace(R.id.container_fragment, fragment).commit();
        });

        texthelpcenter.setOnClickListener(v -> {
            startActivity(new Intent(getActivity().getApplicationContext(), HelpCenterActivity.class));
            getActivity().finish();
        });

        textsignout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getActivity().getApplicationContext(), LoginActivity.class));
            getActivity().finish();
        });

    }

}
