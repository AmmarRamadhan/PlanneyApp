package com.example.firebaseuserauthentication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
import java.util.Objects;

public class FragmentBalance extends Fragment {
    ListView event, date, amount;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_balance, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        event = getActivity().findViewById(R.id.event_balance);
        date = getActivity().findViewById(R.id.date_balance);
        amount = getActivity().findViewById(R.id.amount_balance);
        FirebaseAuth auth = FirebaseAuth.getInstance();

        String uid = auth.getUid();
        final ArrayList<String> listEvent = new ArrayList<>();
        final ArrayAdapter adapterEvent = new ArrayAdapter<String>(getActivity(), R.layout.list_item, listEvent);
        event.setAdapter(adapterEvent);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Registered Users").child(uid).child("Transaction");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listEvent.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    WriteUserNewPlan writeUserNewPlan = snapshot.getValue(WriteUserNewPlan.class);
                    String txtEventPlan = writeUserNewPlan.getTextEvent();
                    if (txtEventPlan.length() > 10) {
                        String txtEventShort = txtEventPlan.substring(0,10) + "...";
                        listEvent.add(txtEventShort);
                    }
                    else{
                        listEvent.add(txtEventPlan);
                    }
                }
                adapterEvent.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        final ArrayList<String> listDate = new ArrayList<>();
        final ArrayAdapter adapterDate = new ArrayAdapter<String>(getActivity(), R.layout.list_item, listDate);
        date.setAdapter(adapterDate);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listDate.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    WriteUserNewPlan writeUserNewPlan = snapshot.getValue(WriteUserNewPlan.class);
                    String txtDate = writeUserNewPlan.getTextDate();
                    listDate.add(txtDate);
                }
                adapterDate.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        final ArrayList<String> listAmount = new ArrayList<>();
        final ArrayAdapter adapterAmount = new ArrayAdapter<String>(getActivity(), R.layout.list_item, listAmount);
        amount.setAdapter(adapterAmount);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listAmount.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    WriteUserNewPlan writeUserNewPlan = snapshot.getValue(WriteUserNewPlan.class);
                    String txtPrice = writeUserNewPlan.getTextAmount();
                    String transaction = writeUserNewPlan.getTextTransaction();

                    if(txtPrice.length() < 4){
                        txtPrice = "Rp " + txtPrice;
                        if (Objects.equals(transaction, "Income")) {
                            txtPrice = "+" + txtPrice;
                            listAmount.add(txtPrice);
                        }

                        else if (Objects.equals(transaction, "Expense")) {
                            txtPrice = "-" + txtPrice;
                            listAmount.add(txtPrice);
                        }
                    }
                    else if (txtPrice.length() >= 4) {
                        txtPrice = "Rp "+ txtPrice.substring(0,txtPrice.length()-3) + "K";

                        if (Objects.equals(transaction, "Income")) {
                            txtPrice = "+" + txtPrice;
                            listAmount.add(txtPrice);
                        }
                        else if (Objects.equals(transaction, "Expense")) {
                            txtPrice = "-" + txtPrice;
                            listAmount.add(txtPrice);
                        }
                    }
                }
                adapterAmount.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
