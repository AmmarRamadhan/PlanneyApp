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
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
import java.util.Objects;


public class FragmentNewPlan extends Fragment {
    private AdView mAdView;
    private EditText editEvent, editAmount, editDescription, editDate;
    private DatabaseReference dbref;
    private WriteUserNewPlan writeUserNewPlan;
    private ProgressDialog progressDialog;
    private Button btnSave, btnCancel;
    private FirebaseAuth mAuth;
    private RadioGroup radioTransactionGroup;
    private RadioButton selectedRadioButton;
    private String transactionVote;
    private ReadWriteUserWallet writeUserWallet;
    long maxid=0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_newplan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editEvent = getActivity().findViewById(R.id.event_transaction);
        editAmount = getActivity().findViewById(R.id.amount_transaction);
        editDescription = getActivity().findViewById(R.id.description_transaction);
        editDate = getActivity().findViewById(R.id.pick_date_transaction);
        //Amount = Integer.parseInt(editAmount.getText().toString());

        radioTransactionGroup = getActivity().findViewById(R.id.transactionGroup);

        btnSave = getActivity().findViewById(R.id.btn_save_transaction);
        btnCancel = getActivity().findViewById(R.id.btn_cancel_transaction);

        mAuth = FirebaseAuth.getInstance();

        String uid = mAuth.getUid();
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);

        writeUserWallet = new ReadWriteUserWallet();
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
            reload();
        });

        btnSave.setOnClickListener(v -> {
            if(editAmount.getText().length()>0 && editEvent.getText().length()>0) {
                selectedRadioButton = getActivity().findViewById(radioTransactionGroup.getCheckedRadioButtonId());
                transactionVote = selectedRadioButton.getText().toString();
                newplan(editEvent.getText().toString(), editAmount.getText().toString(), editDescription.getText().toString(),
                        editDate.getText().toString(), transactionVote);

                if(Objects.equals(transactionVote, "Income"))
                {
                    String txtAmount = writeUserNewPlan.getTextAmount();

                   // int income = Integer.parseInt(txtAmount);

                    //String txtBalanceAmount = writeUserWallet.getTextBalanceAmount();

                    //int wallet = Integer.parseInt(txtBalanceAmount);
                   // int total = wallet;
                    //String txtTotal = Integer.toString(total);
                    //writeUserWallet.setTextBalanceAmount(txtTotal);
                    //reference.setValue(writeUserNewPlan);
                    //Toast.makeText(getActivity().getApplicationContext(),txtTotal, Toast.LENGTH_SHORT).show();
                }
                else if (Objects.equals(transactionVote, "Expense"))
                {
                    Toast.makeText(getActivity().getApplicationContext(),"Data has been saved, Expense confirmed", Toast.LENGTH_SHORT).show();
                }
                reload();
            } else {
                Toast.makeText(getActivity().getApplicationContext(),"Please fill in all the data", Toast.LENGTH_SHORT).show();
            }
        });

        mAdView = getActivity().findViewById(R.id.adView_transaction);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        editDate.setOnClickListener(new View.OnClickListener() {
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
                        editDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                },
                        // on below line we are passing year, month and day for selected date in our date picker.
                        year_from, month_from, day_from);
                // at last we are calling show to display our date picker dialog.
                dateFromPickerDialog.show();
            }
        });

    }
    private void newplan(String event, String amount, String description, String pick_date, String transaction){
        progressDialog.show();

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();
        dbref = FirebaseDatabase.getInstance().getReference("Registered Users");

        writeUserNewPlan.setTextEvent(editEvent.getText().toString());
        writeUserNewPlan.setTextAmount(editAmount.getText().toString());
        writeUserNewPlan.setTextDescription(editDescription.getText().toString());
        writeUserNewPlan.setTextDate(editDate.getText().toString());
        writeUserNewPlan.setTextTransaction(transactionVote);

        // To get the UID from the Firebase Authentication and NOT generated by the Realtime Database
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // To insert the UID under the Registered Users
        dbref = FirebaseDatabase.getInstance().getReference("Registered Users").child(uid).child("Transaction");

        // To insert the value of the variables
        dbref.push().setValue(writeUserNewPlan);

        progressDialog.dismiss();
    }
    private void reload(){
        Fragment fragment = new FragmentNewPlan();
        getParentFragmentManager().beginTransaction().replace(R.id.container_fragment, fragment).commit();
    }
}
