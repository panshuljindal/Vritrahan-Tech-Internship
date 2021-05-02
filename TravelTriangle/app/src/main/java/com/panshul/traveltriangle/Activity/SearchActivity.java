package com.panshul.traveltriangle.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.panshul.traveltriangle.Activity.PlanHoliday.Plan1;
import com.panshul.traveltriangle.Adapter.ItemRecyclerAdapter;
import com.panshul.traveltriangle.Model.ItemModel;
import com.panshul.traveltriangle.R;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    RecyclerView item;
    ListView list;
    ArrayList<String> myList;
    ArrayList<ItemModel> packageList;
    EditText search;
    ImageView back;
    ArrayList<String> tempList;
    ArrayAdapter<String> arrayAdapter;
    String type;
    TextView result,relevant,popular;
    Button plan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        item=findViewById(R.id.itemRecyclerView);
        list = findViewById(R.id.itemListView);
        search = findViewById(R.id.searchEditText);
        back = findViewById(R.id.searchBack);
        result = findViewById(R.id.resultsTextView);
        relevant = findViewById(R.id.relevantResults);
        popular = findViewById(R.id.popularDestinations);
        plan = findViewById(R.id.planMyHolidayButtonSearch);


        myList = new ArrayList<>();
        tempList = new ArrayList<>();
        tempList.add("Adventure");
        tempList.add("Delhi");
        tempList.add("Mumbai");
        tempList.add("Hiking");
        tempList.add("Hill Climbing");
        tempList.add("Kolkata");
        tempList.add("Adventurous");


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FirebaseAuth.getInstance().getCurrentUser()!=null){
                    startActivity(new Intent(SearchActivity.this, Plan1.class));
                }
                else {
                    Toast.makeText(SearchActivity.this, "Please Login before Planning", Toast.LENGTH_SHORT).show();
                }
            }
        });
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                myList.clear();
                item.setVisibility(View.GONE);
                relevant.setVisibility(View.GONE);
                result.setVisibility(View.GONE);
                popular.setVisibility(View.GONE);
                list.setVisibility(View.GONE);
                list.setVisibility(View.VISIBLE);
                if (s.length() == 0) {
                    list.setVisibility(View.GONE);
                } else {
                    list.setVisibility(View.VISIBLE);
                    for (String s1 : tempList) {
                        if (s1.toLowerCase().contains(s)) {
                            myList.add(s1);
                        }
                    }
                }
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {
                myList.clear();
                item.setVisibility(View.GONE);
                relevant.setVisibility(View.GONE);
                result.setVisibility(View.GONE);
                popular.setVisibility(View.GONE);
                list.setVisibility(View.GONE);
                if (s.length() == 0) {
                    list.setVisibility(View.GONE);
                } else {
                    list.setVisibility(View.VISIBLE);
                    for (String s1 : tempList) {
                        if (s1.toLowerCase().contains(s)) {
                            myList.add(s1);
                        }
                    }

                }
                arrayAdapter.notifyDataSetChanged();
            }
        });
        adapter1();
    }
    public void adapter1(){
        arrayAdapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,myList);
        list.setAdapter(arrayAdapter);
        list.setBackgroundColor(Color.WHITE);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                type = myList.get(position);
                result.setText(type);
                search.setText(myList.get(position));
                list.setVisibility(View.GONE);
                item.setVisibility(View.VISIBLE);
                relevant.setVisibility(View.VISIBLE);
                result.setVisibility(View.VISIBLE);
                popular.setVisibility(View.VISIBLE);
                displayData();
            }
        });
    }

    public void displayData(){
        packageList = new ArrayList<>();
        packageList.add(new ItemModel("Himachal",getResources().getString(R.string.rupee)+" 15999","709000+ Travelers served by 20+ Experts",R.drawable.himachal));
        packageList.add(new ItemModel("Andaman",getResources().getString(R.string.rupee)+" 29999","569000+ Travelers served by 20+ Experts",R.drawable.andaman));
        packageList.add(new ItemModel("Nainital",getResources().getString(R.string.rupee)+" 19999","9000+ Travelers served by 20+ Experts",R.drawable.nainital));
        packageList.add(new ItemModel("Jaipur",getResources().getString(R.string.rupee)+" 12999","8000+ Travelers served by 20+ Experts",R.drawable.jaipur));

        ItemRecyclerAdapter adapter = new ItemRecyclerAdapter(packageList,this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.VERTICAL);
        item.setAdapter(adapter);
        item.setLayoutManager(manager);
    }
}