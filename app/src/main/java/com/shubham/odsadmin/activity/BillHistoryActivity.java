package com.shubham.odsadmin.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
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
import com.shubham.odsadmin.adapter.UserAdapter;
import com.shubham.odsadmin.model.billList;
import com.shubham.odsadmin.model.userList;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BillHistoryActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ProgressDialog progressDialog;
    private ListView BillList;
    private ArrayList<billList> bill = new ArrayList<billList>();
    private TextView nothing;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_history);

       BillList = (ListView) findViewById(R.id.bill_history_list);
        nothing = (TextView) findViewById(R.id.bill_history_nothing_tv);
        progressDialog = new ProgressDialog(BillHistoryActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        updateList();
    }

    private void updateList() {
        bill.clear();

        db.collection("bills").whereEqualTo("Status","Paid").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
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
                            bill.sort(Comparator.comparing(o -> o.getDate()));
                            if (bill.size() > 0)
                                nothing.setVisibility(View.GONE);

                            BillAdapter billAdapter = new BillAdapter(BillHistoryActivity.this, bill);

                            progressDialog.dismiss();
                            BillList.setAdapter(billAdapter);
                            BillList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    billList d = bill.get(position);
                                    Intent intent = new Intent(BillHistoryActivity.this, BillInfoActivity.class);
                                    intent.putExtra("Id", d.getId());
                                    intent.putExtra("From","History");
                                    startActivity(intent);
                                }
                            });


                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(BillHistoryActivity.this, "Error#121", Toast.LENGTH_SHORT).show();
                            Log.d("DATA FETCH ERROR", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}