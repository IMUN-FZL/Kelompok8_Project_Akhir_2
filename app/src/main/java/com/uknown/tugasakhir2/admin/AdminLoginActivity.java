package com.uknown.tugasakhir2.admin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.uknown.tugasakhir2.R;

public class AdminLoginActivity extends AppCompatActivity {

    FirebaseFirestore database;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_admin);

        database = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        EditText usernameInput = findViewById(R.id.userLoginAdmin);
        EditText passwordInput = findViewById(R.id.pw);

        // Mendapatkan referensi ke tombol login dari layout
        Button loginButton = findViewById(R.id.loginbutton);

        // Menambahkan onClickListener untuk menangani klik tombol login
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                database.collection("admin")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    for (QueryDocumentSnapshot doc: task.getResult()){
                                        String username = doc.get("username").toString().trim();
                                        String password = doc.get("password").toString().trim();
                                        System.out.println("dari database " +username);
                                        System.out.println("dari input" + usernameInput.getText().toString());
                                        if(usernameInput.getText().toString().equals(username) &&
                                            passwordInput.getText().toString().equals(password)){
                                            // Memulai aktivitas baru setelah login berhasil
                                            Toast.makeText(AdminLoginActivity.this, "Login Berhasil", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(AdminLoginActivity.this, halaman_admin.class);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Username/password salah", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                } else {
                                    System.out.println(task.getException().toString());
                                }
                            }
                        });
//
//                String email = usernameInput.getText().toString().trim();
//                String password = passwordInput.getText().toString().trim();
//                loginAdmin(email, password);
            }
        });
    }

    private void loginAdmin(String email, String password) {
        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Intent intent = new Intent(AdminLoginActivity.this, halaman_admin.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
