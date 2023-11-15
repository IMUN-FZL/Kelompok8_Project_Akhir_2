package com.uknown.tugasakhir2.homepage.clothing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.uknown.tugasakhir2.R;
import com.uknown.tugasakhir2.homepage.detailproduct.ProductListActivity;

public class ClothingCategoryActivity extends AppCompatActivity {

    private ImageView imageTshirt, imageFormal, imageBottomWear, imageShoes; // Membuat variable untuk menampung componen ImageView
    private String dataCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothing_category);

        // Mengambil data dari aktivity sebelumnya
        dataCategory = getIntent().getExtras().getString("kategori");
        System.out.println(dataCategory);

        // Memanggil setiap component ImageView menggunakan id
        imageTshirt = findViewById(R.id.imageTshirt);
        imageFormal = findViewById(R.id.imageFormal);
        imageBottomWear = findViewById(R.id.imageBottomWear);
        imageShoes = findViewById(R.id.imageShoes);


        if(dataCategory.equals("women")){
            // Menyesuaikan gambar pakaian serta sepatu wanita
            imageTshirt.setImageResource(R.drawable.tshirt_category_wanita);
            imageFormal.setImageResource(R.drawable.casual_category_wanita);
            imageBottomWear.setImageResource(R.drawable.underlow_category_wanita);
            imageShoes.setImageResource(R.drawable.shoes_category_wanita);
        }

        // Function untuk menentukan activity tujuan sesuai dengan input user
       setNavigation(imageTshirt, imageFormal, imageBottomWear, imageShoes);


    }

    private void setNavigation(ImageView imageTshirt, ImageView imageCasual, ImageView imageBottomWear, ImageView imageShoes) {
        Intent toItemActivity = new Intent(ClothingCategoryActivity.this, ProductListActivity.class);

        imageTshirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toItemActivity.putExtra("kategori", "tshirt");
                toItemActivity.putExtra("menorwoman", dataCategory); // Menambahkan data ketika yang diklik pria atau wanita
                // Memulai activity
                startActivity(toItemActivity);
            }
        });

        imageCasual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toItemActivity.putExtra("menorwoman", dataCategory); // Menambahkan data ketika yang diklik pria atau wanita
                toItemActivity.putExtra("kategori", "formal");
                // Memulai activity
                startActivity(toItemActivity);
            }
        });

        imageBottomWear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toItemActivity.putExtra("menorwoman", dataCategory); // Menambahkan data ketika yang diklik pria atau wanita
                toItemActivity.putExtra("kategori", "bottomwear");
                // Memulai activity
                startActivity(toItemActivity);
            }
        });

        imageShoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toItemActivity.putExtra("kategori", "shoes");
                toItemActivity.putExtra("menorwoman", dataCategory); // Menambahkan data ketika yang diklik pria atau wanita
                // Memulai activity
                startActivity(toItemActivity);
            }
        });

    }


}