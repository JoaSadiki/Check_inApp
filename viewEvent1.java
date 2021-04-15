package com.example.taski;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class viewEvent1 extends AppCompatActivity implements AdapterView.OnItemClickListener {

    TextView maill1;
    SharedPreferences prf;
    Intent intent;
    String tpp, fmail,cod;

    DatabaseReference reference;
    FirebaseDatabase rootNode;

    // ListView dataListView ;

    // creating variables for our list view.
    private ListView coursesLV;

    // creating a new array list.
    ArrayList<String> coursesArrayList;

   /* ArrayList<String> listItems = new ArrayList<String>();
    ArrayList<String> listKeys = new ArrayList<String>();
    ArrayAdapter<String> adapter;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event1);

        prf = getSharedPreferences("user_details",MODE_PRIVATE);
        intent = new Intent(viewEvent1.this,HomeOrg.class);
        maill1 = findViewById(R.id.maill12);
        Intent intent;
        intent = getIntent();
        final String dmail = intent.getStringExtra("d_mail");
        final String email1 = intent.getStringExtra("u_mail");
        final String tp = intent.getStringExtra("typ");
        maill1.setText(email1);
        tpp = tp;
        fmail = dmail;

        // initializing variables for listviews.
        coursesLV = findViewById(R.id.idLVCourses);

        // initializing our array list
        coursesArrayList = new ArrayList<String>();

        // calling a method to get data from
        // Firebase and set data to list view
        //  initializeListView();

        // creating a new array adapter for our list view.
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, coursesArrayList);

        String fml = fmail;
        // below line is used for getting reference
        // of our Firebase Database.
        reference = FirebaseDatabase.getInstance().getReference().child("attendance").child(fml);

        // in below line we are calling method for add child event
        // listener to get the child of our database.
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // this method is called when new child is added to
                // our data base and after adding new child
                // we are adding that item inside our array list and
                // notifying our adapter that the data in adapter is changed.
                coursesArrayList.add(snapshot.child("email").getValue(String.class));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // this method is called when the new child is added.
                // when the new child is added to our list we will be
                // notifying our adapter that data has changed.
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                // below method is called when we remove a child from our database.
                // inside this method we are removing the child from our array list
                // by comparing with it's value.
                // after removing the data we are notifying our adapter that the
                // data has been changed.
                coursesArrayList.remove(snapshot.getValue(String.class));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // this method is called when we move our
                // child in our database.
                // in our code we are note moving any child.
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // this method is called when we get any
                // error from Firebase with error.
            }
        });
        // below line is used for setting
        // an adapter to our list view.
        coursesLV.setAdapter(adapter);
        coursesLV.setOnItemClickListener(this);
    }

    private void initializeListView() {
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String eco = parent.getItemAtPosition(position).toString();
        cod = eco;
        openEvents();
    }

    private void openEvents() {
        String mail = maill1.getText().toString().trim();
        String mail1 = maill1.getText().toString().trim().replace(".","_");
        String tp = tpp;
        String cd = cod;
        Intent intent = new Intent(this, Listp.class);
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



}