package com.example.taski;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {

    EditText mfname, memail, mpassword, mpass;
    RadioGroup radioGroup;
    String types;
    FirebaseAuth fAuth;

    Button login_btn, register_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        login_btn = findViewById(R.id.login1);
        register_btn = findViewById(R.id.register1);
        mfname= findViewById(R.id.fname);
        memail= findViewById(R.id.email);
        mpassword= findViewById(R.id.passcode);
        mpass= findViewById(R.id.pass1);
         radioGroup = findViewById(R.id.mRadioGroup);



        fAuth = FirebaseAuth.getInstance();


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.participant) {
                    types = "participant";
                } else if (checkedId == R.id.organiser) {
                    types = "organiser";
                } else {
                    types = "nulll";
                }
            }
        });


        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String mail= memail.getText().toString().trim();
                String code= mpassword.getText().toString().trim();
                String mcode= mpass.getText().toString().trim();

                final String fn = mfname.getText().toString();
                final String tp = types;

                if(TextUtils.isEmpty(fn)){
                    mfname.setError("Your name is required");
                    return;
                }

                if(TextUtils.isEmpty(mail)){
                    memail.setError("Email is required");
                    return;
                }

                if(TextUtils.isEmpty(code)){
                    mpassword.setError("Password is required");
                    return;
                }
                if(code.length() < 5){
                    mpassword.setError("Password is too short");
                    return;
                }

                if(TextUtils.isEmpty(mcode)){
                    mpass.setError("Password confirmation is Required.");
                    return;
                }
                if (!code.equals(mcode)) {
                    mpass.setError("Passwords does not match");
                    return;
                }

                if(TextUtils.isEmpty(tp)){
                    Toast.makeText(Register.this, "Select a type please!!", Toast.LENGTH_SHORT).show();
                    return;
                }


                fAuth.createUserWithEmailAndPassword(mail,code).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            Toast.makeText(Register.this, "Registration successful", Toast.LENGTH_SHORT).show();

                            if (tp=="participant"){
                                openHomeuser();
                            }
                            else {
                                openHomeorg();
                            }
                        }else{
                            Toast.makeText(Register.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLogin();
            }
        });
    }

    public void openLogin(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void openHomeuser(){

        String mail = memail.getText().toString().trim();
        String fname = mfname.getText().toString().trim();
        String tp = types;
        Intent intent = new Intent(this, HomeUsr.class);
        intent.putExtra("f_name",fname);
        intent.putExtra("u_mail",mail);
        intent.putExtra("typ",tp);
        startActivity(intent);
    }

    public void openHomeorg(){
        String mail = memail.getText().toString().trim();
        String fname = mfname.getText().toString().trim();
        String tp = types;
        Intent intent = new Intent(this, HomeOrg.class);
        intent.putExtra("f_name",fname);
        intent.putExtra("u_mail",mail);
        intent.putExtra("typ",tp);
        startActivity(intent);
    }
}