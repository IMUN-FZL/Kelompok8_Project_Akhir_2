package com.uknown.tugasakhir2.homepage.detailproduct;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.uknown.tugasakhir2.R;
import com.uknown.tugasakhir2.homepage.adapter.ProductAdapter;
import com.uknown.tugasakhir2.homepage.model.ProductModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends AppCompatActivity implements ProductAdapter.setListener {

    private ProductAdapter adapter;
    private List<ProductModel> listProductModel = new ArrayList<>();
    private RecyclerView recyclerViewProduct;
    private DatabaseReference dr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        recyclerViewProduct = findViewById(R.id.rvProduct);

        String hasilGetDataIntent = getIntent().getExtras().getString("kategori"); // Mengambil data intent sebelumnya dnegan kunci "kategori"
        String menorwomen = getIntent().getExtras().getString("menorwoman"); // Mengambil data intent sebelumnya dnegan kunci "menorwoman"

        // Memanggil function dataSettable guna mengambil data dari firebase lalu dimasukan ke dalam list Product Model
        dataSettable(hasilGetDataIntent, menorwomen);

        adapter = new ProductAdapter(this, listProductModel, this); // Membuat Object Product Adapter

        recyclerViewProduct.setHasFixedSize(true);
        recyclerViewProduct.setLayoutManager(new LinearLayoutManager(this)); // Setting Layout Recyleview
        recyclerViewProduct.setAdapter(adapter); // Mengimplementasikan adapter kedalam Recycler View


    }

    private void dataSettable(String hasil, String menorwomen) {
        dr = FirebaseDatabase.getInstance().getReference().child("data"); // Mengambil data dari firebase dengan path "data"

        /* Mengambil data dari database turunan path data sesuai dengan yang ditetntukan
        *
        * setListProductModel adalah custom fungsi untuk mengola data yang sudah diambil dari firebase
        * */
        switch (hasil) {
            case "computer":
                setListProductModel(dr.child("electronics").child("computer"));
                break;
            case "smartphone":
                setListProductModel(dr.child("electronics").child("smartphone"));
                break;
            case "tshirt":
                setListProductModel(dr.child("clothing").child(menorwomen).child("tshirt"));
                break;
            case "formal":
                setListProductModel(dr.child("clothing").child(menorwomen).child("formal"));
                break;
            case "bottomwear":
                setListProductModel(dr.child("clothing").child(menorwomen).child("bottomwear"));
                break;
            case "shoes":
                setListProductModel(dr.child("clothing").child(menorwomen).child("shoes"));
                break;
            case "books":
                setListProductModel(dr.child("books"));
                break;
            case "other":
                setListProductModel(dr.child("others"));
                break;
            default:
        }
    }

    @Override
    public void onItemClick(int adapterPosition) {
        Intent goDetailItem = new Intent(ProductListActivity.this, DetailProductItemActivity.class); // Initialisasi Intent
        ProductModel itemInPosition = listProductModel.get(adapterPosition); // Mengambil Product Model pada list
        goDetailItem.putExtra("name", itemInPosition.getName()); // Mengirimkan data name ke activity yang dituju
        goDetailItem.putExtra("image", itemInPosition.getImage()); // Mengirimkan data image ke activity yang dituju
        goDetailItem.putExtra("quantity", String.valueOf(itemInPosition.getQuantity())); // Mengirimkan quantity name ke activity yang dituju
        startActivity(goDetailItem);
    }

    public void setListProductModel(DatabaseReference dr){
        dr.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ProductModel value = snapshot.getValue(ProductModel.class);
                listProductModel.add(value);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}