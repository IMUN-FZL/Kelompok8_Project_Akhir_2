package com.uknown.tugasakhir2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final int WAKTU_TAMPIL_SPLASH = 3000; // 3 detik

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Gunakan Handler untuk menunda perpindahan ke konten utama
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Mulai aktivitas utama
                Intent i = new Intent(MainActivity.this, HalamanDepanActivity.class);
                startActivity(i);

                // Tutup
                finish();
            }
        }, WAKTU_TAMPIL_SPLASH);
    }
}
