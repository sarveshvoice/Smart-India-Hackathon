package com.mygov.parivartan.smartindiahackathon.employee;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mygov.parivartan.smartindiahackathon.R;

/**
 * Created by deepak on 01-04-2017.
 */

public class EmployeeRegistration extends AppCompatActivity{

    //Firebase Variables
    private FirebaseAuth mAuth;

    public EditText mEmail;
    private EditText mPassword;
    private EditText mAadhaar;
    private TextView mLogin;
    private Button mRegister;
    private EditText mName;
    private ProgressDialog progressDialog;
    private DatabaseReference mDatabase;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_reg);

        //Firebase Instances
        mAuth = FirebaseAuth.getInstance();

        mEmail = (EditText)findViewById(R.id.et_reg_email);
        mPassword = (EditText)findViewById(R.id.et_reg_pass);
        mAadhaar = (EditText)findViewById(R.id.et_reg_aadhaar);
        mRegister = (Button)findViewById(R.id.bt_reg);
        mLogin = (TextView)findViewById(R.id.bt_login);

        mDatabase = FirebaseDatabase.getInstance().getReference("Employee");
        TextView textView = (TextView)findViewById(R.id.emp_title);
        textView.setText(R.string.employee_signup);

        progressDialog = new ProgressDialog(this);

        if(mAuth.getCurrentUser()!=null){
            finish();
            startActivity(new Intent(EmployeeRegistration.this,EmployeeProfile.class));
        }

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),EmployeeLogin.class));
            }
        });
    }

    private void registerUser(){
        final String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();
        final String aadhaar = mAadhaar.getText().toString();

        //Added shared Prefreneces
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email",email);
        editor.putString("password",password);
        editor.putString("uid",aadhaar);
        editor.apply();

        //Log the inputs and
        Log.e("email","");
        Log.e("password","");

        if(TextUtils.isEmpty(email)){
            mEmail.setError("Enter Correct Email");
            return;
        }
        if(aadhaar.length() != 12){
            mAadhaar.setError("Enter a valid Aadhaar Number");
            return;
        }

        if(TextUtils.isEmpty(password)){
            mPassword.setError("Enter a Valid 8 digit Password");
            return;
        }
        progressDialog.setMessage("Registering Please Wait");
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //TODO add here the Employee Object to push to Database
                            Toast.makeText(EmployeeRegistration.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            Toast.makeText(EmployeeRegistration.this, "Registration Error", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }
}