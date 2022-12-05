package com.example.firebaseuserauthentication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FragmentMyPlan extends Fragment {
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mGetReference = mDatabase.getReference();
    ImageView myplancreate;
    ListView currentPlan, Date, Status, PriceGoal;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_myplan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        currentPlan = getActivity().findViewById(R.id.currentPlan);
        Date = getActivity().findViewById(R.id.date);
        Status = getActivity().findViewById(R.id.status);
        PriceGoal = getActivity().findViewById(R.id.priceGoal);
        FirebaseAuth auth = FirebaseAuth.getInstance();

        myplancreate = getActivity().findViewById(R.id.newplancreate);
        myplancreate.setOnClickListener(v -> {
            Fragment fragment = new FragmentMyPlanCreate();
            getParentFragmentManager().beginTransaction().replace(R.id.container_fragment, fragment).commit();
        });

        String uid = auth.getUid();
        final ArrayList<String> listCurrPlan = new ArrayList<>();
        final ArrayAdapter adapterCurrPlan = new ArrayAdapter<String>(getActivity(), R.layout.list_item, listCurrPlan);
        currentPlan.setAdapter(adapterCurrPlan);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Registered Users").child(uid).child("My Plan");
        reference.addValueEventListener(new ValueEventListener() {
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

        final ArrayList<String> listDate = new ArrayList<>();
        final ArrayAdapter adapterDate = new ArrayAdapter<String>(getActivity(), R.layout.list_item, listDate);
        Date.setAdapter(adapterDate);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listDate.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    WriteUserMyPlan writeUserMyPlan = snapshot.getValue(WriteUserMyPlan.class);
                    String txtDate = writeUserMyPlan.getTextDateUntil();
                    listDate.add(txtDate);
                }
                adapterDate.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        final ArrayList<String> listPrice = new ArrayList<>();
        final ArrayAdapter adapterPrice = new ArrayAdapter<String>(getActivity(), R.layout.list_item, listPrice);
        PriceGoal.setAdapter(adapterPrice);

        reference.addValueEventListener(new ValueEventListener() {
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

        reference.addValueEventListener(new ValueEventListener() {
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