package com.example.mike9.seg2105_project;

import android.app.Service;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class WelcomeScreen extends AppCompatActivity {

    //Firebase things
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mRef;

    private String userID;
    private String serviceName, serviceRate;
    private ListView serviceList;
    ArrayList<String> array;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        //Carries over the user sign in
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference();
        userID = user.getUid();

        serviceList = findViewById(R.id.service_list);
        array = new ArrayList<>();

        mRef.child("Services").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void onClickAddService(View view){
        Intent nextPage = new Intent(getApplicationContext(), AddService.class);
        startActivity(nextPage);
    }
    //Put code to view active services here
    private void showData (DataSnapshot dataSnapshot){
        array.clear();
        ServiceInformation sInfo = new ServiceInformation();
        for(DataSnapshot ds : dataSnapshot.getChildren()){
            sInfo.setName(ds.getKey());
            sInfo.setRate(ds.getValue().toString());

                array.add(sInfo.toString());

        }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, array);
        serviceList.setAdapter(adapter);
    }

}
