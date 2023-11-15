package com.uknown.tugasakhir2.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.uknown.tugasakhir2.R;

public class UserRegisterActivity extends AppCompatActivity {

    EditText Name;
    EditText Email;
    EditText PhoneNumber;
    EditText Password, password2;
    Button Registerbtn;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_register);

        Name = findViewById(R.id.etName);
        Email = findViewById(R.id.etEmail);
        Password = findViewById(R.id.etPassword);
        Registerbtn = findViewById(R.id.btnRegister);
        password2 = findViewById(R.id.etPassword2);

        mAuth = FirebaseAuth.getInstance();

        Registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
            }
        });
    }


    private void createUser() {
        String username = Name.getText().toString().trim();
        String emailTxt = Email.getText().toString().trim();
        String passTxt = Password.getText().toString().trim();

        if (username.isEmpty() || emailTxt.isEmpty() || passTxt.isEmpty()) {
            Toast.makeText(UserRegisterActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        } else if (!passTxt.equals(password2.getText().toString())){
            Toast.makeText(UserRegisterActivity.this, "Password tidak identik", Toast.LENGTH_SHORT).show();
        }

        mAuth.createUserWithEmailAndPassword(emailTxt, passTxt)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            DataUser dataUser = new DataUser(username, emailTxt, passTxt, "user");
                            String uId = task.getResult().getUser().getUid();
                            FirebaseDatabase.getInstance().getReference("User").child(uId).setValue(dataUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(UserRegisterActivity.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(UserRegisterActivity.this, UserLoginActivity.class));
                                        finish();
                                    } else {
                                        Toast.makeText(UserRegisterActivity.this, "Failed to register", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });


                        }
                    }
                });
    }
}
