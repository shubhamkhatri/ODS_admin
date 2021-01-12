package com.shubham.odsadmin.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.shubham.odsadmin.R;

public class HomeActivity extends AppCompatActivity {

    private LinearLayout bills,history,users,workers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bills=(LinearLayout)findViewById(R.id.bills_available);
        history=(LinearLayout)findViewById(R.id.bills_history);
        workers=(LinearLayout)findViewById(R.id.workers);
        users=(LinearLayout)findViewById(R.id.users);

        bills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,BillPendingActivity.class));
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,BillHistoryActivity.class));
            }
        });

        workers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,WorkerActivity.class));
            }
        });

        users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,UserActivity.class));
            }
        });
        
    }
}