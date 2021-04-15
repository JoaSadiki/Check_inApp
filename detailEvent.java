package com.example.taski;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class detailEvent extends AppCompatActivity {
    TextView maill1;
    SharedPreferences prf;
    Intent intent;
    String tpp, fmail,cod;

    DatabaseReference reffer;
    FirebaseDatabase rootNode;
    Button check,home, list;
    EditText ename, edesc, edate, loca, stime, etime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_event);

        prf = getSharedPreferences("user_details",MODE_PRIVATE);
        intent = new Intent(detailEvent.this,viewEvent.class);

        list = findViewById(R.id.buttonlist);
        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        home = findViewById(R.id.homee2);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHomeorg();
            }
        });
        maill1 = findViewById(R.id.maill9);
        ename = findViewById(R.id.eename);
        edesc = findViewById(R.id.eedesc);
        edate = findViewById(R.id.eedate);
        loca = findViewById(R.id.eeloc);
        stime = findViewById(R.id.eestime);
        etime = findViewById(R.id.eetime);
        check = findViewById(R.id.bcheck);

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkEvents();
            }
        });

        Intent intent;
        intent = getIntent();

        final String dmail = intent.getStringExtra("d_mail");
        final String email1 = intent.getStringExtra("u_mail");
        final String tp = intent.getStringExtra("typ");
        final String cd = intent.getStringExtra("cod");

        maill1.setText(email1);
        tpp = tp;
        cod = cd;

        reffer = FirebaseDatabase.getInstance().getReference().child("events").child(dmail).child(cd);

        reffer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                rootNode = FirebaseDatabase.getInstance();
                reffer = rootNode.getReference("events");

                String fnn = dataSnapshot.child("fname").getValue().toString();
                String des = dataSnapshot.child("addrs").getValue().toString();
                String da = dataSnapshot.child("dob").getValue().toString();
                String st = dataSnapshot.child("nextn").getValue().toString();
                String et = dataSnapshot.child("nextp").getValue().toString();
                String loc = dataSnapshot.child("pnum").getValue().toString();

                ename.setText(fnn);
                edesc.setText(des);
                loca.setText(loc);
                edate.setText(da);
                stime.setText(st);
                etime.setText(et);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(getApplicationContext(),"event does not exist", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void checkEvents() {
        String mail = maill1.getText().toString().trim();
        String mail1 = maill1.getText().toString().trim().replace(".","_");
        String tp = tpp;
        String cd = cod;
        Intent intent = new Intent(this, Qrscan.class);
        intent.putExtra("u_mail",mail);
        intent.putExtra("d_mail",mail1);
        intent.putExtra("typ",tp);
        intent.putExtra("cod",cd);
        startActivity(intent);

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

    public void openHomeorg(){
        String mail = maill1.getText().toString().trim();
        String mail1 = maill1.getText().toString().trim().replace(".","_");
        String tp = tpp;
        Intent intent = new Intent(this, HomeOrg.class);
        intent.putExtra("u_mail",mail);
        intent.putExtra("d_mail",mail1);
        intent.putExtra("typ",tpp);
        startActivity(intent);
    }
    public void viewEvent(){
        String mail = maill1.getText().toString().trim();
        String mail1 = maill1.getText().toString().trim().replace(".","_");
        String tp = tpp;
        Intent intent = new Intent(this, viewEvent1.class);
        intent.putExtra("u_mail",mail);
        intent.putExtra("d_mail",mail1);
        intent.putExtra("typ",tp);
        startActivity(intent);
    }
}