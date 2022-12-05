package com.example.firebaseuserauthentication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class FragmentSummary extends Fragment {
    private AdView mAdView;
    private TextView textBalance;
    ListView event, date, amount, currentPlan, Date, Status, PriceGoal;;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_summary, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textBalance = getActivity().findViewById(R.id.textSummaryBalanceAmount);
        event = getActivity().findViewById(R.id.event_balance);
        date = getActivity().findViewById(R.id.date_balance);
        amount = getActivity().findViewById(R.id.amount_balance);

        currentPlan = getActivity().findViewById(R.id.currentPlan);
        Date = getActivity().findViewById(R.id.date);
        Status = getActivity().findViewById(R.id.status);
        PriceGoal = getActivity().findViewById(R.id.priceGoal);

        mAdView = getActivity().findViewById(R.id.adView_summary);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

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
               // listEvent.subList(0, listEvent.size() - 1);
                adapterEvent.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }});
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Registered Users").child(uid).child("Wallet");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ReadWriteUserWallet writeUserWallet = snapshot.getValue(ReadWriteUserWallet.class);
                String txtBalanceAmount = writeUserWallet.getTextBalanceAmount();
                textBalance.setText("Rp " + txtBalanceAmount);
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

        final ArrayList<String> listCurrPlan = new ArrayList<>();
        final ArrayAdapter adapterCurrPlan = new ArrayAdapter<String>(getActivity(), R.layout.list_item, listCurrPlan);
        currentPlan.setAdapter(adapterCurrPlan);

        DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("Registered Users").child(uid).child("My Plan");
        reference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listCurrPlan.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    WriteUserMyPlan writeUserMyPlan = snapshot.getValue(WriteUserMyPlan.class);
                    String txtCurrPlan = writeUserMyPlan.getTextEvent();
                    if (txtCurrPlan.length() > 7) {
                        String txtCurrPlanShort = txtCurrPlan.substring(0,7) + "...";
                        listCurrPlan.add(txtCurrPlanShort);
                    }
                    else{
                        listCurrPlan.add(txtCurrPlan);
                    }
                }
                adapterCurrPlan.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        final ArrayList<String> listDate2 = new ArrayList<>();
        final ArrayAdapter adapterDate2 = new ArrayAdapter<String>(getActivity(), R.layout.list_item, listDate2);
        Date.setAdapter(adapterDate2);

        reference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listDate2.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    WriteUserMyPlan writeUserMyPlan = snapshot.getValue(WriteUserMyPlan.class);
                    String txtDate = writeUserMyPlan.getTextDateUntil();
                    listDate2.add(txtDate);
                }
                adapterDate2.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        final ArrayList<String> listPrice = new ArrayList<>();
        final ArrayAdapter adapterPrice = new ArrayAdapter<String>(getActivity(), R.layout.list_item, listPrice);
        PriceGoal.setAdapter(adapterPrice);

        reference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listPrice.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    WriteUserMyPlan writeUserMyPlan = snapshot.getValue(WriteUserMyPlan.class);
                    String txtPrice = "Rp " + writeUserMyPlan.getTextAmount().substring(0,(writeUserMyPlan.getTextAmount().length()-3));
                    txtPrice += "K";
                    if (txtPrice.length() > 7) {
                        String txtPriceShort = txtPrice.substring(0,7) + "K";
                        listPrice.add(txtPriceShort);
                    }
                    else{
                        listPrice.add(txtPrice);
                    }
                }
                adapterPrice.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        final ArrayList<String> listStatus = new ArrayList<>();
        final ArrayAdapter adapterStatus = new ArrayAdapter<String>(getActivity(), R.layout.list_item, listStatus);
        Status.setAdapter(adapterStatus);

        reference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listStatus.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    WriteUserMyPlan writeUserMyPlan = snapshot.getValue(WriteUserMyPlan.class);
                    String textStatus = writeUserMyPlan.getTextStatus();
                    listStatus.add(textStatus);
                }
                adapterStatus.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
