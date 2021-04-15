package com.example.taski;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class HomeUsr extends AppCompatActivity {

    DatePickerDialog picker;

    TextView mail;
    SharedPreferences prf;
    Intent intent;
    String typ,ml;
    EditText fnam, idnum, address, dob, pnumber, nextname, nextphone,typo,mail3 ;
    Button save;

    DatabaseReference reffer;
    FirebaseDatabase rootNode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_usr);


        prf = getSharedPreferences("user_details",MODE_PRIVATE);
        intent = new Intent(HomeUsr.this,Register.class);

        fnam = findViewById(R.id.fulln);
        idnum = findViewById(R.id.idnum);
        address = findViewById(R.id.address);
        dob = findViewById(R.id.editTextDate);
        pnumber = findViewById(R.id.phone);
        nextname = findViewById(R.id.fnameKin);
        nextphone = findViewById(R.id.phonekin);
        mail = findViewById(R.id.maill);
        typo = findViewById(R.id.typo);
        save = findViewById(R.id.save);
        mail3 = findViewById(R.id.maill4);

        dob.setInputType(InputType.TYPE_NULL);
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(HomeUsr.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                dob.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        Intent intent;
        intent = getIntent();

      final String tp = intent.getStringExtra("typ");
      final String email = intent.getStringExtra("u_mail");
      final String fname = intent.getStringExtra("f_name");


        fnam.setText(fname);
        mail.setText(email);
        mail3.setText(email);

        typo.setText(tp);

        typ = tp;
        ml = email;

       reffer= FirebaseDatabase.getInstance().getReference().child("users");

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                rootNode= FirebaseDatabase.getInstance();
                reffer=rootNode.getReference("users");

                String idn = idnum.getText().toString();
                String fn = fnam.getText().toString();
                String ty = typo.getText().toString();
                String addr = address.getText().toString();
                String db = dob.getText().toString();
                String pnum = pnumber.getText().toString();
                String nname = nextname.getText().toString();
                String nnum = nextphone.getText().toString();
                String mail = mail3.getText().toString().trim().replace(".","_");

                if(TextUtils.isEmpty(idn)){
                    idnum.setError("id number is required");
                    return;
                }
                if(TextUtils.isEmpty(addr)){
                    address.setError("Address is required");
                    return;
                }

                if(TextUtils.isEmpty(db)){
                    dob.setError("Date of birth is Required.");
                    return;
                }
                if(TextUtils.isEmpty(pnum)){
                    pnumber.setError("phone number is required");
                    return;
                }
                if(TextUtils.isEmpty(nname)){
                    nextname.setError("Name is required");
                    return;
                }

                if(TextUtils.isEmpty(nnum)){
                    nextphone.setError("number is Required.");
                    return;
                }

                UserHelperClass helperClass= new UserHelperClass (idn,fn,addr,db,pnum,nname,nnum,ty,mail);

                reffer.child(ty).child(mail).setValue(helperClass);

                Toast.makeText(getApplicationContext(),"SAVED", Toast.LENGTH_SHORT).show();

                openUser();


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


    public void openUser(){
        String mail = ml.trim();
        String tp = typ.trim();
        Intent intent = new Intent(this, User1.class);
        intent.putExtra("u_mail",mail);
        intent.putExtra("typ",tp);
        startActivity(intent);
    }
}