package com.example.taski;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
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

public class MyProfile extends AppCompatActivity {

    EditText fullname, idnm, addresss, myphone, namenext, phonenext;
    TextView maill1;
    SharedPreferences prf;
    Intent intent;
    String mill, tpp;

    DatabaseReference reffer;
    FirebaseDatabase rootNode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        prf = getSharedPreferences("user_details",MODE_PRIVATE);
        intent = new Intent(MyProfile.this,User1.class);

        maill1 = findViewById(R.id.maill2);
        fullname = findViewById(R.id.fullname);
        idnm = findViewById(R.id.idnumber);
        addresss = findViewById(R.id.addresss);
        myphone = findViewById(R.id.myphone);
        namenext = findViewById(R.id.namenext);
        phonenext = findViewById(R.id.phonenext);


        Intent intent;
        intent = getIntent();

        final String dmail = intent.getStringExtra("d_mail");
        final String email1 = intent.getStringExtra("u_mail");
        final String tp = intent.getStringExtra("typ");

        //mill = dmail;

        maill1.setText(email1);
        tpp = tp;

        reffer = FirebaseDatabase.getInstance().getReference().child("users").child(tp).child(dmail);

        reffer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                rootNode = FirebaseDatabase.getInstance();
                reffer = rootNode.getReference("users");

                String fnn = dataSnapshot.child("fname").getValue().toString();
                String idnumb = dataSnapshot.child("id").getValue().toString();
                String addres = dataSnapshot.child("addrs").getValue().toString();
                String mphne = dataSnapshot.child("pnum").getValue().toString();
                String nextn = dataSnapshot.child("nextn").getValue().toString();
                String nextp = dataSnapshot.child("nextp").getValue().toString();

                fullname.setText(fnn);
                idnm.setText(idnumb);
                addresss.setText(addres);
                myphone.setText(mphne);
                namenext.setText(nextn);
                phonenext.setText(nextp);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(getApplicationContext(),"profile does not exist", Toast.LENGTH_SHORT).show();
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

    public void openBack(){
        String mail = maill1.getText().toString().trim();
        String mail1 = maill1.getText().toString().trim().replace(".","_");
        String tp = tpp;
        Intent intent = new Intent(this, User1.class);
        intent.putExtra("u_mail",mail);
        intent.putExtra("d_mail",mail1);
        intent.putExtra("typ",tp);
        startActivity(intent);
    }

}