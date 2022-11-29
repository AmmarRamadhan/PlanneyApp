package com.example.firebaseuserauthentication;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
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
import androidx.fragment.app.Fragment;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;


public class FragmentMyPlanCreate extends Fragment {
    private AdView mAdView;
    private EditText editEvent, editAmount, editDescription, editDateFrom, editDateUntil;
    private DatabaseReference dbref;
    private WriteUserMyPlan writeUserMyPlan;
    private ProgressDialog progressDialog;
    private Button btnSave, btnCancel;
    private FirebaseAuth mAuth;
    long maxid=0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_myplancreate, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editEvent = getActivity().findViewById(R.id.event);
        editAmount = getActivity().findViewById(R.id.amount);
        editDescription = getActivity().findViewById(R.id.description);
        editDateFrom = getActivity().findViewById(R.id.pick_date_from);
        editDateUntil = getActivity().findViewById(R.id.pick_date_until);
        //Amount = Integer.parseInt(editAmount.getText().toString());

        btnSave = getActivity().findViewById(R.id.btn_save);
        btnCancel = getActivity().findViewById(R.id.btn_cancel);

        mAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);

        writeUserMyPlan = new WriteUserMyPlan();

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
                myplan(editEvent.getText().toString(), editAmount.getText().toString(), editDescription.getText().toString(),
                        editDateFrom.getText().toString(), editDateUntil.getText().toString());
                Toast.makeText(getActivity().getApplicationContext(),"Data has been saved", Toast.LENGTH_SHORT).show();
                reload();
            } else {
                Toast.makeText(getActivity().getApplicationContext(),"Please fill in all the data", Toast.LENGTH_SHORT).show();
            }
        });

        mAdView = getActivity().findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        editDateFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are getting the instance of our calendar.
                final Calendar calendar = Calendar.getInstance();

                // on below line we are getting our day, month and year.
                int year_from = calendar.get(Calendar.YEAR);
                int month_from = calendar.get(Calendar.MONTH);
                int day_from = calendar.get(Calendar.DAY_OF_MONTH);

                // on below line we are creating a variable for date picker dialog.
                DatePickerDialog dateFromPickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                // on below line we are setting date to our text view.
                                editDateFrom.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        },
                        // on below line we are passing year, month and day for selected date in our date picker.
                        year_from, month_from, day_from);
                // at last we are calling show to display our date picker dialog.
                dateFromPickerDialog.show();
            }
        });

        editDateUntil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are getting the instance of our calendar.
                final Calendar calendar = Calendar.getInstance();

                // on below line we are getting our day, month and year.
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                // on below line we are creating a variable for date picker dialog.
                DatePickerDialog dateUntilPickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // on below line we are setting date to our text view.
                        editDateUntil.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                },
                        // on below line we are passing year, month and day for selected date in our date picker.
                        year, month, day);
                // at last we are calling show to display our date picker dialog.
                dateUntilPickerDialog.show();
            }
        });


    }
    private void myplan(String event, String amount, String description, String pick_date_from, String pick_date_until){
        progressDialog.show();

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();
        dbref = FirebaseDatabase.getInstance().getReference("Registered Users");

        writeUserMyPlan.setTextEvent(editEvent.getText().toString());
        writeUserMyPlan.setTextAmount(editAmount.getText().toString());
        writeUserMyPlan.setTextDescription(editDescription.getText().toString());
        writeUserMyPlan.setTextDateFrom(editDateFrom.getText().toString());
        writeUserMyPlan.setTextDateUntil(editDateUntil.getText().toString());

        // To get the UID from the Firebase Authentication and NOT generated by the Realtime Database
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // To insert the UID under the Registered Users
        dbref = FirebaseDatabase.getInstance().getReference("Registered Users").child(uid).child("My Plan");

        // To insert the value of the variables
        dbref.push().setValue(writeUserMyPlan);

        //dbref.push().setValue(writeUserNewPlan);
        progressDialog.dismiss();
    }
    private void reload(){
        getFragmentManager().beginTransaction().detach(FragmentMyPlanCreate.this).attach(FragmentMyPlanCreate.this).commit();
    }
}