package com.uknown.tugasakhir2.staff;

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
import com.uknown.tugasakhir2.user.DataUser;

public class StaffLoginActivity extends AppCompatActivity {


    EditText EmailAddress, Password;
    Button btnLoginStaff;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staff_login);

        EmailAddress = findViewById(R.id.staffEmail);
        Password = findViewById(R.id.staffPw);
        btnLoginStaff = findViewById(R.id.btn_loginStaff);

        mAuth = FirebaseAuth.getInstance();

        btnLoginStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailTxt = EmailAddress.getText().toString().trim();
                String passwordTxt = Password.getText().toString().trim();

                if (!Patterns.EMAIL_ADDRESS.matcher(emailTxt).matches()) {
                    EmailAddress.setError("Invalid Email");
                    EmailAddress.requestFocus();
                    return;
                }

                if (emailTxt.isEmpty() || passwordTxt.isEmpty()) {
                    Toast.makeText(StaffLoginActivity.this, "Please enter your Email or password", Toast.LENGTH_SHORT).show();
                }

                System.out.println(emailTxt + "\n" + passwordTxt);
                mAuth.signInWithEmailAndPassword(emailTxt, passwordTxt).addOnCompleteListener(StaffLoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            String email = firebaseUser.getEmail();
                            String uId = firebaseUser.getUid();
                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Staff");
                            databaseReference.child(uId).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    boolean isExcist = snapshot.exists();

                                    if (isExcist) {
                                        DataUser dataUser = snapshot.getValue(DataUser.class);
                                        String statusUser = dataUser.getRole();

                                        if (statusUser.equals("staff")) {
                                            Toast.makeText(StaffLoginActivity.this, "Succesfully logged in", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(StaffLoginActivity.this, StaffPageActivity.class));
                                            finish();
                                        } else {
                                            Toast.makeText(StaffLoginActivity.this, "Not User", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(StaffLoginActivity.this, "Wrong email or password", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }
                });
            }
        });
    }
}