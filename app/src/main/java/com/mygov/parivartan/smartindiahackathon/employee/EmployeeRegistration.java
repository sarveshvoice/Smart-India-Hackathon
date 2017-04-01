package com.mygov.parivartan.smartindiahackathon.employee;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

    }
}
