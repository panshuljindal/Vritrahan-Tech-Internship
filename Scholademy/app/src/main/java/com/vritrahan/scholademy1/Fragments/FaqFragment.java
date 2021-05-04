package com.vritrahan.scholademy1.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vritrahan.scholademy1.Adapter.FaqAdapter;
import com.vritrahan.scholademy1.Model.FaqModel;
import com.vritrahan.scholademy1.R;

import java.util.ArrayList;

public class FaqFragment extends Fragment {

    View view;
    RecyclerView recyclerView;
    ArrayList<FaqModel> myList;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_faq, container, false);
        recyclerView = view.findViewById(R.id.faqRecyclerView);
        addData();

        adapter();
        return view;
    }
    public void adapter(){
        FaqAdapter adapter = new FaqAdapter(myList,view.getContext());
        LinearLayoutManager manager = new LinearLayoutManager(view.getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);
    }
    public void addData(){
        myList = new ArrayList<>();
        myList.add(new FaqModel("Why should I register for National Coaching scholarship test?",
                "Our National Coaching scholarship test NCST provides up to 100% scholarship (ie fee " +
                        "waiver) in tuition fee in coaching for various competitive entrance and job examinations like " +
                        "IIT JEE, NEET, UPSC CSE, IES, GATE, CLAT, UGC NET, Judiciary, State PCS, SSC CGL, etc."));
        myList.add(new FaqModel("Do you provide scholarship for full year courses only or for short courses as well?",
                "Our scholarship is valid on all courses but they are subject to the unique conditions applied " +
                        "by the various coaching institutes"));
        myList.add(new FaqModel("For which coaching institutes do you provide scholarship for?",
                "We have multiple coaching institutes from all over the country who have partnered with us. " +
                        "Various various competitive entrance and job examinations like IIT JEE, NEET, UPSC CSE, IES, " +
                        "GATE, CLAT, UGC NET, Judiciary, State PCS, SSC CGL, etc. are covered by multiple institutes " +
                        "each. Please visit our About us page for a complete list of partner institutes."));
        myList.add(new FaqModel("What are the registration charges for this scholarship test?",
                "The scholarship test is absolutely free for all students. This initiative is to help " +
                        "underprivileged by making costly coaching based educational products accessible through " +
                        "generous scholarships."));
        myList.add(new FaqModel("How can I donate to the Scholademy cause?",
                "You can mail us at" +" donations@scholademy.org"+ " with all your/ your company’s details and " +
                        "how many students or amount you plan to commit."));

    }

}