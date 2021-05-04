package com.vritrahan.scholademy1.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vritrahan.scholademy1.Adapter.MstAdapter;
import com.vritrahan.scholademy1.Model.MstModel;
import com.vritrahan.scholademy1.R;

import java.util.ArrayList;

public class MstFragment extends Fragment {

    View view;
    RecyclerView recyclerView;
    ArrayList<MstModel> modelList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_mst, container, false);
        recyclerView = view.findViewById(R.id.mstRecyclerView);
        modelList = new ArrayList<>();
        addData();
        adapter();
        return view;
    }
    public void addData(){
        modelList.add(new MstModel("IIT JEE","Course Name","Rs. 1000",R.drawable.googlelogo,"10000","50%"));
        modelList.add(new MstModel("UPSC CSE GS","Course Name","Rs. 1000",R.drawable.googlelogo,"10000","50%"));
        modelList.add(new MstModel("CLAT","Course Name","Rs. 1000",R.drawable.googlelogo,"10000","50%"));
        modelList.add(new MstModel("UGC NET","Course Name","Rs. 1000",R.drawable.googlelogo,"10000","50%"));
        modelList.add(new MstModel("CA/CS","Course Name","Rs. 1000",R.drawable.googlelogo,"10000","50%"));
        modelList.add(new MstModel("SSC,Bank PO,RBI,NABARD","Course Name","Rs. 1000",R.drawable.googlelogo,"10000","50%"));
    }
    public void adapter(){
        MstAdapter adapter = new MstAdapter(modelList,view.getContext());
        LinearLayoutManager manager = new LinearLayoutManager(view.getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);
    }
}