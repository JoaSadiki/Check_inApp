package com.example.taski;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.EventListener;

public class MainActivity extends AppCompatActivity {

    RadioGroup radioGroup;
    String types;
    TextView mail;
    SharedPreferences prf;
    Intent intent;
    String typ,ml;

    EditText email, password;
    FirebaseAuth fAuth;

    DatabaseReference reffer;
    FirebaseDatabase rootNode;

    Button login_b, register_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        login_b = findViewById(R.id.login);
        register_btn = findViewById(R.id.register);
        email= findViewById(R.id.emailA);
        password= findViewById(R.id.pass);
        radioGroup = findViewById(R.id.mRadioGroup1);




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



        final String mail1= email.getText().toString().trim().replace(".","_");

       /* String tp = types;
        ml= mail1;

            reffer = FirebaseDatabase.getInstance().getReference().child("users").child(tp).child(mail1);

        reffer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                rootNode = FirebaseDatabase.getInstance();
                reffer = rootNode.getReference("users");

                final String ttle = dataSnapshot.child("title").getValue().toString();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(getApplicationContext(),"profile does not exist", Toast.LENGTH_SHORT).show();
            }
        });

       */



        fAuth = FirebaseAuth.getInstance();



        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegister();
            }
        });

        login_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail= email.getText().toString().trim();
                final String tp= types.trim();
                String code= password.getText().toString().trim();

                if(TextUtils.isEmpty(mail)){
                    email.setError("Email is required");
                    return;
                }
                if(TextUtils.isEmpty(code)){
                    password.setError("Password is required");
                    return;
                }
                if(code.length() < 5){
                    password.setError("Password is too short");
                    return;
                }

                fAuth.signInWithEmailAndPassword(mail,code).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();

                            if (tp=="participant"){
                                openHomeuser1();}
                            else {openHomeorg();}
                        }else{
                            Toast.makeText(MainActivity.this, "wrong credentials, Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }


    public void openRegister(){
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

    public void openHomeuser1(){
        String mail = email.getText().toString().trim();
        String mail1 = email.getText().toString().trim().replace(".","_");
        String tp = types;
        Intent intent = new Intent(this, User1.class);
        intent.putExtra("u_mail",mail);
        intent.putExtra("d_mail",mail1);
        intent.putExtra("typ",tp);
        startActivity(intent);
    }

    public void openHomeorg(){
        String mail = email.getText().toString().trim();
        String mail1 = email.getText().toString().trim().replace(".","_");
        String tp = types;
        Intent intent = new Intent(this, HomeOrg.class);
        intent.putExtra("u_mail",mail);
        intent.putExtra("d_mail",mail1);
        intent.putExtra("typ",tp);
        startActivity(intent);
    }
}