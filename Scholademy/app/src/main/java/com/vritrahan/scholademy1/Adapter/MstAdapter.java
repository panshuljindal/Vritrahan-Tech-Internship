package com.vritrahan.scholademy1.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vritrahan.scholademy1.Model.MstModel;
import com.vritrahan.scholademy1.R;
import com.vritrahan.scholademy1.Activity.StartQuizActivity;

import java.util.ArrayList;

public class MstAdapter extends RecyclerView.Adapter<MstAdapter.MyViewHolder> {

    ArrayList<MstModel> mylist;
    Context mContext;
    String type,course;
    public MstAdapter(ArrayList<MstModel> mylist, Context mContext) {
        this.mylist = mylist;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.mst_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MstModel model = mylist.get(position);
        holder.examName.setText("Exam Name- "+model.getExamName());
        holder.courseName.setText(model.getCourseName());
        holder.courseFees.setText(model.getCourseFees());
        holder.scholarship.setText(mContext.getResources().getString(R.string.rupee)+model.getScholoshipAmount());
        holder.scholarshipUpto.setText("Scholarship upto "+model.getSchlorshipUpto());
        holder.takeTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = mContext.getSharedPreferences("com.vritrahan.scholademy.mst",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                if (holder.examName.getText().toString().contains("IIT JEE")){
                    type="type1";
                    course="IITJEE";
                }
                if (holder.examName.getText().toString().contains("UPSC CSE GS"))
                {
                    type ="type2";
                    course="UPSC";
                }
                if (holder.examName.getText().toString().contains("CLAT")){
                    type ="type3";
                    course="CLAT";
                }
                if (holder.examName.getText().toString().contains("UGC NET")){
                    type ="type4";
                    course="UGC";
                }
                if (holder.examName.getText().toString().contains("CA/CS")){
                    type ="type5";
                    course="CA";
                }
                if (holder.examName.getText().toString().contains("SSC,Bank PO,RBI,NABARD")){
                    type ="type6";
                    course="SSC";
                }
                DatabaseReference myref = FirebaseDatabase.getInstance().getReference("Users");
                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                myref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String value = snapshot.child(uid).child("Exam").child(course).getValue().toString();
                        if (value.equals("null")){
                            Intent intent = new Intent(mContext,StartQuizActivity.class);
                            intent.putExtra("typeQuestion",type);
                            mContext.startActivity(intent);
                        }
                        else {
                            Toast.makeText(mContext, "Not allowed to attempt again!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(mContext, "Error Occurred. Please try again later! ", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return mylist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        Button takeTest;
        TextView examName,courseName,courseFees,scholarship,scholarshipUpto;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            takeTest = itemView.findViewById(R.id.takeTestButton);
            examName = itemView.findViewById(R.id.examNameTextView);
            courseName = itemView.findViewById(R.id.courseNameTextView);
            courseFees = itemView.findViewById(R.id.feesTextView);
            scholarship = itemView.findViewById(R.id.amountTextView);
            scholarshipUpto = itemView.findViewById(R.id.scholarshipTextView);
        }
    }
}
