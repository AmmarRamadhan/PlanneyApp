package com.example.firebaseuserauthentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private EditText editEmail, editPassword, editPasswordConf, editQuestion, editAnswer;
    private Button btnRegister, btnLogin;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    private DatabaseReference dbref;
    private ReadWriteUserQNA writeUserQNA;
    private ReadWriteUserWallet writeUserWallet;

    private static final String TAG="RegisterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        editEmail = findViewById(R.id.email);
        editPassword = findViewById(R.id.password);
        editPasswordConf = findViewById(R.id.password_conf);
        //editQuestion = findViewById(R.id.question);
        //editAnswer = findViewById(R.id.answer);
        btnRegister = findViewById(R.id.btn_register);
        btnLogin = findViewById(R.id.btn_login);

        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);

        //writeUserQNA = new ReadWriteUserQNA();
        //dbref = FirebaseDatabase.getInstance().getReference("Registered Users");
        writeUserWallet = new ReadWriteUserWallet();
        btnLogin.setOnClickListener(v -> {
            finish();
        });
        btnRegister.setOnClickListener(v -> {
           if(editEmail.getText().length()>0 && editPassword.getText().length()>0 && editPasswordConf.getText().length()>0){
                if(editPassword.getText().toString().equals(editPasswordConf.getText().toString())){
                    register(editEmail.getText().toString(), editPassword.getText().toString());
               }else{
                    Toast.makeText(getApplicationContext(),"Please fill in the same passwords", Toast.LENGTH_SHORT).show();
               }
           }else{
               Toast.makeText(getApplicationContext(),"Please fill in all the data", Toast.LENGTH_SHORT).show();
           }
        });
        }

    private void register(String email, String password){
        progressDialog.show();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    FirebaseUser firebaseUser = auth.getCurrentUser();
                    if(firebaseUser!=null) {
                        FirebaseAuth auth = FirebaseAuth.getInstance();
                        String uid = auth.getUid();

                        // To insert the UID under the Registered Users
                        dbref = FirebaseDatabase.getInstance().getReference("Registered Users").child(uid).child("Wallet");
                        // To insert the value of question and answer
                        //dbref.setValue(writeUserQNA);
                        writeUserWallet.setTextBalanceAmount("0");
                        dbref.setValue(writeUserWallet);
                        reload();
                    }
                } else {
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        editPassword.setError("Your password is too weak. Please use a mix of alphabets, numbers and special characters");
                        editPassword.requestFocus();
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        editPassword.setError("Your email is invalid or already in use. Please re-enter.");
                        editPassword.requestFocus();
                    } catch (FirebaseAuthUserCollisionException e) {
                        editPassword.setError("User is already registered with this email. Use another email.");
                        editPassword.requestFocus();
                    } catch (Exception e) {
                        Log.e(TAG, e.getMessage());
                        Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    // Hide Progress Dialog
                    progressDialog.dismiss();
                }
            }
        });
    }

    private void reload(){
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
/*
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            reload();
        }
    }*/
}