package com.example.firebaseuserauthentication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FragmentAccount extends Fragment {
    private FirebaseUser firebaseUser;
    private TextView textsignout, texthelpcenter;

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
        // textName = getView().findViewById(R.id.name);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        /* if(firebaseUser!=null){
            textName.setText(firebaseUser.getDisplayName());
        }else{
            textName.setText("Login Failed");
        }
        */
        textsignout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getActivity().getApplicationContext(), LoginActivity.class));
            getActivity().finish();
        });
        texthelpcenter.setOnClickListener(v -> {
            startActivity(new Intent(getActivity().getApplicationContext(), HelpCenterActivity.class));
            getActivity().finish();
        });
    }
}
