package com.shubham.odsadmin.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.shubham.odsadmin.R;

public class WorkerInfoActivity extends AppCompatActivity {

    private TextView name, age, gender, address, wallet, phone, email, servicesDone;
    private RatingBar rating;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String PhoneNumber;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_info);

        Intent i = getIntent();
        PhoneNumber = i.getStringExtra("Phone");
        setDefault();
        progressDialog = new ProgressDialog(WorkerInfoActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        setText();
    }

    private void setDefault() {
        name = (TextView) findViewById(R.id.worker_info_name_tv);
        age = (TextView) findViewById(R.id.worker_info_age_tv);
        gender = (TextView) findViewById(R.id.worker_info_gender_tv);
        address = (TextView) findViewById(R.id.worker_info_address_tv);
        servicesDone = (TextView) findViewById(R.id.worker_info_service_done_tv);
        wallet = (TextView) findViewById(R.id.worker_info_wallet_tv);
        phone = (TextView) findViewById(R.id.worker_info_phone_tv);
        email = (TextView) findViewById(R.id.worker_info_email_tv);
        rating = (RatingBar) findViewById(R.id.worker_info_ratingBar);
    }

    private void setText() {
        db.collection("workers").document(PhoneNumber).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    String fName = documentSnapshot.getString("First Name");
                    String lName = documentSnapshot.getString("Last Name");
                    String Age = documentSnapshot.getString("Age");
                    String Gender = documentSnapshot.getString("Gender");
                    String Address = documentSnapshot.getString("Address");
                    String Rating = documentSnapshot.getString("Rating");
                    String ServicesDone = documentSnapshot.getString("Services Done");
                    String Phone = documentSnapshot.getString("Phone");
                    String Email = documentSnapshot.getString("Email");
                    String Wallet = documentSnapshot.getString("Wallet");
                    String namee = fName + " " + lName;

                    name.setText(namee);
                    gender.setText(Gender);
                    age.setText(Age);
                    address.setText(Address);
                    wallet.setText(Wallet);
                    rating.setRating(Float.parseFloat(Rating));
                    servicesDone.setText(ServicesDone);
                    phone.setText(Phone);
                    email.setText(Email);
                    progressDialog.dismiss();
                }
            }
        });
    }
}