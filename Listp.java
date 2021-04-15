package com.example.taski;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Listp extends AppCompatActivity {
    TextView maill1;
    SharedPreferences prf;
    Intent intent;
    String tpp, fmail,cod;

    EditText fullname, idnm, addresss, myphone, namenext, phonenext, snum, seat, timein;


    DatabaseReference reffer, ref;
    FirebaseDatabase rootNode;


    Button home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listp);

        prf = getSharedPreferences("user_details",MODE_PRIVATE);
        intent = new Intent(Listp.this,viewEvent1.class);


        home = findViewById(R.id.homee4);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHomeorg();
            }
        });
        maill1 = findViewById(R.id.maill13);
        maill1 = findViewById(R.id.maill11);
        fullname = findViewById(R.id.fullname3);
        idnm = findViewById(R.id.idnumber3);
        addresss = findViewById(R.id.addresss3);
        myphone = findViewById(R.id.myphone3);
        namenext = findViewById(R.id.namenext3);
        phonenext = findViewById(R.id.phonenext3);
        snum = findViewById(R.id.seat);
        timein = findViewById(R.id.timinn1);
        seat = findViewById(R.id.seat3);



        Intent intent;
        intent = getIntent();

        final String dmail = intent.getStringExtra("d_mail");
        final String email1 = intent.getStringExtra("u_mail");
        final String tp = intent.getStringExtra("typ");
        final String cd = intent.getStringExtra("cod");

        maill1.setText(email1);
        tpp = tp;
        cod = cd;

        reffer = FirebaseDatabase.getInstance().getReference().child("attendance").child(dmail).child(cd);

        reffer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                rootNode = FirebaseDatabase.getInstance();
                reffer = rootNode.getReference("events");

                String fnn = dataSnapshot.child("fname").getValue().toString();
                String des = dataSnapshot.child("id").getValue().toString();
                String da = dataSnapshot.child("addrs").getValue().toString();
                String st = dataSnapshot.child("pnum").getValue().toString();
                String et = dataSnapshot.child("nextn").getValue().toString();
                String loc = dataSnapshot.child("nextp").getValue().toString();
                String stt = dataSnapshot.child("seat").getValue().toString();
                String tim = dataSnapshot.child("time").getValue().toString();

                fullname.setText(fnn);
                idnm.setText(des);
                addresss.setText(loc);
                myphone.setText(da);
                namenext.setText(st);
                phonenext.setText(et);
                seat.setText(stt);
                timein.setText(tim);

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