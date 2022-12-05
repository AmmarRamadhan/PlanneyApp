package com.example.firebaseuserauthentication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FragmentMyWallet extends Fragment {
    private TextView textBalance, textBalanceAmount;
    private DatabaseReference dbref;
    private ReadWriteUserWallet writeUserWallet;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mywallet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textBalance = getActivity().findViewById(R.id.textBalance);
        textBalanceAmount = getActivity().findViewById(R.id.balanceAmount);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String uid = auth.getUid();

        textBalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBalanceDialog();
            }
        });

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Registered Users").child(uid).child("Wallet");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                writeUserWallet = snapshot.getValue(ReadWriteUserWallet.class);
                String txtBalanceAmount = writeUserWallet.getTextBalanceAmount();
                textBalanceAmount.setText("Rp " + txtBalanceAmount);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        /*
        final ArrayList<String> listBalance = new ArrayList<>();
        final ArrayAdapter adapterBalance = new ArrayAdapter<String>(getActivity(), R.layout.balance_item, listBalance);
        balanceAmount.setAdapter(adapterBalance);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Registered Users").child(uid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listBalance.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    ReadWriteUserWallet writeUserWallet = snapshot.getValue(ReadWriteUserWallet.class);
                    String txtAmount = writeUserWallet.getTextBalanceAmount();
                    listBalance.add(txtAmount);
                }
                adapterBalance.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        */
    }

    private void showBalanceDialog() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String uid = auth.getUid();
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle("Please Enter Your Balance");
        LinearLayout linearLayout=new LinearLayout(getActivity());
        final EditText setBalance= new EditText(getActivity());

        // write the email using which you registered
        setBalance.setHint("Please enter your balance");
        setBalance.setMinEms(4);
        setBalance.setInputType(InputType.TYPE_CLASS_NUMBER);
        linearLayout.addView(setBalance);
        linearLayout.setPadding(70,60,70,40);
        builder.setView(linearLayout);

        // Click on Recover and a email will be sent to your registered email id
        builder.setPositiveButton("Set", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String balance=setBalance.getText().toString().trim();
                Toast.makeText(getActivity().getApplicationContext(),"Amount is : Rp " + balance, Toast.LENGTH_SHORT).show();
                writeUserWallet.setTextBalanceAmount(setBalance.getText().toString());
                dbref = FirebaseDatabase.getInstance().getReference("Registered Users").child(uid).child("Wallet");
                dbref.setValue(writeUserWallet);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

}
