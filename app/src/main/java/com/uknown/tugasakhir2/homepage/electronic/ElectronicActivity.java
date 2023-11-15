package com.uknown.tugasakhir2.homepage.electronic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.uknown.tugasakhir2.R;
import com.uknown.tugasakhir2.homepage.adapter.ProductAdapter;
import com.uknown.tugasakhir2.homepage.detailproduct.ProductListActivity;
import com.uknown.tugasakhir2.homepage.model.ProductModel;

import java.util.List;

public class ElectronicActivity extends AppCompatActivity {

    private RelativeLayout computer, smartphone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electronic);

        // Mengambil beberapa komponen yang diperlukan
        computer = findViewById(R.id.containerComputer);
        smartphone = findViewById(R.id.containerSmartphone);

        // Ketika container computer diklik
        computer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goListProduct = new Intent(ElectronicActivity.this, ProductListActivity.class); // Intialitation intent
                goListProduct.putExtra("kategori", "computer"); // Mengirimkan string kepada ProductListAcitivity
                startActivity(goListProduct); // Memulai Intent
            }
        });

        // Ketika container smartphone diklik
        smartphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goListProduct = new Intent(ElectronicActivity.this, ProductListActivity.class); // Intialitation intent
                goListProduct.putExtra("kategori", "smartphone"); // Mengirimkan string kepada ProductListAcitivity
                startActivity(goListProduct); // Memulai Intent
            }
        });
    }
}