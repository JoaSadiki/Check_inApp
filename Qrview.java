package com.example.taski;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.WriterException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import androidmads.library.qrgenearator.QRGSaver;

public class Qrview extends AppCompatActivity {
    private String savePath = Environment.getExternalStorageDirectory().getPath() + "/QRCode/";
    private Bitmap bitmap;
    private QRGEncoder qrgEncoder;
    private AppCompatActivity activity;


    EditText qrvalue;
    Button generateBtn,savebtn;
    ImageView qrImage;

    TextView maill1;
    SharedPreferences prf;
    Intent intent;
    String mill, tpp;
    DatabaseReference reffer;
    FirebaseDatabase rootNode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrview);

        prf = getSharedPreferences("user_details",MODE_PRIVATE);
        intent = new Intent(Qrview.this,User1.class);

        maill1 = findViewById(R.id.maill5);
        qrvalue = findViewById(R.id.qrInput);
        generateBtn = findViewById(R.id.generateBtn);
        savebtn = findViewById(R.id.saveBtn);
        qrImage = findViewById(R.id.qrPlaceHolder);


        Intent intent;
        intent = getIntent();

        final String dmail = intent.getStringExtra("d_mail");
        final String email1 = intent.getStringExtra("u_mail");
        final String tp = intent.getStringExtra("typ");

        qrvalue.setText(dmail);
        maill1.setText(email1);

        generateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = qrvalue.getText().toString();
                if(data.isEmpty()){
                    qrvalue.setError("Value Required.");
                }else {
                    QRGEncoder qrgEncoder = new QRGEncoder(data,null, QRGContents.Type.TEXT,500);
                    Bitmap qrBits = null;
                    try {
                        qrBits = qrgEncoder.encodeAsBitmap();
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                    ;
                    qrImage.setImageBitmap(qrBits);
                }
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
}
