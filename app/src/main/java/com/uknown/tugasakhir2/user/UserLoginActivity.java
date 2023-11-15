package com.uknown.tugasakhir2.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uknown.tugasakhir2.R;
import com.uknown.tugasakhir2.homepage.HomeActivity;

public class UserLoginActivity extends AppCompatActivity {

    EditText InputEmail, InputPass;
    Button loginUser;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login);

        InputEmail = findViewById(R.id.etEmail);
        InputPass = findViewById(R.id.etPw);
        loginUser = findViewById(R.id.btnLogin);

        mAuth = FirebaseAuth.getInstance();

        loginUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String EmailAddress = InputEmail.getText().toString().trim();
                String passwordTxt = InputPass.getText().toString().trim();

                if (EmailAddress.isEmpty() || passwordTxt.isEmpty()) {
                    Toast.makeText(UserLoginActivity.this, "Mohon masukkan Email dan password Anda", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(EmailAddress).matches()){
                    InputEmail.setError("Email tidak valid");
                    InputEmail.requestFocus();
                    return;
                }

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User");
                databaseReference.orderByChild("email").equalTo(EmailAddress)
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    mAuth.signInWithEmailAndPassword(EmailAddress, passwordTxt)
                                            .addOnCompleteListener(UserLoginActivity.this, new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                    if (task.isSuccessful()) {
                                                        FirebaseUser firebaseUser = mAuth.getCurrentUser();
                                                        String uId = firebaseUser.getUid();
                                                        DataUser dataUser = snapshot.child(uId).getValue(DataUser.class);
                                                        String statusUser = dataUser.getRole();

                                                        if ("user".equals(statusUser)) {
                                                            Toast.makeText(UserLoginActivity.this, "Berhasil login", Toast.LENGTH_SHORT).show();
                                                            startActivity(new Intent(UserLoginActivity.this, HomeActivity.class));
                                                            finish();
                                                        } else {
                                                            Toast.makeText(UserLoginActivity.this, "Bukan pengguna reguler", Toast.LENGTH_SHORT).show();
                                                        }
                                                    } else {
                                                        Toast.makeText(UserLoginActivity.this, "Autentikasi gagal", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                } else {
                                    Toast.makeText(UserLoginActivity.this, "Email atau password salah", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                // Handle error
                            }
                        });
            }
        });
    }
}