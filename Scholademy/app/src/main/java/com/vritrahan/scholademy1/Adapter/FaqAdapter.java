package com.vritrahan.scholademy1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.vritrahan.scholademy1.Model.FaqModel;
import com.vritrahan.scholademy1.R;

import java.util.ArrayList;

public class FaqAdapter extends RecyclerView.Adapter<FaqAdapter.MyViewHolder> {

    ArrayList<FaqModel> faqList;
    Context context;

    public FaqAdapter(ArrayList<FaqModel> faqList, Context context) {
        this.faqList = faqList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.faq_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        FaqModel model = faqList.get(position);
        holder.question.setText(model.getFaqQuestion());
        holder.answer.setText(model.getFaqAnswer());

        holder.imagePlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.imagePlus.setRotation(180);
                holder.imagePlus.setVisibility(View.INVISIBLE);
                holder.imagePlus.setEnabled(false);
                holder.imageMinus.setVisibility(View.VISIBLE);
                holder.answerCl.setVisibility(View.VISIBLE);
            }
        });
        holder.imageMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.imageMinus.setRotation(180);
                holder.imageMinus.setVisibility(View.GONE);
                holder.imagePlus.setEnabled(true);
                holder.imagePlus.setVisibility(View.VISIBLE);
                holder.answerCl.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return faqList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView question,answer;
        ConstraintLayout questionCl,answerCl;
        ImageView imagePlus,imageMinus;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            question = itemView.findViewById(R.id.faqquestion);
            answer = itemView.findViewById(R.id.faqanswer);
            questionCl = itemView.findViewById(R.id.questionCl);
            answerCl = itemView.findViewById(R.id.answerCl);
            imageMinus = itemView.findViewById(R.id.faqMinus);
            imagePlus = itemView.findViewById(R.id.faqPlus);
        }
    }
}
