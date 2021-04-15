package com.example.taski;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class Qrscan extends AppCompatActivity {

    TextView maill1;
    SharedPreferences prf;
    Intent intent;
    String tpp, fmail,cod;

    DatabaseReference reffer;
    FirebaseDatabase rootNode;

    CodeScanner codeScanner;
    CodeScannerView scannView;
    TextView resultData;
    String rmail;

    Button buttonch,home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrscan);


        home = findViewById(R.id.homee3);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHomeorg();
            }
        });

        scannView = findViewById(R.id.scannerView);
        codeScanner = new CodeScanner(this,scannView);
        resultData = findViewById(R.id.resultsOfQr);
        buttonch = findViewById(R.id.buttonch);

        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        resultData.setText(result.getText());
                        rmail= resultData.getText().toString();
                    }
                });

            }
        });


        scannView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codeScanner.startPreview();
            }
        });


        prf = getSharedPreferences("user_details",MODE_PRIVATE);
        intent = new Intent(Qrscan.this,detailEvent.class);

        maill1 = findViewById(R.id.maill10);


        buttonch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkin();
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




    }

    @Override
    protected void onResume() {
        super.onResume();
        requestForCamera();

    }

    public void requestForCamera() {
        Dexter.withActivity(this).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse response) {
                codeScanner.startPreview();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse response) {
                Toast.makeText(Qrscan.this, "Camera Permission is Required.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                token.continuePermissionRequest();

            }
        }).check();
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
    public void checkin(){
        String mail = maill1.getText().toString().trim();
        String mail1 = maill1.getText().toString().trim().replace(".","_");
        String tp = tpp;
        String cd = cod;
        String pmail = rmail;
        Intent intent = new Intent(this, Checkin.class);
        intent.putExtra("u_mail",mail);
        intent.putExtra("d_mail",mail1);
        intent.putExtra("typ",tp);
        intent.putExtra("cod",cd);
        intent.putExtra("pmail",pmail);
        startActivity(intent);
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
}