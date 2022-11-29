package com.example.firebaseuserauthentication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentMyWallet extends Fragment {
    private TextView textBalance;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mywallet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textBalance=getActivity().findViewById(R.id.textBalance);

        textBalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBalanceDialog();
            }
        });

    }

    private void showBalanceDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle("Recover Password");
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
                Toast.makeText(getActivity().getApplicationContext(),"Amount is :" + balance, Toast.LENGTH_SHORT).show();
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