package com.uknown.tugasakhir2.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.uknown.tugasakhir2.AboutActivity;
import com.uknown.tugasakhir2.HalamanDepanActivity;
import com.uknown.tugasakhir2.R;
import com.uknown.tugasakhir2.homepage.adapter.HomeAdapter;
import com.uknown.tugasakhir2.homepage.clothing.ClothingActivity;
import com.uknown.tugasakhir2.homepage.detailproduct.ProductListActivity;
import com.uknown.tugasakhir2.homepage.electronic.ElectronicActivity;
import com.uknown.tugasakhir2.homepage.model.ItemModel;
import com.uknown.tugasakhir2.staff.StaffLoginActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements HomeAdapter.onItem, NavigationView.OnNavigationItemSelectedListener {

    private List<ItemModel> listHomeAdapter = new ArrayList<>();
    private RecyclerView rvListHome;
    private HomeAdapter adapter;
    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawerLayout;
    private FirebaseDatabase database;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private String uid;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        database = FirebaseDatabase.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        reference = database.getReference();
        auth = FirebaseAuth.getInstance();

        // Mengambil componen toolbar dan navigation view dan drawer layout
        Toolbar tl = findViewById(R.id.toolbar);
        NavigationView nv = findViewById(R.id.navView);
        drawerLayout = findViewById(R.id.drawLayout);

        // Set ActionBarDrawerToggle
        toggle = new ActionBarDrawerToggle(this, drawerLayout, tl, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle); // Mengimplementasikan toggle ke dalam drawer
        toggle.syncState(); // Me-sinkron kan toggle

        setSupportActionBar(tl); // Mendaftarkan toolbar ke dalam aplikasi
        getSupportActionBar().setDisplayShowTitleEnabled(false); // Menyembunyikan tittle pada Toolbar
        nv.setNavigationItemSelectedListener(HomeActivity.this); // Mengimplementasikan function ketika item dalam nav view diklik


        rvListHome = findViewById(R.id.rvHomePage); // Meng inisialisasi recycleview dengan yang ada pada layout activity_home
        setModel(); // Memanggil funciton setModel

        adapter = new HomeAdapter(this, listHomeAdapter, this); // Membuat object home adapter
        rvListHome.setLayoutManager(new GridLayoutManager(this, 2)); // Set layout RecyclerView
        rvListHome.setAdapter(adapter); // Set adapter ke dalam recycleview


    }

    // Membuat sample untuk model tampilan home page
    private void setModel() {

        List<String> nama = Arrays.asList("CLOTHING AND ACCESSORIES", "ELECTRONICS", "BOOKS", "OTHER ITEMS"); // Membuat data nama kategori  berupa string

        List<Integer> image = Arrays.asList(R.drawable.item_clothing, R.drawable.item_electronic, R.drawable.item_books, R.drawable.item_otheritem); // Membuat data image kategori berupa integer

        // Looping untuk memasukan semua data ke dalam List<ItemModel>
        for (int i = 0 ; i < nama.size() ; i++){
            ItemModel itemModel = new ItemModel();
            itemModel.setName(nama.get(i));
            itemModel.setImage(image.get(i));
            listHomeAdapter.add(itemModel);
        }
    }

    // Function implement dari HomeAdapter yang fungsinya untuk logic ketika item dalam adapter diklik
    @Override
    public void click(int adapterPosition) {
        Intent switchActivity = null;

        // Kondisi ketika posisi item pada adapter diklik
        switch (adapterPosition){
            case 0:
                switchActivity = new Intent(HomeActivity.this, ClothingActivity.class);
                break;
            case 1:
                switchActivity = new Intent(HomeActivity.this, ElectronicActivity.class);
                break;
            case 2:
                switchActivity = new Intent(HomeActivity.this, ProductListActivity.class);
                switchActivity.putExtra("kategori", "books");
                break;
            case 3:
                switchActivity = new Intent(HomeActivity.this, ProductListActivity.class);
                switchActivity.putExtra("kategori", "other");
                break;
            default:
                Toast.makeText(HomeActivity.this, "Content tidak ditemukan", Toast.LENGTH_SHORT).show();
        }


        if(switchActivity != null){
            startActivity(switchActivity);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Memasukan custom menu ke dalam menu toolbar
        getMenuInflater().inflate(R.menu.top_app_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        // Mengaktifkan toggle app drawer
        if(toggle.onOptionsItemSelected(item)){
            return true;
        }

        // Action ketika icon keluar diklik
        if(item.getItemId() == R.id.logout){
            Toast.makeText(HomeActivity.this, "Logout", Toast.LENGTH_SHORT).show();
            auth.signOut();
            checkUser();
        }
        return super.onOptionsItemSelected(item);
    }

    private void checkUser() {
        FirebaseUser firebaseUser = auth.getCurrentUser();
        if (firebaseUser == null){
            startActivity(new Intent(this, HalamanDepanActivity.class));
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        // Action ketika didalam klik backpress didalam drawer, maka drawer akan tertutup
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Action ketika item navigation diklik sesuai dengan id
        if (item.getItemId() == R.id.aboutus){
            Toast.makeText(HomeActivity.this, "Berhasil", Toast.LENGTH_SHORT).show();
            Intent goAbout = new Intent(HomeActivity.this, AboutActivity.class);
            startActivity(goAbout);
        }
        return true;
    }
}