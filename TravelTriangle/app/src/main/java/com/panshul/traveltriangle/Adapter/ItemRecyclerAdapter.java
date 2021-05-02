package com.panshul.traveltriangle.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.panshul.traveltriangle.Model.ItemModel;
import com.panshul.traveltriangle.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ItemRecyclerAdapter extends RecyclerView.Adapter<ItemRecyclerAdapter.MyViewHolder> {

    ArrayList<ItemModel> list;
    Context context;

    public ItemRecyclerAdapter(ArrayList<ItemModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_booking,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ItemModel model = list.get(position);
        holder.cityName.setText(model.getName());
        holder.data.setText(model.getData());
        holder.price.setText(model.getPrice());
        holder.photo.setImageResource(model.getImage());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView cityName,data,price;
        ImageView photo;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            photo = itemView.findViewById(R.id.photoPackage);
            cityName = itemView.findViewById(R.id.cityName);
            data = itemView.findViewById(R.id.dataPackage);
            price = itemView.findViewById(R.id.packagePrice);
        }
    }
}
