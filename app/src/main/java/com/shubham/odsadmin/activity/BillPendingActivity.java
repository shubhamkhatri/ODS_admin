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
import com.shubham.odsadmin.adapter.BillAdapter;
import com.shubham.odsadmin.model.billList;

import java.util.ArrayList;

public class BillPendingActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ProgressDialog progressDialog;
    private ListView BillList;
    private ArrayList<billList> bill = new ArrayList<billList>();
    private TextView nothing;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_pending);

        BillList = (ListView) findViewById(R.id.bill_pending_list);
        nothing = (TextView) findViewById(R.id.bill_pending_nothing_tv);
        progressDialog = new ProgressDialog(BillPendingActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        updateList();
    }

    private void updateList() {
        bill.clear();

        db.collection("bills").whereEqualTo("Status","Unpaid").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String Phone = document.getString("Phone");
                                String Date = document.getString("Date");
                                String Type = document.getString("From");
                                String Amount = document.getString("Amount");
                                String Id = document.getId();

                                bill.add(new billList(Phone,Type,Date,Amount,Id));

                            }

                            if (bill.size() > 0)
                                nothing.setVisibility(View.GONE);

                            BillAdapter billAdapter = new BillAdapter(BillPendingActivity.this, bill);

                            progressDialog.dismiss();
                            BillList.setAdapter(billAdapter);
                            BillList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    billList d = bill.get(position);
                                    Intent intent = new Intent(BillPendingActivity.this, BillInfoActivity.class);
                                    intent.putExtra("Id", d.getId());
                                    intent.putExtra("From","Pending");
                                    startActivity(intent);
                                }
                            });


                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(BillPendingActivity.this, "Error#121", Toast.LENGTH_SHORT).show();
                            Log.d("DATA FETCH ERROR", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

}