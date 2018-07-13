package com.example.freya.talking_hands;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SignUp extends AppCompatActivity {

    EditText dategetter,name,pass,conf,contact,email;
    Calendar myCalendar;
    Button signUp;
    ProgressDialog mprog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                setDate();
            }

        };

        dategetter = findViewById(R.id.editText9);
        name= findViewById(R.id. editText12);
        name.requestFocus();
        email= findViewById(R.id.editText4);
        pass= findViewById(R.id.editText7);
        conf= findViewById(R.id.editText8);
        contact= findViewById(R.id.editText10);

        mprog=new ProgressDialog(this);

        signUp= findViewById(R.id.button2);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(dategetter.getText().toString())||TextUtils.isEmpty(name.getText().toString())||TextUtils.isEmpty(email.getText().toString())
                        ||TextUtils.isEmpty(contact.getText().toString())||TextUtils.isEmpty(pass.getText().toString())||TextUtils.isEmpty(conf.getText().toString())){
                    Toast.makeText(SignUp.this, "Fill all the details", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(pass.length()<6){
                    Toast.makeText(SignUp.this, "Password should be more than 6 characters", Toast.LENGTH_SHORT).show();
                }
                if(pass.getText().toString().equals(conf.getText().toString())){
                    mprog.setMessage("Creating new Account");
                    mprog.setCancelable(false);
                    mprog.show();
                    FirebaseAuth mAuth= FirebaseAuth.getInstance();
                    String emailId=email.getText().toString();
                    String password=pass.getText().toString();
                    mAuth.createUserWithEmailAndPassword(emailId,password).addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            mprog.dismiss();
                            if(task.isSuccessful()){
                                Toast.makeText(SignUp.this, "User Created", Toast.LENGTH_SHORT).show();
                                Intent i=new  Intent(SignUp.this,LoginActivity.class);
                                startActivity(i);
                            }
                            else{
                                Toast.makeText(SignUp.this, "User not created", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(SignUp.this, "Re-Type your password", Toast.LENGTH_SHORT).show();
                    pass.setText("");
                    conf.setText("");
                }
            }
        });

        dategetter.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                new DatePickerDialog(SignUp.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    public void setDate (){
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dategetter.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(SignUp.this,LoginActivity.class));
    }
}

