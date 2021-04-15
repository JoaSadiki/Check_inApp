package com.example.taski;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Checkin extends AppCompatActivity {
    EditText fullname, idnm, addresss, myphone, namenext, phonenext, snum;

    TextView maill1, timein;
    SharedPreferences prf;
    Intent intent;
    String tpp, fmail,cod;

    DatabaseReference reffer, ref;
    FirebaseDatabase rootNode;

    CodeScanner codeScanner;
    CodeScannerView scannView;
    TextView resultData;
    String rmail;

    Button buttonsave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkin);

        prf = getSharedPreferences("user_details",MODE_PRIVATE);
        intent = new Intent(Checkin.this,Qrscan.class);

        maill1 = findViewById(R.id.maill11);
        fullname = findViewById(R.id.fullname3);
        idnm = findViewById(R.id.idnumber3);
        addresss = findViewById(R.id.addresss3);
        myphone = findViewById(R.id.myphone3);
        namenext = findViewById(R.id.namenext3);
        phonenext = findViewById(R.id.phonenext3);
        snum = findViewById(R.id.seat);
        buttonsave = findViewById(R.id.buttonsave);
        timein = findViewById(R.id.timeinn);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
        String currentDateandTime = sdf.format(new Date());
        timein.setText(currentDateandTime);

        Intent intent;
        intent = getIntent();

        final String dmail = intent.getStringExtra("d_mail");
        final String email1 = intent.getStringExtra("u_mail");
        final String tp = intent.getStringExtra("typ");
        final String cd = intent.getStringExtra("cod");
        final String rm = intent.getStringExtra("pmail");
        maill1.setText(email1);
        tpp = tp;
        cod = cd;
        rmail = rm;
        fmail = dmail;

        reffer = FirebaseDatabase.getInstance().getReference().child("users").child("participant").child(rm);

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

                ref= FirebaseDatabase.getInstance().getReference().child("attendance");

        buttonsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                rootNode= FirebaseDatabase.getInstance();
                ref=rootNode.getReference("attendance");

                String idn = idnm.getText().toString();
                String fn = fullname.getText().toString();
                String addr = addresss.getText().toString();
                String pnum = myphone.getText().toString();
                String nname = namenext.getText().toString();
                String nnum = phonenext.getText().toString();
                String st = snum.getText().toString();
                String timin = timein.getText().toString();
                String mail = rmail.trim();
                String coo = cod.trim();
                String org = fmail.trim();



                if(TextUtils.isEmpty(st)){
                    snum.setError("seat is Required.");
                    return;
                }

                UserHelperClassp helperClass= new UserHelperClassp (idn,fn,addr,pnum,nname,nnum,mail,st,timin,coo);

                ref.child(org).child(coo).child(mail).setValue(helperClass);

                Toast.makeText(getApplicationContext(),"Check In Successful", Toast.LENGTH_SHORT).show();

                openScan();


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
    public void openScan(){
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
}