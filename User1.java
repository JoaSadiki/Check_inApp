package com.example.taski;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class User1 extends AppCompatActivity {


    TextView maill1;
    SharedPreferences prf;
    Intent intent;

    String tpp, fmail;
    Button qrbutton, profile, history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user1);

        prf = getSharedPreferences("user_details",MODE_PRIVATE);
        intent = new Intent(User1.this,HomeUsr.class);

        maill1 = findViewById(R.id.maill3);
        qrbutton = findViewById(R.id.qrDisplay);
        profile = findViewById(R.id.profileView);
        history = findViewById(R.id.historyDisplay);


        Intent intent;
        intent = getIntent();

      final String dmail = intent.getStringExtra("d_mail");
      final String email1 = intent.getStringExtra("u_mail");
        final String tp = intent.getStringExtra("typ");

        maill1.setText(email1);
        tpp = tp;
        fmail = dmail;

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openProfile();
            }
        });

        qrbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openQr();
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
    public void openProfile(){
        String mail = maill1.getText().toString().trim();
        String mail1 = fmail;
        String tp = tpp;
        Intent intent = new Intent(this, MyProfile.class);
        intent.putExtra("u_mail",mail);
        intent.putExtra("d_mail",mail1);
        intent.putExtra("typ",tp);
        startActivity(intent);
    }

    public void openQr(){
        String mail = maill1.getText().toString().trim();
        String mail1 = fmail;
        String tp = tpp;
        Intent intent = new Intent(this, Qrview.class);
        intent.putExtra("u_mail",mail);
        intent.putExtra("d_mail",mail1);
        intent.putExtra("typ",tp);
        startActivity(intent);
    }
}