package com.shubham.odsadmin.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
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
import com.shubham.odsadmin.adapter.UserAdapter;
import com.shubham.odsadmin.adapter.WorkerAdapter;
import com.shubham.odsadmin.model.userList;
import com.shubham.odsadmin.model.workerList;

import java.util.ArrayList;
import java.util.List;

public class UserActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ProgressDialog progressDialog;
    private ListView UserList;
    private ArrayList<userList> user = new ArrayList<userList>();
    private TextView nothing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        UserList = (ListView) findViewById(R.id.user_list);
        nothing = (TextView) findViewById(R.id.user_nothing_tv);
        progressDialog = new ProgressDialog(UserActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        updateList();
    }

    private void updateList() {
        user.clear();

        db.collection("users").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String firstName = document.getString("First Name");
                                String lastName = document.getString("Last Name");
                                String address = document.getString("Address");
                                String phoneNumber = document.getString("Phone");
                                String service = document.getString("Services Done");
                                String Name = firstName + " " + lastName;

                                user.add(new userList(Name, address, service,  phoneNumber));

                            }

                            if (user.size() > 0)
                                nothing.setVisibility(View.GONE);

                            UserAdapter userAdapter = new UserAdapter(UserActivity.this, user);

                            progressDialog.dismiss();
                            UserList.setAdapter(userAdapter);
                            UserList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    userList d = user.get(position);
                                    Intent intent = new Intent(UserActivity.this, UserInfoActivity.class);
                                    intent.putExtra("Phone", d.getPhone());
                                    startActivity(intent);
                                }
                            });


                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(UserActivity.this, "Error#121", Toast.LENGTH_SHORT).show();
                            Log.d("DATA FETCH ERROR", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}