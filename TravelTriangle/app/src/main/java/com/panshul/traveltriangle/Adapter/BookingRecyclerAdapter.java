package com.panshul.traveltriangle.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.panshul.traveltriangle.Activity.PreferenceActivity;
import com.panshul.traveltriangle.Model.FormModel.FormData;
import com.panshul.traveltriangle.R;

import java.util.List;

public class BookingRecyclerAdapter extends RecyclerView.Adapter<BookingRecyclerAdapter.MyViewHolder> {

    List<FormData> mylist;
    Context mContext;

    public BookingRecyclerAdapter(List<FormData> mylist, Context mContext) {
        this.mylist = mylist;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.booking_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        FormData data = mylist.get(position);
        holder.toName.setText(data.getTo());
        if (data.getMonth().equals("null")){
            holder.dateName.setText(data.getDeparture_date());
        }
        else if(data.getDeparture_date().equals("null")){
            holder.dateName.setText(data.getMonth());
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                String json = gson.toJson(data);
                Intent intent = new Intent(mContext, PreferenceActivity.class);
                intent.putExtra("modelActivity",json);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mylist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView toName,dateName;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            toName = itemView.findViewById(R.id.toName);
            dateName = itemView.findViewById(R.id.dateName);
        }
    }
}
