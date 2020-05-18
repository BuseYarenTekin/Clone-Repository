package com.example.tekin.instagramklonfirebaseprojem;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShowActivity extends AppCompatActivity {
    ListView listView;
    PostClass adapter;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    ArrayList<String> userEmailfromFB;
    ArrayList<String> userImagefromFB;
    ArrayList<String> userCommentfromFB;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_post, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add_post) {
            Intent intent = new Intent(getApplicationContext(), UploadActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        listView = findViewById(R.id.listView);
        userEmailfromFB = new ArrayList<String>();
        userCommentfromFB = new ArrayList<String>();
        userImagefromFB = new ArrayList<String>();
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();
        adapter = new PostClass(userEmailfromFB, userCommentfromFB, userImagefromFB, this);
        listView.setAdapter(adapter);
        getFromDataFirebase();

    }
    public void getFromDataFirebase(){
        DatabaseReference newReference = firebaseDatabase.getReference("Gönderiler");
        newReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    HashMap<String, String> hashMap=(HashMap<String, String>) ds.getValue();
                    userImagefromFB.add(hashMap.get("downloadurl"));
                    userCommentfromFB.add(hashMap.get("usercomment"));
                    userEmailfromFB.add(hashMap.get("useremail"));
                    adapter.notifyDataSetChanged();//adaptör değişiklik olduğunun farkına varsın
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
