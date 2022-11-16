package com.example.firebaseuserauthentication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;
import androidx.fragment.app.Fragment;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;


public class FragmentNewPlanCreate extends Fragment {
    private AdView mAdView;
    private EditText editEvent, editAmount, editDescription;
    private DatabaseReference dbref;
    private WriteUserNewPlan writeUserNewPlan;
    private ProgressDialog progressDialog;
    private Button btnSave, btnCancel;
    private FirebaseAuth mAuth;
    long maxid=0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_newplancreate, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editEvent = getActivity().findViewById(R.id.event);
        editAmount = getActivity().findViewById(R.id.amount);
        editDescription = getActivity().findViewById(R.id.description);
        //Amount = Integer.parseInt(editAmount.getText().toString());

        btnSave = getActivity().findViewById(R.id.btn_save);
        btnCancel = getActivity().findViewById(R.id.btn_cancel);

        mAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);

        writeUserNewPlan = new WriteUserNewPlan();

        dbref = FirebaseDatabase.getInstance().getReference("Registered Users");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                    maxid=(snapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        btnCancel.setOnClickListener(v -> {
            getActivity().finish();
        });
        btnSave.setOnClickListener(v -> {
            if(editAmount.getText().length()>0 && editEvent.getText().length()>0) {
                newplan(editEvent.getText().toString(), editAmount.getText().toString());
            } else {
                Toast.makeText(getActivity().getApplicationContext(),"Please fill in all the data", Toast.LENGTH_SHORT).show();
            }
        });



    }
    private void newplan(String event, String amount){
        progressDialog.show();

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();
        dbref = FirebaseDatabase.getInstance().getReference("Registered Users");

        writeUserNewPlan.setTextEvent(editEvent.getText().toString());
        writeUserNewPlan.setTextAmount(editAmount.getText().toString());
        writeUserNewPlan.setTextDescription(editDescription.getText().toString());
        // To get the UID from the Firebase Authentication and NOT generated by the Realtime Database
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        // To insert the UID under the Registered Users
        dbref = FirebaseDatabase.getInstance().getReference().child(uid);
        dbref.push().setValue(writeUserNewPlan);
        // To insert the value of question and answer
        //dbref.push().setValue(writeUserNewPlan);
        progressDialog.dismiss();
    }
    private void reload(){
        startActivity(new Intent(getActivity().getApplicationContext(), FragmentNewPlanCreate.class));
    }
}