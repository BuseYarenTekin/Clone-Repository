package com.example.tekin.instagramklonfirebaseprojem;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class GaleryActivity extends AppCompatActivity {
    ImageView imageViewgalery;
    Button btn;
    ArrayList<String> userImagefromFB;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    int sayac = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galery);
        userImagefromFB = new ArrayList<String>();
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();
        imageViewgalery = findViewById(R.id.imageView2);
        btn = findViewById(R.id.buttonGoster);
        getFromDataFirebase();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sayac == userImagefromFB.size()-1){

                }
                else{
                    sayac += 1;
                    Picasso.get().load(userImagefromFB.get(sayac)).into(imageViewgalery);
                }
            }

        });

    }

    public void getFromDataFirebase() {
        DatabaseReference newReference = firebaseDatabase.getReference("Gönderiler");
        newReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    HashMap<String, String> hashMap = (HashMap<String, String>) ds.getValue();
                    userImagefromFB.add(hashMap.get("downloadurl"));
                }
                Picasso.get().load(userImagefromFB.get(0)).into(imageViewgalery);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
