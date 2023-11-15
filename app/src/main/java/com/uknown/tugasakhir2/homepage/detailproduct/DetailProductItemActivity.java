package com.uknown.tugasakhir2.homepage.detailproduct;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.uknown.tugasakhir2.R;
import com.uknown.tugasakhir2.homepage.model.ProductModel;

public class DetailProductItemActivity extends AppCompatActivity {

    ImageView dtlProduct;
    TextView nameProduct, quantityProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detai_product_item);

        // Mengambil setiap komponen item yang diperlukan
        dtlProduct = findViewById(R.id.imageDetailItem);
        nameProduct = findViewById(R.id.nameProductDetail);
        quantityProduct = findViewById(R.id.quantityDetailProduct);

        // Mengambil data bundle dari intent sebelumnya
        Bundle bundle = getIntent().getExtras();

        // Mengubah gambar menggunakan Glide
        Glide.with(this)
                .load(bundle.getString("image"))
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round)
                .into(dtlProduct);

        nameProduct.setText(bundle.getString("name")); // Mengubah nama product
        quantityProduct.setText(bundle.getString("quantity")); // Mengubah quantity product

    }
}