package com.example.taski;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class Create extends AppCompatActivity {

    TextView maill1;
    SharedPreferences prf;
    Intent intent;
    DatePickerDialog picker;

    TimePickerDialog pickers;

    String tpp, fmail;
    Button addE, viewE, home;
    EditText ecode, ename, edesc, edate, eloc, start, end;

    DatabaseReference reffer;
    FirebaseDatabase rootNode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        prf = getSharedPreferences("user_details",MODE_PRIVATE);
        intent = new Intent(Create.this,HomeOrg.class);

        ecode = findViewById(R.id.Ecode);
        ename = findViewById(R.id.Ename);
        edesc = findViewById(R.id.Edesc);
        home = findViewById(R.id.homee);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHomeorg();
            }
        });
        eloc = findViewById(R.id.Elocation);
        addE = findViewById(R.id.addE);
        viewE = findViewById(R.id.viewE);
        maill1 = findViewById(R.id.maill7);
        start = findViewById(R.id.starts);
        start.setInputType(InputType.TYPE_NULL);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog
                pickers = new TimePickerDialog(Create.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                start.setText(sHour + ":" + sMinute);
                            }
                        }, hour, minutes, true);
                pickers.show();
            }
        });

        end = findViewById(R.id.ends);
        end.setInputType(InputType.TYPE_NULL);
        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog
                pickers = new TimePickerDialog(Create.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                end.setText(sHour + ":" + sMinute);
                            }
                        }, hour, minutes, true);
                pickers.show();
            }
        });

        edate = findViewById(R.id.Edate);
        edate.setInputType(InputType.TYPE_NULL);
        edate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(Create.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                edate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        Intent intent;
        intent = getIntent();

        final String dmail = intent.getStringExtra("d_mail");
        final String email1 = intent.getStringExtra("u_mail");
        final String tp = intent.getStringExtra("typ");

        maill1.setText(email1);
        tpp = tp;
        fmail = dmail;



        viewE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewEvent();
            }
        });

        addE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reffer= FirebaseDatabase.getInstance().getReference().child("events");
                rootNode= FirebaseDatabase.getInstance();
                reffer=rootNode.getReference("events");

                String ecod = ecode.getText().toString();
                String enam = ename.getText().toString();
                String edes = edesc.getText().toString();
                String edat = edate.getText().toString();
                String el = eloc.getText().toString();
                String srt = start.getText().toString();
                String eds = end.getText().toString();
                String mail = fmail.trim();

                if(TextUtils.isEmpty(ecod)){
                    ecode.setError("event id is required");
                    return;
                }
                if(TextUtils.isEmpty(enam)){
                    ename.setError("name is required");
                    return;
                }

                if(TextUtils.isEmpty(edes)){
                    edesc.setError("description is Required.");
                    return;
                }
                if(TextUtils.isEmpty(edat)){
                    edate.setError("date is required");
                    return;
                }
                if(TextUtils.isEmpty(el)){
                    eloc.setError("location is required");
                    return;
                }

                UserHelperClassEvent helperClass= new UserHelperClassEvent (mail,enam,edes,edat,el,srt,eds,ecod);

                reffer.child(mail).child(ecod).setValue(helperClass);

                Toast.makeText(getApplicationContext(),"" +
                        "Event Created", Toast.LENGTH_SHORT).show();

                openCreate();


            }
        });


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

    public void viewEvent(){
        String mail = maill1.getText().toString().trim();
        String mail1 = maill1.getText().toString().trim().replace(".","_");
        String tp = tpp;
        Intent intent = new Intent(this, viewEvent.class);
        intent.putExtra("u_mail",mail);
        intent.putExtra("d_mail",mail1);
        intent.putExtra("typ",tp);
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
}