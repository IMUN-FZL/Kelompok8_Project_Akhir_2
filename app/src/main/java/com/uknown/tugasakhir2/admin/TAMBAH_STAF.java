package com.uknown.tugasakhir2.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.uknown.tugasakhir2.R;
import com.uknown.tugasakhir2.user.DataUser;


public class TAMBAH_STAF extends AppCompatActivity {

    private EditText email, nama, password, confirmpassword;
    private Button regist;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tambah_staff);

        email = findViewById(R.id.etEmail);
        nama = findViewById(R.id.etUsername);
        password = findViewById(R.id.etPasswordStafRegister);
        confirmpassword = findViewById(R.id.confirmPassword);
        regist = findViewById(R.id.btn_registerStaff);

        mAuth = FirebaseAuth.getInstance();
        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createStaff();
            }
        });

    }

    private void createStaff() {
        String username = nama.getText().toString().trim();
        String emailTxt = email.getText().toString().trim();
        String passTxt = password.getText().toString().trim();
        String passConfrim = confirmpassword.getText().toString().trim();

        if (username.isEmpty() || emailTxt.isEmpty() || passConfrim.isEmpty() || passTxt.isEmpty()) {
            Toast.makeText(TAMBAH_STAF.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        } else if (!passTxt.equals(passConfrim)){
            Toast.makeText(TAMBAH_STAF.this, "Password tidak identik", Toast.LENGTH_SHORT).show();
            return;
        } else if(passTxt.length() <= 6 && passConfrim.length() <= 6){
            Toast.makeText(TAMBAH_STAF.this, "Password setidaknya 6 kata", Toast.LENGTH_SHORT).show();
        }

        mAuth.createUserWithEmailAndPassword(emailTxt, passTxt)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            DataUser dataUser = new DataUser(username, emailTxt, passTxt, "staff");
                            String uId = task.getResult().getUser().getUid();
                            FirebaseDatabase.getInstance().getReference("Staff").child(uId).setValue(dataUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(TAMBAH_STAF.this, "Staff Registered Successfully", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(TAMBAH_STAF.this, halaman_admin.class));
                                        finish();
                                    } else {
                                        Toast.makeText(TAMBAH_STAF.this, "Failed to register", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });


                        }
                    }
                });
    }
}

