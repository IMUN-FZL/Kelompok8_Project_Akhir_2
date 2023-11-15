package com.uknown.tugasakhir2.admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;
import com.uknown.tugasakhir2.R;
import com.uknown.tugasakhir2.homepage.model.ProductModel;

import java.util.HashMap;
import java.util.Map;

public class TAMBAH_STOK extends AppCompatActivity {
    
    private DatabaseReference database;
    private EditText itemID, quantityToID;
    private Button change;
    private Spinner spinner;
    private String choice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_stok);

        itemID = findViewById(R.id.itemID);
        quantityToID = findViewById(R.id.quantityToID);
        change = findViewById(R.id.changeValue);
        spinner = findViewById(R.id.spinChoice);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choice = parent.getItemAtPosition(position).toString();
                database = getCall(choice);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.orderByChild("id").equalTo(Integer.parseInt(itemID.getText().toString())).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            ProductModel model = null;
                            String key = "";
                            for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                                model = dataSnapshot.getValue(ProductModel.class);
                                key = dataSnapshot.getKey();
                            }
                            HashMap<String, Object> data = new HashMap<String, Object>();
                            data.put("id", model.getId());
                            data.put("image", model.getImage());
                            data.put("name", model.getName());
                            data.put("quantity", Integer.parseInt(quantityToID.getText().toString()));

                            System.out.println(key);

                            database.child(key).updateChildren(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(TAMBAH_STOK.this, "Berhasil Terupdate", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        } else {
                            System.out.println("tidak ada");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
    }

    private DatabaseReference getCall(String choice) {
        switch (choice) {
            case "books" :
                return FirebaseDatabase.getInstance().getReference("data").child("books");
            case "clothing men tshirt" :
                return FirebaseDatabase.getInstance().getReference("data").child("clothing").child("men").child("tshirt");
            case "clothing men bottomear":
                return FirebaseDatabase.getInstance().getReference("data").child("clothing").child("men").child("bottomwear");
            case "clothing men formal" :
                return FirebaseDatabase.getInstance().getReference("data").child("clothing").child("men").child("formal");
            case "clothing men shoes" :
                return FirebaseDatabase.getInstance().getReference("data").child("clothing").child("men").child("shoes");
            case "clothing women tshirt" :
                return FirebaseDatabase.getInstance().getReference("data").child("clothing").child("women").child("tshirt");
            case "clothing women bottomear":
                return FirebaseDatabase.getInstance().getReference("data").child("clothing").child("women").child("bottomwear");
            case "clothing women formal" :
                return FirebaseDatabase.getInstance().getReference("data").child("clothing").child("women").child("formal");
            case "clothing women shoes" :
                return FirebaseDatabase.getInstance().getReference("data").child("clothing").child("women").child("shoes");
            case "electronics computer" :
                return FirebaseDatabase.getInstance().getReference("data").child("electronics").child("computer");
            case "electronics smartphone" :
                return FirebaseDatabase.getInstance().getReference("data").child("electronics").child("smartphone");
            case "other" :
                return FirebaseDatabase.getInstance().getReference("data").child("others");
            default:
                return null;
        }
    }
}