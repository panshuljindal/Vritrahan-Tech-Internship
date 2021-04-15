package com.panshul.prelimsguru.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.panshul.prelimsguru.Activity.SingleQuestion;
import com.panshul.prelimsguru.Model.Answers;
import com.panshul.prelimsguru.Model.QuestionModel;
import com.panshul.prelimsguru.R;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class AllQuestionAdapter extends RecyclerView.Adapter<AllQuestionAdapter.MyViewHolder> {

    List<QuestionModel> questions;
    List<Answers> answers;
    Context mContext;

    public AllQuestionAdapter(List<QuestionModel> list1, List<Answers> answers, Context mContext) {
        this.questions = list1;
        this.answers = answers;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.allquestionitem,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        QuestionModel item = questions.get(position);
        if (position==0){
            holder.view.setVisibility(View.INVISIBLE);
        }
        else {
            holder.view.setVisibility(View.VISIBLE);
        }
        holder.questionNo.setText("Question: "+String.valueOf(position+1));
        holder.question.setText(item.getQuestion());
        holder.option1Text.setText(item.getOption1());
        holder.option2Text.setText(item.getOption2());
        holder.option3Text.setText(item.getOption3());
        holder.option4Text.setText(item.getOption4());
        if (answers.get(position).getChoice().equals("null")){
            holder.option1Image.setImageResource(R.drawable.ic_mcq);
            holder.option2Image.setImageResource(R.drawable.ic_mcq);
            holder.option3Image.setImageResource(R.drawable.ic_mcq);
            holder.option4Image.setImageResource(R.drawable.ic_mcq);
        }
        if (holder.option1Text.getText().toString().equals(answers.get(position).getChoice())){
            holder.option1Image.setImageResource(R.drawable.ic_mcqselected);
            holder.option2Image.setImageResource(R.drawable.ic_mcq);
            holder.option3Image.setImageResource(R.drawable.ic_mcq);
            holder.option4Image.setImageResource(R.drawable.ic_mcq);
        }
        if (holder.option2Text.getText().toString().equals(answers.get(position).getChoice())){
            holder.option1Image.setImageResource(R.drawable.ic_mcq);
            holder.option2Image.setImageResource(R.drawable.ic_mcqselected);
            holder. option3Image.setImageResource(R.drawable.ic_mcq);
            holder.option4Image.setImageResource(R.drawable.ic_mcq);
        }
        if (holder.option3Text.getText().toString().equals(answers.get(position).getChoice())){
            holder. option1Image.setImageResource(R.drawable.ic_mcq);
            holder. option2Image.setImageResource(R.drawable.ic_mcq);
            holder.  option3Image.setImageResource(R.drawable.ic_mcqselected);
            holder.  option4Image.setImageResource(R.drawable.ic_mcq);
        }
        if (holder.option4Text.getText().toString().equals(answers.get(position).getChoice())){
            holder.option1Image.setImageResource(R.drawable.ic_mcq);
            holder.  option2Image.setImageResource(R.drawable.ic_mcq);
            holder.  option3Image.setImageResource(R.drawable.ic_mcq);
            holder.  option4Image.setImageResource(R.drawable.ic_mcqselected);
        }
        holder.option1Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.option1Image.setImageResource(R.drawable.ic_mcqselected);
                holder.option2Image.setImageResource(R.drawable.ic_mcq);
                holder.option3Image.setImageResource(R.drawable.ic_mcq);
                holder.option4Image.setImageResource(R.drawable.ic_mcq);
                setAnswer(position,holder.option1Text.getText().toString());
            }
        });
        holder.option2Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder. option1Image.setImageResource(R.drawable.ic_mcq);
                holder.option2Image.setImageResource(R.drawable.ic_mcqselected);
                holder.option3Image.setImageResource(R.drawable.ic_mcq);
                holder.option4Image.setImageResource(R.drawable.ic_mcq);
                setAnswer(position,holder.option2Text.getText().toString());
            }
        });
        holder.option3Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.option1Image.setImageResource(R.drawable.ic_mcq);
                holder.option2Image.setImageResource(R.drawable.ic_mcq);
                holder. option3Image.setImageResource(R.drawable.ic_mcqselected);
                holder.option4Image.setImageResource(R.drawable.ic_mcq);
                setAnswer(position,holder.option3Text.getText().toString());
            }
        });
        holder.option4Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.option1Image.setImageResource(R.drawable.ic_mcq);
                holder.option2Image.setImageResource(R.drawable.ic_mcq);
                holder.option3Image.setImageResource(R.drawable.ic_mcq);
                holder. option4Image.setImageResource(R.drawable.ic_mcqselected);
                setAnswer(position,holder.option4Text.getText().toString());
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, SingleQuestion.class);
                saveData();
                i.putExtra("quesNo",String.valueOf(position));
                mContext.startActivity(i);

            }
        });
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView questionNo;
        TextView question;
        View view;
        TextView option1Text,option2Text,option3Text,option4Text;
        ImageView option1Image,option2Image,option3Image,option4Image;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            questionNo = itemView.findViewById(R.id.questionNoAll);
            question = itemView.findViewById(R.id.questionAll);
            view = itemView.findViewById(R.id.allDivider);

            option1Text = itemView.findViewById(R.id.allOption1Text);
            option2Text = itemView.findViewById(R.id.allOption2Text);
            option3Text = itemView.findViewById(R.id.allOption3Text);
            option4Text = itemView.findViewById(R.id.allOption4Text);

            option1Image = itemView.findViewById(R.id.allOption1Image);
            option2Image = itemView.findViewById(R.id.allOption2Image);
            option3Image = itemView.findViewById(R.id.allOption3Image);
            option4Image = itemView.findViewById(R.id.allOption4Image);
        }
    }
    public void saveData(){
        SharedPreferences preferences = mContext.getSharedPreferences("com.panshul.prelimsguru.final",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson1 = new Gson();
        String json2 = gson1.toJson(answers);
        editor.putString("answers",json2);
        editor.apply();
    }
    void setAnswer(int ques,String ans){
        answers.get(ques).setChoice(ans);
        saveData();
    }
}
