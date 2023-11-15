package com.uknown.tugasakhir2.homepage.clothing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.uknown.tugasakhir2.R;
import com.uknown.tugasakhir2.homepage.HomeActivity;

public class ClothingActivity extends AppCompatActivity {

    private RelativeLayout menContainer, womanContainer; // Membuat variable guna menampung id component relative layout.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothing);

        menContainer = findViewById(R.id.containerClothingMen); // Memanggil layout relative untuk pria menggunakan id
        womanContainer = findViewById(R.id.containerClothingWomen); // Memanggil layout relative untuk pria menggunakan id

        // Membuat listener ketika komponen diclick
        menContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mendirect ke ClothingCatergoryActivity.class
                Intent go = new Intent(ClothingActivity.this, ClothingCategoryActivity.class);
                go.putExtra("kategori", "men");
                startActivity(go);
            }
        });

        womanContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mendirect ke ClothingCatergoryActivity.class dengan membawa data berupa string
                Intent go = new Intent(ClothingActivity.this, ClothingCategoryActivity.class);
                go.putExtra("kategori", "women");
                startActivity(go);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        // Ketika tombol back diklik, maka stack sebelumnya dihilangkan (delete)
        Intent go = new Intent(ClothingActivity.this, HomeActivity.class);
        startActivity(go);
        finish();
    }
}