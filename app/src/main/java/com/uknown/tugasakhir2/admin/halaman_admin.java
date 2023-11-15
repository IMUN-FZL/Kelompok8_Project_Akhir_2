package com.uknown.tugasakhir2.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.uknown.tugasakhir2.R;

public class halaman_admin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.halaman_admin);

        Button staf = findViewById(R.id.add_staff);
        Button stok = findViewById(R.id.add_stok);

        // Menambahkan onClickListener untuk menangani klik tombol staf
        staf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Memulai aktivitas baru setelah klik tombol staf
                Intent intent1 = new Intent(halaman_admin.this, TAMBAH_STAF.class);
                startActivity(intent1);
            }
        });

        // Menambahkan onClickListener untuk menangani klik tombol stok
        stok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Memulai aktivitas baru setelah klik tombol stok
                Intent intent2 = new Intent(halaman_admin.this, TAMBAH_STOK.class);
                startActivity(intent2);
            }
        });
    }
}
