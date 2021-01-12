package com.shubham.odsadmin.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.shubham.odsadmin.R;
import com.shubham.odsadmin.adapter.WorkerAdapter;
import com.shubham.odsadmin.model.workerList;

import java.util.ArrayList;

public class WorkerActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ProgressDialog progressDialog;
    private ListView WorkerList;
    private ArrayList<workerList> worker = new ArrayList<workerList>();
    private TextView nothing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_activity);

        WorkerList = (ListView) findViewById(R.id.worker_list);
        nothing = (TextView) findViewById(R.id.worker_nothing_tv);
        progressDialog = new ProgressDialog(WorkerActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        updateList();
    }

    private void updateList() {
        worker.clear();

        db.collection("workers").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String firstName = document.getString("First Name");
                                String lastName = document.getString("Last Name");
                                String address = document.getString("Address");
                                String rating = document.getString("Rating");
                                String phoneNumber = document.getString("Phone");
                                String service = document.getString("Services Done");
                                String Name = firstName + " " + lastName;

                                worker.add(new workerList(Name, address, service, rating, phoneNumber));

                            }

                            if (worker.size() > 0)
                                nothing.setVisibility(View.GONE);

                            WorkerAdapter workerAdapter = new WorkerAdapter(WorkerActivity.this, worker);

                            progressDialog.dismiss();
                            WorkerList.setAdapter(workerAdapter);
                            WorkerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    com.shubham.odsadmin.model.workerList d = worker.get(position);
                                    Intent intent = new Intent(WorkerActivity.this, WorkerInfoActivity.class);
                                    intent.putExtra("Phone", d.getPhone());
                                    startActivity(intent);
                                }
                            });


                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(WorkerActivity.this, "Error#121", Toast.LENGTH_SHORT).show();
                            Log.d("DATA FETCH ERROR", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}