package com.mygov.parivartan.smartindiahackathon.employee.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mygov.parivartan.smartindiahackathon.R;
import com.mygov.parivartan.smartindiahackathon.employee.adapter.JobAdapter;
import com.mygov.parivartan.smartindiahackathon.employee.model.JobItem;
import com.mygov.parivartan.smartindiahackathon.employer.model.Employer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by deepak on 01-04-2017.
 */

public class JobSearch extends Fragment {
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    ListView listView;
    JobAdapter mAdapter;
    EditText et_search;
    ImageView bt_search;
    ProgressDialog progressDialog;
    Spinner spinner_sort;

    String key = "req_skill1";
    static String mSearch ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_employee_search,container,false);

        //Getting FireBase Instance
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Employer");

        //Getting id of Views
        listView = (ListView)view.findViewById(R.id.list_searched_job);
        et_search = (EditText)view.findViewById(R.id.et_search);
        bt_search = (ImageView)view.findViewById(R.id.bt_search);
        spinner_sort = (Spinner)view.findViewById(R.id.spinner_sort);
        setupSpinner();

        List<JobItem> jobItems = new ArrayList<>();
        mAdapter = new JobAdapter(getContext(),R.layout.list_item_jobs,jobItems);

        bt_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mAdapter != null){
                    mAdapter.clear();
                }

                progressDialog = new ProgressDialog(getContext());
                progressDialog.setMessage("Searching Jobs");
                progressDialog.show();

                mSearch = et_search.getText().toString();

                databaseReference.orderByChild(key).startAt(mSearch).endAt(mSearch)
                        .addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                Employer child = dataSnapshot.getValue(Employer.class);
                                JobItem jobItem = new JobItem(child.name, child.city, child.state, child.intro);
                                mAdapter.add(jobItem);
                                progressDialog.dismiss();

                                if(mAdapter == null){
                                    Toast.makeText(getContext(), "No Match Found", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                            }

                            @Override
                            public void onChildRemoved(DataSnapshot dataSnapshot) {

                            }
                            @Override
                            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                if(mAdapter == null){
                    Toast.makeText(getContext(), "No Match Found", Toast.LENGTH_SHORT).show();
                }
                else{
                    listView.setAdapter(mAdapter);
                }

            }
        });
        return view;
    }
    private void setupSpinner(){
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getContext(),R.array.array_job_sort,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner_sort.setAdapter(adapter);
        spinner_sort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                key = (String)adapterView.getItemAtPosition(i);
                if(key.equals(getString(R.string.sort_city))){
                    key = "city";
                }else if(key.equals(getString(R.string.sort_skill))){
                    key = "req_skill1";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter.clear();
    }
}

