package com.uknown.tugasakhir2.homepage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.uknown.tugasakhir2.R;
import com.uknown.tugasakhir2.homepage.model.ProductModel;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private List<ProductModel> listDataProduct = new ArrayList<>(); // List data product
    private Context context;
    private setListener listener;

    // Membuat interface yang berisi function ketika item diklik
    public interface setListener {
        void onItemClick(int adapterPosition);
    }

    // Constuctor Product Adapter
    public ProductAdapter (Context context, List<ProductModel> listDataProduct, setListener listener){
        this.listDataProduct = listDataProduct;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position) {

        // Memanggil data dari list Product Model dengan posisi sesuai adapter
        ProductModel dataInPosition = listDataProduct.get(position);

        // Memasukan data yang telah didapat kedalam layout
        holder.quantity.setText(String.valueOf(dataInPosition.getQuantity()));
        holder.nameProduct.setText(dataInPosition.getName());

        // Mamsukan link gambar yang didapat keadalam component imageview menggunakan glide
        Glide.with(context)
                .load(dataInPosition.getImage())
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.drawable.error_display)
                .into(holder.imageProduct);

    }

    @Override
    public int getItemCount() {
        return listDataProduct.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // Variable untuk menampung component layout item product
        ImageView imageProduct;
        TextView nameProduct, quantity;
        LinearLayout container;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // Inisialisasi variable component
            imageProduct = itemView.findViewById(R.id.imageProduct);
            nameProduct = itemView.findViewById(R.id.nameProduct);
            quantity = itemView.findViewById(R.id.quantityProduct);
            container = itemView.findViewById(R.id.containerProduct);

            // Set action ketika item diklik
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(getAdapterPosition());
                }
            });
        }
    }
}
