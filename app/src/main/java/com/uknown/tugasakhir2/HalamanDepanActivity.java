package com.uknown.tugasakhir2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.uknown.tugasakhir2.admin.AdminLoginActivity;
import com.uknown.tugasakhir2.staff.StaffLoginActivity;
import com.uknown.tugasakhir2.user.UserLoginActivity;
import com.uknown.tugasakhir2.user.UserRegisterActivity;

public class HalamanDepanActivity extends AppCompatActivity {

    Button register, login;
    TextView AdminLogin, StaffLogin, About;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.halaman_depan);

        register = (Button) findViewById(R.id.btn_register);
        login =  (Button) findViewById(R.id.btn_Login);
        AdminLogin = (TextView) findViewById(R.id.tvAdminLogin);
        StaffLogin = (TextView) findViewById(R.id.tv_stafLogin);
        About = (TextView) findViewById(R.id.tv_about);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HalamanDepanActivity.this, UserLoginActivity.class);
                startActivity(intent);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HalamanDepanActivity.this, UserRegisterActivity.class);
                startActivity(intent);
            }
        });

        AdminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HalamanDepanActivity.this, AdminLoginActivity.class));
            }
        });

        StaffLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HalamanDepanActivity.this, StaffLoginActivity.class));
            }
        });

        About.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HalamanDepanActivity.this,AboutActivity.class));
            }
        });
    }
    public void onBackPressed(){
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }
}
