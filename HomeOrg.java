package com.example.taski;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class HomeOrg extends AppCompatActivity {
    TextView maill1;
    SharedPreferences prf;
    Intent intent;

    String tpp, fmail;
    Button cevent, myevent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_org);

        prf = getSharedPreferences("user_details",MODE_PRIVATE);
        intent = new Intent(HomeOrg.this,MainActivity.class);

        maill1 = findViewById(R.id.maill6);
        cevent = findViewById(R.id.create);
        myevent = findViewById(R.id.events);

        Intent intent;
        intent = getIntent();

        final String dmail = intent.getStringExtra("d_mail");
        final String email1 = intent.getStringExtra("u_mail");
        final String tp = intent.getStringExtra("typ");

        maill1.setText(email1);
        tpp = tp;
        fmail = dmail;

        cevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCreate();
            }
        });

        myevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEvents();
            }
        });

    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        openLogin();
    }

    public void openLogin(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void openCreate(){
        String mail = maill1.getText().toString().trim();
        String mail1 = maill1.getText().toString().trim().replace(".","_");
        String tp = tpp;
        Intent intent = new Intent(this, Create.class);
        intent.putExtra("u_mail",mail);
        intent.putExtra("d_mail",mail1);
        intent.putExtra("typ",tp);
        startActivity(intent);
    }

    public void openEvents(){
        String mail = maill1.getText().toString().trim();
        String mail1 = maill1.getText().toString().trim().replace(".","_");
        String tp = tpp;
        Intent intent = new Intent(this, viewEvent.class);
        intent.putExtra("u_mail",mail);
        intent.putExtra("d_mail",mail1);
        intent.putExtra("typ",tp);
        startActivity(intent);
    }
}