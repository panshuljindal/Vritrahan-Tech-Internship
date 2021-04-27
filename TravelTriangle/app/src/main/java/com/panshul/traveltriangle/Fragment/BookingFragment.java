package com.panshul.traveltriangle.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.panshul.traveltriangle.Adapter.BookingRecyclerAdapter;
import com.panshul.traveltriangle.Model.FormModel.FormData;
import com.panshul.traveltriangle.R;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class BookingFragment extends Fragment {

    View view;
    List<FormData> dataList;
    RecyclerView recyclerView;
    TextView empty;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_booking, container, false);

        dataList = new ArrayList<>();
        loadData();
        recyclerView = view.findViewById(R.id.bookingRecyclerView);
        empty = view.findViewById(R.id.emptyTextView);
        addData();
        adapter();
        checkData();
        return view;

    }
    public void checkData(){
        if (dataList.isEmpty()){
            empty.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
        }
        else {
            empty.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }
    public void adapter(){
        BookingRecyclerAdapter adapter = new BookingRecyclerAdapter(dataList,view.getContext());
        GridLayoutManager manager = new GridLayoutManager(view.getContext(),2);
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }
    public void addData(){
        try {
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            DatabaseReference myref = FirebaseDatabase.getInstance().getReference("Users").child(uid).child("Booking");
            myref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    dataList.clear();
                    for (DataSnapshot ds: snapshot.getChildren()){
                        FormData data = ds.getValue(FormData.class);
                        dataList.add(data);
                    }
                    checkData();
                    saveData();
                    adapter();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
        catch (Exception e){

        }
        checkData();

    }
    public void loadData(){
        SharedPreferences pref1 = view.getContext().getSharedPreferences("com.panshul.traveltriangle.booking",MODE_PRIVATE);
        String json1 = pref1.getString("bookings","");
        Gson gson1 = new Gson();
        Type type = new TypeToken<List<FormData>>() {}.getType();
        dataList = gson1.fromJson(json1,type);
        if (dataList==null){
            dataList = new ArrayList<>();
        }
    }
    public void saveData(){
        SharedPreferences pref = view.getContext().getSharedPreferences("com.panshul.traveltriangle.booking",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(dataList);
        editor.putString("bookings",json);
        editor.apply();
    }
}