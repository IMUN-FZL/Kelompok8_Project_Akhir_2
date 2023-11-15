package com.uknown.tugasakhir2.homepage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.uknown.tugasakhir2.R;
import com.uknown.tugasakhir2.homepage.model.ItemModel;

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder>{

    private List<ItemModel> data = new ArrayList<>(); // Membuat variable list dengan model ItemModel
    private Context context;

    public HomeAdapter (Context context, List<ItemModel> itemModels, onItem setListener){
        this.data = itemModels;
        this.context = context;
        this.setListener = setListener;
    }

    private onItem setListener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_homepage, parent , false)); // Meng-inflate item layout yang sudah dibuat secara terpisah
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemModel datainPosition = data.get(position);  // Menggambil scope data didalam setiap posisi

        holder.category.setText(datainPosition.getName()); // Set nama item
        holder.container.setBackground(ContextCompat.getDrawable(context, datainPosition.getImage())); // Set background item


    }

    @Override
    public int getItemCount() {
        return data.size(); // Jumlah data pada list variable data
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView category; // Membuat variable guna menampung component TextView
        private RelativeLayout container; // Membuat variable guna menampung component Relative
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            category = itemView.findViewById(R.id.tvItemCategory); // Memanggil component textview menggunakan id pada item_homepage layout
            container = itemView.findViewById(R.id.containerItem); // Memanggil component Relative menggunakan id pada item_homepage layout


            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setListener.click(getAdapterPosition());
                }
            });
        }
    }

    public interface onItem {
        void click(int adapterPosition);
    }
}
