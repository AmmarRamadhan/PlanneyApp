package com.example.firebaseuserauthentication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FragmentPersonalInfo extends Fragment {
    private FirebaseUser firebaseUser;
    private TextView textUsername, textEmail, textOccupation, textPhoneNumber, textPassword;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_personalinfo, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textUsername = getView().findViewById(R.id.userUsername);
        textEmail = getView().findViewById(R.id.userEmail);
        textOccupation = getView().findViewById(R.id.userOccupation);
        textPhoneNumber = getView().findViewById(R.id.userPhoneNumber);
        textPassword = getView().findViewById(R.id.userPassword);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if(firebaseUser==null){

        }else{
            textUsername.setText("Username Not Set");
        }

        if(firebaseUser.getEmail()!=null){
            textEmail.setText(firebaseUser.getEmail());
        }else{
            textEmail.setText("Email Not Set");
        }

        if(firebaseUser == null){

        }else{
            textOccupation.setText("Occupation Not Set");
        }

        if(firebaseUser==null){

        }else{
            textPhoneNumber.setText("Phone Number Not Set");
        }

        if(firebaseUser==null){

        }else{
            textPassword.setText("Password Not Set");
        }
    }
}
