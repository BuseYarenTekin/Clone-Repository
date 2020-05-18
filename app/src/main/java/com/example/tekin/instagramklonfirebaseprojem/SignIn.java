package com.example.tekin.instagramklonfirebaseprojem;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignIn extends AppCompatActivity {
    private FirebaseAuth mAuth;
    ImageView img;
    EditText etMail;
    EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mAuth = FirebaseAuth.getInstance();
        img = findViewById(R.id.iv1);
        etMail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        //güncel kullanıcı çekme
        FirebaseUser user = mAuth.getCurrentUser();
//        if (user != null){
//            Intent intent=new Intent(getApplicationContext(),ShowActivity.class);
//            startActivity(intent);
//        }

    }
    public void signIn(View view) {
        mAuth.signInWithEmailAndPassword(etMail.getText().toString(), etPassword.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(getApplicationContext(), GaleryActivity.class);
                            startActivity(intent);
                        }
                    }
                }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignIn.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
    public void signUp(View view) {

        mAuth.createUserWithEmailAndPassword(etMail.getText().toString(), etPassword.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignIn.this, "Kullanıcı oluşturuldu.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), ShowActivity.class);
                            startActivity(intent);
                        }
                    }
                }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //kullanıcıya hata mesajı göster
                Toast.makeText(SignIn.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}
